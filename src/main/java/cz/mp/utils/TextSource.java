/*
 * TextSource.java
 *
 *  created: 19.3.2019
 *  charset: UTF-8
 */

package cz.mp.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


/**
 * Zdroj lokalizovaných textů.
 * 
 * @author Martin Pokorný
 */
public class TextSource {

    /** Zdroj lokalizovaných textů pro výchozí jazyk. */
    public static final ResourceBundle MAIN = 
            PropertyResourceBundle.getBundle(
                "texts.Main",
                Locale.getDefault() );
    
    /** */
    private TextSource() {
    }

    /** 
     * Získání formátovaného lokalizovaného textu. 
     * 
     * @param key  klíč lokalizovaného textu
     * @param args  volitelné parametry textu
     * @return  lokalizovaný text nebo text v {@code key}, když ke klíči
     *      není možno najít lokalizovaný text
     * @throws IllegalArgumentException  pokud je {@code key} prázdný.
     */     
    public static String getLocText(String key, Object... args) {
        return getLocText(MAIN, key, args);
    }

    /** 
     * Získání formátovaného lokalizovaného textu. 
     * 
     * @param bundle  zdroj lokalizovaných textů
     * @param key  klíč lokalizovaného textu
     * @param args  volitelné parametry textu
     * @return  lokalizovaný text nebo text v {@code key}, když ke klíči
     *      není možno najít lokalizovaný text
     * @throws IllegalArgumentException  pokud je {@code key} prázdný.
     */     
    public static String getLocText(ResourceBundle bundle, 
            String key, Object... args) {
        if (key == null || key.trim().length() == 0) {
            throw new IllegalArgumentException("resource key is blank");
        }
        try {
            bundle.getString(key);
        } catch (MissingResourceException ex) {
            return key;
        }
        
        return MessageFormat.format(bundle.getString(key), args);
    }
    
}   // TextSource.java
