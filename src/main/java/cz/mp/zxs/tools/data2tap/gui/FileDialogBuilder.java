/*
 * FileDialogBuilder.java
 *
 *  created: 5.10.2017
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap.gui;

import cz.mp.zxs.tools.data2tap.misc.ExtFileFilter;
import java.io.File;
import javax.swing.JFileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Martin Pokorný
 */
public class FileDialogBuilder {

    private static final Logger log = LoggerFactory.getLogger(FileDialogBuilder.class);
            
    /**
     * Vytvoří a inicializuje dialog pro výběr souboru pro načtení 
     * binárního souboru (jako je např SCR).
     * 
     * @return 
     */
    public static JFileChooser createLoadBinaryFileChooser() {
        log.debug("");
        JFileChooser fileChooser = new JFileChooser();
        
        ExtFileFilter scrExtFileFilter = new ExtFileFilter("ZX Spectrum SCREEN$ image", "scr");
        fileChooser.addChoosableFileFilter(scrExtFileFilter);
        fileChooser.setFileFilter(scrExtFileFilter);
        fileChooser.setCurrentDirectory(new File("."));

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);        
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        //fileChooser.setApproveButtonText("Open");
        fileChooser.setDialogTitle("Load binary file");
        
        log.debug("fileChooser initialized");
        return fileChooser;
    }
    
    /**
     * Vytvoří a inicializuje dialog pro výběr souboru pro uložení TAP souboru.
     *
     * @return 
     */
    public static JFileChooser createSaveTapFileChooser() {
        log.debug("");
        JFileChooser fileChooser = new JFileChooser();
        
        ExtFileFilter tapExtFileFilter = new ExtFileFilter("ZX Spectrum TAP file", "tap");
        fileChooser.addChoosableFileFilter(tapExtFileFilter);
        fileChooser.setFileFilter(tapExtFileFilter);
        fileChooser.setCurrentDirectory(new File("."));

        //fileChooser.setAcceptAllFileFilterUsed(false);

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);        
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        //fileChooser.setApproveButtonText("Open");
        fileChooser.setDialogTitle("Create TAP");
        
        log.debug("fileChooser initialized");
        return fileChooser;
    }
    
    /** */
    private FileDialogBuilder() {
    }


}   // FileDialogBuilder.java
