/*
 * TapData.java
 *
 *  created: 4.10.2017
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Blok dat v TAP souboru následující za hlavičkou.
 *
 * @author Martin Pokorný
 * @see TapHeader
 */
public class TapBody {

    private static final Logger log = LoggerFactory.getLogger(TapBody.class);
    
    public static final byte FLAG_DATA = (byte)0xFF;
        
    private byte lenLsb;
    private byte lenMSB;
    private byte flag = FLAG_DATA;
    
    private byte[] bodyData = new byte[]{};
    /** index dalšího volného bytu v data */
    private int idx;    
    
    /** délka samotných dat. */
    private int rawDataSize;
    
    private byte parity = flag;    //parita se počítá průběžně v této třídě
    
    /**
     * 
     * @param rawDataSize  délka samotných dat v bytech. 
     *      Nezapočítává se flag,parita,délka bloku.
     *      ({@code 1 až (48KiB-1)} (viz {@linkplain ZxsConstants#RAM_SIZE__48K}))
     * @throws IllegalArgumentException
     * @see #append(byte...) 
     * @see #append(byte) 
     * @see #appendParityToLastByte()
     */
    public TapBody(int rawDataSize) {
        initImpl(rawDataSize);
    }

    /**
     * 
     * @param rawDataSize  délka samotných dat v bytech. 
     *      Nezapočítává se flag,parita,délka bloku.
     * @throws IllegalArgumentException
     */
    private void initImpl(int rawDataSize) {
        log.debug("rawDataSize = " + rawDataSize);
        if (rawDataSize < 1) {
            throw new IllegalArgumentException("rawDataSize");
        }
        this.rawDataSize = rawDataSize;
        int len = rawDataSize + 2;  // 2 za flag a parity
        lenLsb = (byte) (len & 0xFF);
        lenMSB = (byte) ((len >> 8) & 0xFF);
        int bodyLen = rawDataSize + 4;  // 2 za len, 1 za flag, 1 za paritu
        log.debug("bodyLen = " + bodyLen);
        bodyData = new byte[bodyLen];
        idx = 0;
        bodyData[idx++] = lenLsb;
        bodyData[idx++] = lenMSB;
        bodyData[idx++] = flag;
    }
    
    /**
     * Přidá do těla zadaná binární data. (Parita se touto metodou nepřidává.)
     * 
     * @param value 
     * @see #append(byte...) 
     * @see #appendParityToLastByte() 
     */
    public void append(byte value) {
        bodyData[idx++] = value;
        parity ^= value;
    }
    
    public boolean isFull() {
        return idx >= bodyData.length-1;    // (poslední pozice je pro paritu)
    }

    /**
     * Přidá do těla zadaná binární data. (Parita se touto metodou nepřidává.)
     * 
     * @param values
     * @see #appendParityToLastByte() 
     */
    public void append(byte... values) {
        for(int i=0; i<values.length; i++) {
            bodyData[idx++] = values[i];
            parity ^= values[i];            
        }
    }
    
    /**
     * Po přidání všech dat zavolat tuto metodu pro přidání parity.
     */
    public void appendParityToLastByte() {
        if (bodyData.length == 0) {
            throw new IllegalStateException("data");
        }
        bodyData[idx] = parity;
    }
        
    /**
     * Získá obsah datového bloku TAP.
     * Před zavoláním této metody je třeba zavolat metodu {@link #appendParityToLastByte()},
     * která zapíše na konec dat paritu.
     * 
     * @return 
     * @see #appendParityToLastByte()
     */
    public byte[] getBytes() {
        return bodyData;
    }    
    
    /**
     * 
     * @return velikost celého těla v bytech
     * @see #getRawDataSize() 
     */
    public int getBodySize() {
        return bodyData.length;
    }    
    
    /**
     * 
     * @return délka samotných dat
     */
    public int getRawDataSize() {
        return rawDataSize;
    }
}   // TapData.java
