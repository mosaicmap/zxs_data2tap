/*
 * Main.java
 *
 *  created: 3.10.2017
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap;

import static cz.mp.zxs.tools.data2tap.Version.VERSION;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code data2tap} převádí zadáná binární data na TAP soubor ve formátu 
 * pro ZX Spectrum.
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
    public static void main(String[] args) throws IOException {
        log.info("version: " + VERSION);

        new Data2tapCli().executeWithArgs(args);
    }

}   // Main.java
