/*
 * TapHeader.java
 *
 *  created: 3.10.2017
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Blok dat v TAP souboru uvozující blok dat.
 * 
 * @author Martin Pokorný
 * @see TapBody
 */
public class TapHeader {

    private static final Logger log = LoggerFactory.getLogger(TapHeader.class);
        
    public static final String DEFAULT_NAME = "          ";  // 10 mezer
    public static final int NAME_LEN = DEFAULT_NAME.length(); // = 10
    public static final int HEADER_DEFAULT_SIZE = 19;
    
    public static final byte FLAG_HEADER = 0;
    
    public static final int PARAM_1_MIN_VALUE_FOR_CODE = 0x4000;    // 0x4000 = 16384
    public static final int PARAM_2_CODE_FLAG = 0x8000;         // 0x8000 = 32768
    
    // délka bez lenLsb a lenMSB
    private byte lenLsb = (byte)HEADER_DEFAULT_SIZE;
    private byte lenMSB = 0;
    
    private byte flag = FLAG_HEADER;
    private TapBlockType type;  // 1B
    
    private String name;
    
    private int dataLen;
    private byte dataLenLsb;
    private byte dataLenMSB;
    
    private int param1;
    private byte param1Lsb;
    private byte param1MSB;
    
    private int param2;
    private byte param2Lsb;
    private byte param2MSB;
    
    private byte parity;    // počítá se
    
    private byte[] data = new byte[]{};
            
    // -----
    public TapHeader() {
    }

    /**
     * 
     * @param type  (pokud je {@linkplain TapBlockType#CODE_OR_SCREEN},
     *      potom se nastaví i param2
     */
    public TapHeader(TapBlockType type) {
        setTypeImpl(type);
    }
        
    /**
     * 
     * @param type  (pokud je {@linkplain TapBlockType#CODE_OR_SCREEN},
     *      potom se nastaví i param2
     */
    public void setType(TapBlockType type) {
        setTypeImpl(type);
    }
    
    /**
     * 
     * @param type  (pokud je {@linkplain TapBlockType#CODE_OR_SCREEN},
     *      potom se nastaví i param2
     */
    private void setTypeImpl(TapBlockType type) {
        if (type == null) {
            throw new IllegalArgumentException("type=null");
        }
        this.type = type;
        
        if (type == TapBlockType.CODE_OR_SCREEN) {
            setParam2Impl(PARAM_2_CODE_FLAG);
        }
    }

