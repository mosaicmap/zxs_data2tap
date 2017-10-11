/*
 * TapBlockTypeTest.java
 *
 *  created: 29.9.2017
 *  charset: UTF-8
 */
package cz.mp.zxs.tools.data2tap;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin Pokorný
 */
public class TapBlockTypeTest {
    
    @Test
    public void testGetByNum() {
        TapBlockType result;
        
        result = TapBlockType.getByNum(0);
        assertEquals(TapBlockType.BASIC, result);

        result = TapBlockType.getByNum(1);
        assertEquals(TapBlockType.NUMBERS, result);

        result = TapBlockType.getByNum(2);
        assertEquals(TapBlockType.TEXTS, result);

        result = TapBlockType.getByNum(3);
        assertEquals(TapBlockType.CODE_OR_SCREEN, result);

        result = TapBlockType.getByNum(4);
        assertNull(result);
    }
    
}
