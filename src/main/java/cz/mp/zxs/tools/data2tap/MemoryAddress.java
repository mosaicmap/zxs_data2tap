/*
 * MemoryAddress.java
 *
 *  created: 12.3.2019
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap;

/**
 *
 * @author Martin Pokorný
 */
public enum MemoryAddress {
    /** Začátek RAM po ROM. 16384 (0x4000). */
    ZXS_RAM_BEGINING(16384, "0x4000", "RAM Begining"),
    /** Oblast video paměti. Po bitové mapě následuje oblast atributů. */
    SCREEN(16384, "0x4000", "Screen memory"),
    /** Oblast atributů ve video paměti. */
    SCREEN_ATTRIBS(22528, "0x5800", "Screen memory Attributes"),
    /** Print buffer. Pokud se nebude tisknout, lze využívat, třeba pro 
     * rutiny ve strojovém kódu */
    PRINT_BUFFER(23296, "0x5B00", "Printer Buffer"),
    /** Systémové proměnné. */
    SYS_VARS(23552, "0x5C00", "System Variables"),

    UDG_16K(32600, "0x7F58", "UDG"),
    P_RAMT_16K(32767, "0x7FFF", "P_RAMT"),
    UDG_48K(65368, "0xFF58", "UDG"),
    P_RAMT_48K(65535, "0xFFFF", "P_RAMT"),
    ;
    
    int address;
    String addressDec;
    String addressHex;
    String description;

    /**
     * 
     * @param address
     * @param addressHex
     * @param description  (používá se v GUI pro kombo s adresami)
     */
    MemoryAddress (int address, String addressHex, String description) {
        this.address = address;
        this.addressDec = String.valueOf(address);
        this.addressHex = addressHex;
        this.description = description;
    }

    public int getAddress() {
        return address;
    }

    public String getAddressDec() {
        return addressDec;
    }

    public String getAddressHex() {
        return addressHex;
    }
    
    public String getDescription() {
        return addressHex + "  " + description;
    }
    
    @Override
    public String toString() {
        return addressDec;
    }
    
    /**
     * Převede číslo v textovém formátu zadané desítkově nebo šestnáctkově 
     * na číslo typu int.
     * 
     * @param rawAddress
     * @return 
     * @throws NumberFormatException
     */
    public static int addressToInt(String rawAddress) {
        int result;
        if (rawAddress.startsWith("0x")) {
            result = Integer.parseInt(rawAddress.substring(2), 16);
        }
        else {
            result = Integer.parseInt(rawAddress, 10);
        }  
        return result;
    }
            
}   // MemoryAddress.java