    /**
     * 
     * @param name
     * @return 
     */
    public static boolean checkName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name");
        }
        if (name.length() > NAME_LEN) {
            return false;
        }
        for(int i=0; i<name.length(); i++) {
            char ch = name.charAt(i);            
            if (! isGoogCharacterInName(ch)) {
                return false;
            }
        }
        return true;        
    }
    
    /**
     * 
     * @param ch
     * @return 
     */
    private static boolean isGoogCharacterInName(char ch) {
        return (Character.isLetterOrDigit(ch) 
                || ch == ' ' || ch == '_' 
                || ch == '+' || ch == '-' 
                || ch == '.' || ch == ',' || ch == ';')
                && 
                (ch >= 0x20 || ch <= 0x7F);
    }
    
    /**
     * 
     * @param name  0 - 10 znaků
     * @throws IllegalArgumentException
     * @see #checkName(java.lang.String) 
     */
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name");
        }
        if (name.length() > NAME_LEN) {
            throw new IllegalArgumentException("name.length > " + NAME_LEN);
        }
        // test zda neobsahuje vadný znak
        if (checkName(name) == false) {
            throw new IllegalArgumentException("name contains illegal characters");
        }
        this.name = String.format("%-10s", name);
    }
    
    /**
     * 
     * @param length  délka samotných dat <i>následujího</i> bloku.
     *      Nezapočítává se flag,parita,délka bloku; jde opravdu jen o délku dat.
     * @throws IllegalArgumentException
     */
    public void setDataLength(int length) {
        if (length < 0 || length > ZxsConstants.ADR_MAX__48K) {
            throw new IllegalArgumentException("length");
        }
        this.dataLen = length;
        dataLenLsb = get16bitLsb(length);
        dataLenMSB = get16bitMSB(length);        
    }
       
    /**
     * 
     * @param param1 
     * @throws IllegalArgumentException
     */
    public void setParam1(int param1) {
        if (param1 < 0 || param1 > ZxsConstants.ADR_MAX__48K) {
            throw new IllegalArgumentException("param1");
        }
        if (type == TapBlockType.CODE_OR_SCREEN) {
            if (param1 < PARAM_1_MIN_VALUE_FOR_CODE) {
                throw new IllegalArgumentException("param1 < PARAM_1_MIN_VALUE_FOR_CODE");
            }
        }
        this.param1 = param1;
        param1Lsb = get16bitLsb(param1);
        param1MSB = get16bitMSB(param1);
    }
    
    /**
     * 
     * @param param2 
     * @throws IllegalArgumentException
     */
    public void setParam2(int param2) {
        setParam2Impl(param2);
    }
    
    /**
     * 
     * @param param2 
     * @throws IllegalArgumentException
     */
    private void setParam2Impl(int param2) {
        if (param2 < 0 || param2 > ZxsConstants.ADR_MAX__48K) {
            throw new IllegalArgumentException("param2");
        }
        this.param2 = param2;
        param2Lsb = get16bitLsb(param2);
        param2MSB = get16bitMSB(param2);        
    }
    
    /**
     * 
     * @throws IllegalStateException
     */
    public void createData() {
        if (dataLen < 0 || dataLen > ZxsConstants.ADR_MAX__48K) {
            throw new IllegalStateException("dataLen");
        }
        if (param1 < 0 || param1 > ZxsConstants.ADR_MAX__48K) {
            throw new IllegalStateException("param1");
        }
        if (param2 < 0 || param2 > ZxsConstants.ADR_MAX__48K) {
            throw new IllegalStateException("param2");
        }
        
        data = new byte[HEADER_DEFAULT_SIZE + 2];    // +2 je za lenLsb a lenMSB
        int i=0;
        data[i++] = lenLsb;
        //log.debug("lenLsb = 0x" + Integer.toHexString(lenLsb));
        data[i++] = lenMSB;        
        data[i++] = flag;   parity = flag;
        data[i++] = (byte)type.getNum();   parity ^= (byte)type.getNum();
        for (int j=0; j<name.length(); j++) {
            data[i++] = (byte)name.charAt(j);
            parity ^= (byte)name.charAt(j);
        }
        data[i++] = dataLenLsb;   parity ^= dataLenLsb;
        data[i++] = dataLenMSB;   parity ^= dataLenMSB;
        data[i++] = param1Lsb;   parity ^= param1Lsb;
        data[i++] = param1MSB;   parity ^= param1MSB;
        data[i++] = param2Lsb;   parity ^= param2Lsb;
        data[i++] = param2MSB;   parity ^= param2MSB;
        data[i++] = parity;        
    }
    
    /**
     * Získá obsah hlavičky TAP.
     * Před zavoláním této metody je třeba zavolat {@linkplain #createData()}
     * 
     * @return 
     * @throws IllegalStateException
     * @see #createData() 
     */
    public byte[] getBytes() {
        if (data.length == 0) {
            throw new IllegalStateException("data");
        }        
        return data;
    }
    
    /**
     * 
     * @param value
     * @return 
     */
    private static byte get16bitLsb(int value) {
        return (byte) (value & 0xFF);
    }

    /**
     * 
     * @param value
     * @return 
     */
    private static byte get16bitMSB(int value) {
        return (byte) ((value >> 8) & 0xFF);
    }
    
}   // TapHeader.java
