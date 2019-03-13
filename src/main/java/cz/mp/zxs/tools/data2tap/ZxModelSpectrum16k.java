/*
 * ZxModelSpectrum16k.java
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
public class ZxModelSpectrum16k extends ZxModel {
    
    private static ZxModelSpectrum16k instance;
    
    private static final int RAM_SIZE = 16384;   // 16k
    
    private List<MemoryAddress> suggestions;
            
    /** */
    private ZxModelSpectrum16k() {
        super("ZX Spectrum 16K");
        
        ArrayList<MemoryAddress> list = 
                new ArrayList<MemoryAddress>(10);
        list.add(MemoryAddress.SCREEN);
        list.add(MemoryAddress.SCREEN_ATTRIBS);
        list.add(MemoryAddress.PRINT_BUFFER);
        list.add(MemoryAddress.UDG_16K);
        suggestions = Collections.unmodifiableList(list);
    }
    
    public static ZxModelSpectrum16k get() {
        if (instance == null) {
            instance = new ZxModelSpectrum16k();
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
        return MemoryAddress.ZXS_RAM_BEGINING.address;
    }

    @Override
    public int getRamAddresMax() {
        return MemoryAddress.P_RAMT_16K.address;    // 32767 0x7FFF
    }

}   // ZxModelSpectrum16k.java
