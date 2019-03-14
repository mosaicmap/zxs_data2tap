/*
 * ZxModel.java
 *
 *  created: 12.3.2019
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap;

import java.util.List;


/**
 *
 * @author Martin Pokorný
 * @see MemoryAddress
 */
public abstract class ZxModel {

    private String name;
    
    /**
     * 
     * @param name
     */
    public ZxModel(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name is blank");
        }
        this.name = name;
    }

    abstract public List<MemoryAddress> getMemoryAdressSuggestions();

    /** Max velikost RAM. Pozor, neodpovídá velikosti adresního prostoru. */
    abstract public int getRamSize();
     
    abstract public int getRamAddresMin();

    abstract public int getRamAddresMax();

    public boolean isValidAddress(int address) {
        return address >= getRamAddresMin() && address <= getRamAddresMax();
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}   // ZxModel.java
