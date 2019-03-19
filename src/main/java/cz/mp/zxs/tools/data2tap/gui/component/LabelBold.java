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

    private static final Font DEFAULT_LABEL_FONT = UIManager.getFont("Label.font");
    private static final Font BOLD_LABEL_FONT = DEFAULT_LABEL_FONT.deriveFont(
            DEFAULT_LABEL_FONT.getStyle() | Font.BOLD);    
    
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

    /**
     * 
     */
    private void initFont() {
        setFont(BOLD_LABEL_FONT);
    }       
    
}   // LabelBold.java

