/*
 * Main.java
 *
 *  created: 3.10.2017
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap;

import static cz.mp.zxs.tools.data2tap.Version.VERSION;
import cz.mp.zxs.tools.data2tap.gui.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code data2tap} pro ZX Spectrum převádí zadáná data na TAP soubor.
 * <p>
 * Výsledný TAP lze spojit s jinými TAP soubory 
 * (např. s TAP s 'loaderem' v Basicu)
 *
 * @author Martin Pokorný
 */
public class Main {
    private static final Logger log;
    static {
        if (System.getProperty("java.util.logging.config.file") == null) {
            System.setProperty("java.util.logging.config.file", "logging.properties");
        }
        log = LoggerFactory.getLogger(Main.class);
    }
    
    /** */
    private static void printVersion() {
        System.out.println(VERSION);
    }

    /** */
    public static void main(String[] args) {
        log.info("version: " + VERSION);

        boolean optVersion = false;

        for(int i = 0; i < args.length; i++) {
            String opt = args[i].trim().toLowerCase();
            if (opt.equals("--version")) {
                optVersion = true;
            }
        }
        if (optVersion) {
            printVersion();
            System.exit(0);
        }

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.info("create and show GUI");
                MainFrame.getInstance().setVisible(true);
            }
        });
    }

}   // Main.java
