/*
 * Data2tapTest.java
 *
 *  created: 14.3.2019
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.Assert;
import org.junit.Test;


/**
 *
 * @author Martin Pokorný
 */
public class Data2tapTest {

    @Test
    public void testExecute_testFileData1() {  
        File data1InBin = new File("src/test/resources/files/data_1.bin");
        File data1ExpectedOutTap = new File("src/test/resources/files/data_1.tap");
        
        Data2tap data2tap = new Data2tap();
        data2tap.setTapBlockType(TapBlockType.BINARY_DATA);
        data2tap.setName("data_1");
        data2tap.setAddress(50000);
        
        try {
            byte[] inFileContent = Files.readAllBytes(data1InBin.toPath());
            data2tap.setRawData(inFileContent);
            
            File tempOutFile = File.createTempFile("data_1_", ".tap");
            tempOutFile.deleteOnExit();
            data2tap.setOutTapFile(tempOutFile);            
            
            data2tap.execute();
            
            byte[] tempOutFileContent = Files.readAllBytes(tempOutFile.toPath());
            
            byte[] expectedOutData = Files.readAllBytes(data1ExpectedOutTap.toPath());
            
            Assert.assertArrayEquals(expectedOutData, tempOutFileContent);
            
        } catch (InvalidDataException | IOException ex) {            
            Assert.fail(ex.getMessage());
        }
    }
    
    
    @Test
    public void testExecute_testMaxRam() {
        // data_1.bin --- obsahuje 5 bytů
        File data1InBin = new File("src/test/resources/files/data_1.bin");
        
        Data2tap data2tap = new Data2tap();
        data2tap.setTapBlockType(TapBlockType.BINARY_DATA);
        data2tap.setName("data_1");
        data2tap.setModel(ZxModelSpectrum48k.get());
        data2tap.setAddress(MemoryAddress.P_RAMT_48K.getAddress() - 5 + 1); // poslední adresový byte je platná adresa; 
                
        try {
            byte[] inFileContent = Files.readAllBytes(data1InBin.toPath());
            data2tap.setRawData(inFileContent);
            
            File tempOutFile = File.createTempFile("data_1_", ".tap");
            tempOutFile.deleteOnExit();
            data2tap.setOutTapFile(tempOutFile);            
            
            data2tap.execute();
            
        } catch (InvalidDataException | IOException ex) {            
            Assert.fail(ex.getMessage());
        }        
    }
    
    @Test(expected = InvalidDataException.class)
    public void testExecute_invalidData01() throws InvalidDataException {  
        Data2tap data2tap = new Data2tap();
        data2tap.setTapBlockType(TapBlockType.BINARY_DATA);
        data2tap.setName("data_1");
        data2tap.setAddress(150000);  // adresa mimo adresní prostor

        try {
            data2tap.setRawData(new byte[]{(byte) 0x55});
            
            data2tap.execute(); // --> InvalidDataException
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
        }        
    }
    
    @Test(expected = InvalidDataException.class)
    public void testExecute_invalidData02() throws InvalidDataException {  
        Data2tap data2tap = new Data2tap();
        data2tap.setTapBlockType(TapBlockType.BINARY_DATA);
        data2tap.setName("data_2__fooooooo");   // jméno > 10 znaků
        data2tap.setAddress(30000);

        try {
            data2tap.setRawData(new byte[]{(byte) 0x55});
            
            data2tap.execute(); // --> InvalidDataException
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
        }        
    }    

    @Test(expected = IllegalArgumentException.class)
    public void testSetData_invalidData03() throws InvalidDataException {  
        Data2tap data2tap = new Data2tap();
        data2tap.setRawData(new byte[]{});  // žádná data --> IllegalArgumentException
    } 
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetAddress_invalidData04() throws InvalidDataException {  
        Data2tap data2tap = new Data2tap();
        data2tap.setAddress(-1);    // --> IllegalArgumentException
    }     
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetName_invalidData05() throws InvalidDataException {  
        Data2tap data2tap = new Data2tap();
        data2tap.setName(null);    // --> IllegalArgumentException
    } 
    
}   // Data2tapTest.java