/*
 * Main.java
 *
 *  created: 3.10.2017
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap;

import static cz.mp.zxs.tools.data2tap.Version.VERSION;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
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
    
//    /** */
//    private static void printVersion() {
//        System.out.println(VERSION);
//    }

    /** */
    public static void main(String[] args) throws IOException {
        log.info("version: " + VERSION);

//        Files.write(new File("random_data_1.bin").toPath(), new byte[]{48, 49, 50, 51, 52},
//                StandardOpenOption.CREATE_NEW,
//                StandardOpenOption.WRITE,
//                StandardOpenOption.TRUNCATE_EXISTING);
        
        new Data2tapCli().executeWithArgs(args);
        
    }

}   // Main.java
