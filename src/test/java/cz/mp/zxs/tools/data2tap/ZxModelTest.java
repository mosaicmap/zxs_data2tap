/*
 * ZxModelTest.java
 *
 *  created: 15.3.2019
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 *
 * @author Martin Pokorný
 */
public class ZxModelTest {

    @Test
    public void testIsValidAddress() {
        ZxModel m = ZxModelSpectrum48k.get();
        assertTrue(m.isValidAddress(m.getRamAddresMin()));
        assertTrue(m.isValidAddress(m.getRamAddresMin()+1));
        assertFalse(m.isValidAddress(m.getRamAddresMin()-1));
        assertTrue(m.isValidAddress(m.getRamAddresMax()));
        assertTrue(m.isValidAddress(m.getRamAddresMax()-1));
        assertFalse(m.isValidAddress(m.getRamAddresMax()+1));
        assertFalse(m.isValidAddress(-1));
        assertFalse(m.isValidAddress(130000));
    }

}   // ZxModelTest.java
