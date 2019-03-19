/*
 * StandardDialogsLocaliser.java
 *
 *  created: 19.3.2019
 *  charset: UTF-8
 */

package cz.mp.utils;

import java.util.Collections;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.UIManager;


/**
 *
 * @author Martin Pokorný
 */
public class StandardDialogsLocaliser {

    /** */
    private StandardDialogsLocaliser() {
    }

    /**
     * 
     */
    public static final Set<String> KEYS;
    static {
        Set<String> allKeys = new TreeSet<String>();
        
        // --- FileChooser
        allKeys.add("FileChooser.saveButtonText");
        allKeys.add("FileChooser.openButtonText");
        allKeys.add("FileChooser.cancelButtonText");
        allKeys.add("FileChooser.saveInLabelText");
        allKeys.add("FileChooser.lookInLabelText");
        allKeys.add("FileChooser.fileNameLabelText");
        allKeys.add("FileChooser.filesOfTypeLabelText");
        allKeys.add("FileChooser.acceptAllFileFilterText");
        
        allKeys.add("FileChooser.upFolderToolTipText");
        allKeys.add("FileChooser.homeFolderToolTipText");
        allKeys.add("FileChooser.newFolderToolTipText");
        allKeys.add("FileChooser.listViewButtonToolTipText");
        allKeys.add("FileChooser.detailsViewButtonToolTipText");
        allKeys.add("FileChooser.desktopButtonToolTipText");
        allKeys.add("FileChooser.saveButtonToolTipText");
        allKeys.add("FileChooser.openButtonToolTipText");
        allKeys.add("FileChooser.cancelButtonToolTipText");
        
        allKeys.add("FileChooser.listViewActionLabelText");
        allKeys.add("FileChooser.detailsViewActionLabelText");
        
        // --- OptionPane
        allKeys.add("OptionPane.yesButtonText");
        allKeys.add("OptionPane.noButtonText");
        allKeys.add("OptionPane.cancelButtonText");
        allKeys.add("OptionPane.okButtonText");
        
        KEYS = Collections.unmodifiableSet(allKeys);
    }

    private static ResourceBundle lastRes = null;

    /**
     * Lokalizuje texty v {@link JFileChooser}, pokud jsou v zadaném
     * {@code ResourceBundle} definovány hodnoty ke klíčům v
     * {@linkplain #KEYS}. Pokud ne, zůstanou původní popisky.
     * 
     * @param res
     */
    public static void localize(ResourceBundle res) {
        if (lastRes == res) {
            return;
        }

        for (String key : KEYS) {
            try {
                String value = res.getString(key);
                if (value != null && ! value.isEmpty()) {
                    UIManager.put(key, res.getString(key));
                }
            } catch (MissingResourceException ex) {
                // nic
            }
        }
        lastRes = res;
    }

}   // StandardDialogsLocaliser.java
