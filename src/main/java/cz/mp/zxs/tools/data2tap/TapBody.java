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
    
    private byte[] data = new byte[]{};
    /** index dalšího volného bytu v data */
    private int idx;    
    
    /** délka samotných dat. */
    private int dataOnlySize;
    
    private byte parity = flag;    // počítá se
    
    /**
     * 
     * @param size  délka samotných dat. 
     *      Nezapočítává se flag,parita,délka bloku.
     *      ({@code 1 až (48KiB-1)} (viz {@linkplain ZxsConstants#RAM_SIZE__48K}))
     * @throws IllegalArgumentException
     */
    public TapBody(int size) {
        if (size < 1 || size > (ZxsConstants.RAM_SIZE__48K-1)) {
            throw new IllegalArgumentException("size");
        }
        dataOnlySize = size;
        int len = size + 2;  // 2 za flag a parity
        lenLsb = (byte) (len & 0xFF);
        lenMSB = (byte) ((len >> 8) & 0xFF);
        int dataLen = size + 4;  // 2 za len, 2 za flag a parity
        data = new byte[dataLen];
        idx = 0;
        data[idx++] = lenLsb;
        data[idx++] = lenMSB;
        data[idx++] = flag;
    }

    /**
     * 
     * @param value 
     */
    public void append(byte value) {
        data[idx++] = value;
        parity ^= value;
    }
    
    public boolean isFull() {
        return idx >= data.length-1;    // (poslední pozice je pro paritu)
    }

    /**
     * 
     * @param values
     */
    public void append(byte... values) {
        for(int i=0; i<values.length; i++) {
            data[idx++] = values[i];
            parity ^= values[i];            
        }
    }
    
    /**
     * 
     */
    public void appendParityToLastByte() {
        if (data.length == 0) {
            throw new IllegalStateException("data");
        }
        data[idx] = parity;
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
        return data;
    }    
    
    /**
     * 
     * @return velikost celého těla v bytech
     * @see #getDataSize() 
     */
    public int getSize() {
        return data.length;
    }    
    
    /**
     * 
     * @return délka samotných dat
     */
    public int getDataSize() {
        return dataOnlySize;
    }
}   // TapData.java
