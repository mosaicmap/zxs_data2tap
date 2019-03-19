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
import static cz.mp.utils.TextSource.*;

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
        
        ExtFileFilter scrExtFileFilter = new ExtFileFilter(getLocText("gui.file_dialog.filetype.tap"), "scr");
        fileChooser.addChoosableFileFilter(scrExtFileFilter);
        ExtFileFilter binExtFileFilter = new ExtFileFilter(getLocText("gui.file_dialog.filetype.bin"), "bin");
        fileChooser.addChoosableFileFilter(binExtFileFilter);

        fileChooser.setCurrentDirectory(new File("."));
        
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);        
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setDialogTitle(getLocText("gui.file_dialog.load_data.title"));
        
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
        
        ExtFileFilter tapExtFileFilter = new ExtFileFilter(getLocText("gui.file_dialog.filetype.tap"), "tap");
        fileChooser.addChoosableFileFilter(tapExtFileFilter);
        fileChooser.setFileFilter(tapExtFileFilter);
        fileChooser.setCurrentDirectory(new File("."));

        //fileChooser.setAcceptAllFileFilterUsed(false);

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);        
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.setDialogTitle(getLocText("gui.file_dialog.save_tap.title"));
        
        log.debug("fileChooser initialized");
        return fileChooser;
    }
    
    /** */
    private FileDialogBuilder() {
    }


}   // FileDialogBuilder.java
