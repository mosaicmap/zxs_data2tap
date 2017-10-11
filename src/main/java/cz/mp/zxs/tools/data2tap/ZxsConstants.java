/*
 * ZxsConstants.java
 *
 *  created: 4.10.2017
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap;


/**
 *
 * @author Martin Pokorný
 * @version 0.1
 */
public class ZxsConstants {

    /** Jen bitmapa (bez barevných atributů) */
    public static final int SCREEN_BITMAP_LENGTH = 6144;   // = 24 * 32 * 8
    /** Jen atributy (barvy) (bez bitmapy) */
    public static final int SCREEN_ATTRIB_LENGTH = 768;   // = 24 * 32
    /** Velikost obrazovky v bytech */
    public static final int SCREEN_LENGTH = 6912;   // = SCREEN_BITMAP_LENGTH + SCREEN_ATTRIB_LENGTH

    public static final int ADR_SCREEN_BITMAP  = 0x4000;  // 0x4000 = 16384
    public static final int ADR_SCREEN_BITMAP_MAX = 0x57FF;
    public static final int ADR_SCREEN_ATTRIBS = 0x5800;  // 0x5B00 = 22528
    public static final int ADR_SCREEN_MAX = 0x5AFF;  
    public static final int ADR_PRINT_BUFFER = 0x5B00;  // 0x5B00 = 23296
    
    public static final int UDG_LENGTH = 168;   // 21 * 8
    
    /** Adresa UDG oblasti pro ZXS 16k. */
    public static final int ADR_UDG__16K = 0x7F58;
    /** Adresa UDG oblasti pro ZXS 48k. */
    public static final int ADR_UDG__48K = 0xFF58;
    
    public static final int ADR_MAX__48K = 0xFFFF;  // 0xFFFF = 65535
    public static final int ADR_MAX__16K = 0x7FFF;
    public static final int ADR_MIN = 0x4000;   // 0x4000 = 16384
    
    public static final int RAM_SIZE__48K = ADR_MAX__48K - ADR_MIN;
    public static final int RAM_SIZE__16K = ADR_MAX__16K - ADR_MIN;
    
    public static final int TAP_MIN_SIZE = 2 + TapHeader.HEADER_DEFAULT_SIZE + 5;  // (2B za blocklen na začátku) (5 = 2B za blocklen na začátku dalšího bloku + 1B flag + 1B data + 1B parita) 

    
    private ZxsConstants() {
    }

}   // ZxsConstants.java
