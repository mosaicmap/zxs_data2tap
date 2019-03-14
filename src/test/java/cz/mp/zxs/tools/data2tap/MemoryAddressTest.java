/*
 * MemoryAddressTest.java
 *
 *  created: 14.3.2019
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 *
 * @author Martin Pokorný
 */
public class MemoryAddressTest {

    @Test
    public void testGetAddress_dec() {
        assertEquals(String.valueOf(MemoryAddress.SCREEN), 
                MemoryAddress.SCREEN.getAddressDec());
    }
            
    @Test
    public void testAddressToInt_dec() {
        int result = MemoryAddress.addressToInt("30000");
        assertEquals(30000, result);
        
        result = MemoryAddress.addressToInt("000");
        assertEquals(0, result);
        
        result = MemoryAddress.addressToInt("65535");
        assertEquals(65535, result);

        result = MemoryAddress.addressToInt("-1");
        assertEquals(-1, result);
    }

    @Test
    public void testAddressToInt_hex() {
        int result = MemoryAddress.addressToInt("0x7530");
        assertEquals(30000, result);
        
        result = MemoryAddress.addressToInt("0xFF");
        assertEquals(255, result);

        result = MemoryAddress.addressToInt("0xfe");
        assertEquals(254, result);

        result = MemoryAddress.addressToInt("0x00FE");
        assertEquals(254, result);
        
        result = MemoryAddress.addressToInt("0x0");
        assertEquals(0, result);
        
        result = MemoryAddress.addressToInt("0xFFFF");
        assertEquals(65535, result);

        result = MemoryAddress.addressToInt("0xFFff");
        assertEquals(65535, result);
    }

    @Test(expected = NumberFormatException.class)
    public void testAddressToInt_wrongNumberFormat01() {
        MemoryAddress.addressToInt("0x753Z");
    }

    @Test(expected = NumberFormatException.class)
    public void testAddressToInt_wrongNumberFormat02() {
        MemoryAddress.addressToInt("75_");
    }

    @Test(expected = NumberFormatException.class)
    public void testAddressToInt_wrongNumberFormat03() {
        MemoryAddress.addressToInt("");
    }
    
}   // MemoryAddressTest.java