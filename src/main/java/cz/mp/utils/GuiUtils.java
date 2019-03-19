/*
 * GuiUtils.java
 *
 *  created: 4.10.2017
 *  charset: UTF-8
 */

package cz.mp.utils;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.UIManager;


/**
 *
 * @author Martin Pokorný
 */
public class GuiUtils {

    /** */
    private GuiUtils() {
    }

    /**
     * Vrátí komponentu, která má jen min šířku.
     * Pomůcka pro zajištění min. velikosti komponenty v GridBagLayoutu.
     *
     * @param minWidth
     * @param prefferWidth
     * @return
     */
    public static JLabel createMinWidthFoobar(int minWidth, int prefferWidth) {
        JLabel label = new JLabel("");
        label.setMinimumSize(new Dimension(minWidth, 0));
        if (prefferWidth >= minWidth) {
            label.setPreferredSize(new Dimension(prefferWidth, 0));
        }
        return label;
    }
    
    /**
     * Vrátí šedavou barvu. 
     * 
     * @return 
     */
    public static Color getDisabledLabelColor() {
        if (UIManager.getColor("Label.disabledForeground") != null) {
            return UIManager.getColor("Label.disabledForeground");
        }
        else {
            return Color.GRAY;
        }
    }

    
}   // GuiUtils