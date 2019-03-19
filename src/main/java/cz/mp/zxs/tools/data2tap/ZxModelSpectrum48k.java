/*
 * ZxModelSpectrum48k.java
 *
 *  created: 12.3.2019
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author Martin Pokorný
 */
public class ZxModelSpectrum48k extends ZxModel {

    private static ZxModelSpectrum48k instance;
    
    private static final int RAM_SIZE = 49152;   // 48k

    private List<MemoryAddress> suggestions;
            
    /** */
    private ZxModelSpectrum48k() {
        super("ZX Spectrum 48K  nebo  ZX Spectrum+");
        
        ArrayList<MemoryAddress> list = 
                new ArrayList<MemoryAddress>(10);
        list.add(MemoryAddress.SCREEN);
        list.add(MemoryAddress.SCREEN_ATTRIBS);
        list.add(MemoryAddress.PRINT_BUFFER);
        list.add(MemoryAddress.UDG_48K);
        suggestions = Collections.unmodifiableList(list);
    }
    
    public static ZxModelSpectrum48k get() {
        if (instance == null) {
            instance = new ZxModelSpectrum48k();
        }
        return instance;
    }

    @Override
    public List<MemoryAddress> getMemoryAdressSuggestions() {
        return suggestions;
    }

    @Override
    public int getRamSize() {
        return RAM_SIZE;
    }

    @Override
    public int getRamAddresMin() {
        return MemoryAddress.ZXS_RAM_BEGINING.getAddress();
    }

    @Override
    public int getRamAddresMax() {
        return MemoryAddress.P_RAMT_48K.getAddress();    // 65535 0xFFFF
    }
    
}   // ZxModelSpectrum48k.java
