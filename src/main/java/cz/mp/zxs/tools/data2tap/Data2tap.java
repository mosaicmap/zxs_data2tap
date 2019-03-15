/*
 * Data2tap.java
 *
 *  created: 12.3.2019
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Převádí zadáná binární data na TAP soubor ve formátu pro ZX Spectrum.
 * <p>
 * Viz popis TAP souboru, např:<ul>
 * <li><a href="https://faqwiki.zxnet.co.uk/wiki/TAP_format">TAP format na ZXS FAQ Wiki</a>,</li>
 * <li><a href="http://faqwiki.zxnet.co.uk/wiki/Spectrum_tape_interface">tape format na ZXS FAQ Wiki</a></li>
 * <li><a href="http://www.zx-modules.de/fileformats/tapformat.html">TAP format</a></li>
 * </ul><p>
 * Viz též knihy: <ul>
 * <li><i><b>ZX ROM II - poznámky [CZ] -- Daniel Jenne</b></i> (kapitoly 2, 3, 4)</li>
 * <li><i>Understanding Your Spectrum [EN] -- Ian Logan</i> (strany 14, 17, 32)</li>
 * <li><i>Rutiny ROM ZX Spectrum [CZ] -- Jan Šritter, Marcel Dauth</i> (tabulka 6 na str. 21)</li>
 * </ul>
 *
 * @author Martin Pokorný
 * @see ZxModel
 * @see TapBlockType
 * @see TapHeader
 * @see TapBody
 */
public class Data2tap {

    private static final Logger log = LoggerFactory.getLogger(Data2tap.class);
    
    private static final ZxModel DEFAULT_MODEL = ZxModelSpectrum48k.get();
    /** Model počítače ZX*. Používá se jen pro validace adresy a délky dat. */
    private ZxModel model = DEFAULT_MODEL;
        
    private static final TapBlockType DEFAULT_TAP_BLOCK_TYPE = TapBlockType.BINARY_DATA;
    private TapBlockType tapBlockType = DEFAULT_TAP_BLOCK_TYPE;
    /** Jméno bloku v hlavičce. 0-10 znaků. 
     * @see TapHeader */
    private String name = "";
    /** Adresa, kam sa mají v RAM ZX Spectra uložit data. Uvedena v hlavičce.
     * @see TapHeader */    
    private int address = -1;
    /** Binární data pro zápis do těla TAP souboru. */
    private byte[] rawData;
    
    private TapHeader tapHeader;
    private TapBody tapBody;
    
    /** Cílový TAP soubor. */
    private File outTapFile;
        
    /** */
    public Data2tap() {
    }

    public void setTapBlockType(TapBlockType type) {
        if (type == null) {
            throw new IllegalArgumentException("type = null");
        }        
        this.tapBlockType = type;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name = null");
        }        
        this.name = name;
    }
    
    public void setAddress(int address) {
        if (address < 0) {
            throw new IllegalArgumentException("address < 0");
        }
        this.address = address;
    }

    public void setRawData(byte[] rawData) {
        if (rawData == null || rawData.length == 0) {
            throw new IllegalArgumentException("rawData is blank");
        }
        this.rawData = rawData;
    }
    
    public void setModel(ZxModel model) {
        if (model == null) {
            throw new IllegalArgumentException("model = null");
        }
        this.model = model;
    }

    public void setOutTapFile(File outTapFile) {
        if (outTapFile == null) {
            throw new IllegalArgumentException("outTapFile = null");
        }
        this.outTapFile = outTapFile;
    }

    /**
     * Sestaví a uloží TAP zoubor ze zadaných dat.
     * <p>
     * Volat až po zadání všech dat. 
     * Před zavoláním této metody je potřeba zavolat minimálně: 
     * {@linkplain #setAddress(int)},
     * {@linkplain #setRawData(byte[])},
     * {@linkplain #setOutTapFile(java.io.File)},
     * <p>
     * Defaultně je nastaven model {@linkplain ZxModelSpectrum48k}. 
     * Jméno bloku je defaultně prázdné.
     * <p>
     * 
     * @throws IllegalStateException
     * @throws IllegalArgumentException
     * @throws InvalidDataException
     * @throws IOException
     */
    public void execute() throws IOException, InvalidDataException {
        log.info("");

        buildTapHeaderAndBody();
       
        saveTapDataToOutFile();
        
        log.debug("-----------------------");
    }

    /**
     * 
     * @throws InvalidDataException
     * @see #execute() 
     */
    private void buildTapHeaderAndBody() throws InvalidDataException {
        if (rawData == null || rawData.length == 0) {
            throw new InvalidDataException("no data");
        }
        log.info("model = " + model.getName());
        if (! this.model.isValidAddress(address)) {
            throw new InvalidDataException("Address " + address + 
                    " is invalid for " + model.getName());
        }
        // data se do RAM od zadané adresy nevejdou
        if (address + rawData.length > model.getRamAddresMax()) {
            log.debug("address = " + address);
            log.debug("rawData.length = " + rawData.length);
            log.debug("model.getRamAddresMax() = " + model.getRamAddresMax());
            throw new InvalidDataException(
                    "Data doesn't fit in RAM  (address + length of data > max address)");
        }
        
        log.info("tapBlockType = " + tapBlockType);
        log.info("name = \"" + name + "\"");
        log.info("address = " + address);
        log.info("dataLength = " + rawData.length);
        
        try {
            tapHeader = new TapHeader();
            log.info("assemble tap header");
            tapHeader.setZxModel(model);
            tapHeader.setType(tapBlockType);
            tapHeader.setName(name);
            tapHeader.setDataLength(rawData.length);
            tapHeader.setParam1(address);
            tapHeader.createData();
            log.info("assemble tap header ... OK");

            log.info("assemble tap body");
            tapBody = new TapBody(rawData.length);
            tapBody.append(rawData);
            tapBody.appendParityToLastByte();
            log.info("assemble tap body ... OK");     
            
        } catch (IllegalArgumentException ex) {
            log.warn(ex.getMessage());
            throw new InvalidDataException("invalid data", ex);
        }
    }

    /**
     * Volat po {@linkplain #buildTapHeaderAndBody() }.
     * 
     * @throws IllegalStateException 
     * @throws IOException 
     * @see #execute() 
     */    
    private void saveTapDataToOutFile() throws IOException {
        log.info("outTapFile = " + outTapFile);
        if (outTapFile == null) {
            throw new IllegalStateException("outTapFile = null");
        }
                
        try (
            FileOutputStream fos = new FileOutputStream(outTapFile);
            ) {
            
            log.info("write tap header");
            fos.write(tapHeader.getBytes());
            log.info("write tap body");
            fos.write(tapBody.getBytes());

            fos.flush();
            log.info("done!");
        } catch (IOException ioex) {
            log.warn(ioex.getMessage(), ioex);
            throw ioex;
        }                       
    }
            
}   // Data2tap.java