/*
 * TapBlockType.java
 *
 *  created: 25.4.2017
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap;


/**
 * Typ bloku v TAP souboru.
 *
 * @author Martin Pokorný
 * @see TapHeader 
 * @see Data2tap
 */
public enum TapBlockType {
    BASIC(0, "BASIC program"),
    NUMBERS(1, "Data: numbers"),
    TEXTS(2, "Data: texts"),
    /** SCREEN$ / MC / UDG, Fonts, Sprites, ... */
    BINARY_DATA(3, "Binary data"),
    ;
    
    int num;
    String description;

    private TapBlockType(int num, String description) {
        this.num = num;
        this.description = description;
    }

    public int getNum() {
        return num;
    }

    public String getDescription() {
        return description;
    }

    public static TapBlockType getByNum(int num) {
        for (TapBlockType type : values()) {
            if (type.getNum() == num) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {        
        return num + " \u2013 " + description;
    }
       
}   // TapBlockType
