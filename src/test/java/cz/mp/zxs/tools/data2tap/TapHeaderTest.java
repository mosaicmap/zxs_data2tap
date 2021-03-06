/*
 * TapHeaderTest.java
 *
 *  created: 10.10.2017
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin Pokorný
 */
public class TapHeaderTest {

    @Test
    public void testCheckName() {
        Assert.assertTrue(TapHeader.checkName("01"));
        Assert.assertTrue(TapHeader.checkName("Abc Abc 9-"));
        Assert.assertTrue(TapHeader.checkName(""));
        Assert.assertTrue(TapHeader.checkName("\\/-.,;\"\'"));
        Assert.assertFalse(TapHeader.checkName("ň"));
        Assert.assertFalse(TapHeader.checkName("opqamopqamZ"));
    }
    
    @Test
    public void testCreateData() {
        TapHeader tapHeader = new TapHeader();
        tapHeader.setType(TapBlockType.BINARY_DATA);
        tapHeader.setName("test");
        tapHeader.setParam1(0x4000);
        tapHeader.setDataLength(128);
        tapHeader.createData();
        byte[] result = tapHeader.getBytes();
        
        assertEquals(21, result.length);    // 19 + 2 za délku hlavičky
                
        byte[] expected = new byte[] {
            (byte)0x13, (byte)0x0,  // delka hlavicky (bez této délky) - lsb,Msb (0x13=19)
            (byte)0x0,   // flag pro header
            (byte)0x03,  // type
            (byte)0x74, (byte)0x65, (byte)0x73, (byte)0x74,    // test
            (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, // mezery do délky do 10 znaků
            (byte)0x80, (byte)0x0,  // delka dat následujícího bloku - lsb,Msb (0x80=128)
            (byte)0x0, (byte)0x40,  // Parameter 1 - lsb,Msb (u typu 3 má hodnotu adresy)
            (byte)0x0, (byte)0x80,  // Parameter 2 - lsb,Msb (u typu 3 je 0x8000)
            (byte)0x55,  // parita
        };
        
        assertArrayEquals(expected, result);
    }
    
    @Test(expected = IllegalStateException.class)
    public void testTapHeaderNoFinishErr() {
        TapHeader tapHeader = new TapHeader();
        tapHeader.setType(TapBlockType.BINARY_DATA);
        tapHeader.setName("test");
        tapHeader.setParam1(0x4000);
        tapHeader.setDataLength(128);
        //tapHeader.createData();
        tapHeader.getBytes();   // bez createData() -> IllegalStateException
    }

    
}   // TapHeaderTest.java
