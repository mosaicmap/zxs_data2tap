/*
 * Images.java
 *
 *  created: 4.10.2017
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap.gui;

import cz.mp.zxs.tools.data2tap.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Martin Pokorný
 */
public class Images {
    private static final Logger log = LoggerFactory.getLogger(Images.class);

    private static final String IMG_RESOURCE = "/images/";

    /** */
    private Images() {
    }

    /**
     * Seznam obrázků;
     * klíč je jméno souboru obrázku.
     */
    private static Map<String, ImageIcon> images =
            new HashMap<String, ImageIcon>();

//    public static final String ICON_16 = "icon_16.png";
    public static final String ICON_32 = "icon_32.png";
//    public static final String ICON_64 = "icon_64.png";


    /**
     *
     * @param imgName  jméno obrázku např.
     *      {@linkplain #OK}, {@linkplain #CANCEL}
     * @return
     *      {@code null}, pokud není zadané jméno definováno, jinak ikonu.
     */
    public static ImageIcon getImage(String imgName) {
        //log.trace("imgName = " + imgName);
        if (!images.containsKey(imgName)) {
            URL imgURL = Images.class.getResource(IMG_RESOURCE + imgName);
            if (imgURL != null) {
                images.put(imgName, new ImageIcon(imgURL));
            }
            else {
                log.error("imgName " + imgName + "  NOT FOUND !");
            }
        }
        return images.get(imgName);
    }
    
}   // Images.java
