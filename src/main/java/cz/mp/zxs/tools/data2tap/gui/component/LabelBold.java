/*
 * LabelBold.java
 *
 *  created: 12.10.2011
 *  charset: UTF-8
 */
package cz.mp.zxs.tools.data2tap.gui.component;

import java.awt.Font;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 * JLabel, který je ve výchozím stavu tučným řezem písma.
 * 
 * @author Martin Pokorný
 * @see JLabel
 */
public class LabelBold extends JLabel {

    public LabelBold(String text) {
        super(text);
        initFont();
    }
    
    public LabelBold() {
        super();
        initFont();
    }

    public LabelBold(Icon image) {
        super(image);
        initFont();
    }

    public LabelBold(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
        initFont();
    }

    public LabelBold(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        initFont();
    }

    public LabelBold(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        initFont();
    }

    private static Font defaultLabelFont = UIManager.getFont("Label.font");
    private static Font labelFont = defaultLabelFont.deriveFont(
            defaultLabelFont.getStyle() | Font.BOLD);    
    /**
     * 
     */
    private void initFont() {
        setFont(labelFont);
    }       
    
}   // LabelBold.java

