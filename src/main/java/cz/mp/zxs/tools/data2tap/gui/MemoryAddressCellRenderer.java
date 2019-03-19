/*
 * MemoryAddressItemCellRenderer.java
 *
 *  created: 12.3.2019
 *  charset: UTF-8
 */

package cz.mp.zxs.tools.data2tap.gui;

import cz.mp.zxs.tools.data2tap.MemoryAddress;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;


/**
 *
 * @author Martin Pokorný
 * @see MemoryAddressItem
 * @see MainFrame
 */
public class MemoryAddressCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(
            JList list, Object value,  
            int index, boolean isSelected, boolean cellHasFocus) {

        Component defaultListCellRenderer = super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);

        if (! (defaultListCellRenderer instanceof JLabel)) {
            return defaultListCellRenderer;
        }
        else if (value == null) {
            return defaultListCellRenderer;
        }
        else if (value.toString().trim().isEmpty()) {
            JLabel result = (JLabel) defaultListCellRenderer;
            result.setText(" ");
            return result;   
        }        
        else if (! (value instanceof MemoryAddress)) {
            return defaultListCellRenderer;
        }    
        else {
            JLabel result = (JLabel) defaultListCellRenderer;

            MemoryAddress item = (MemoryAddress) value;            
            if (item.getDescription().isEmpty()) {
                result.setText(item.getAddressDec()+ " ");
            }
            else {
                result.setText("<HTML>" + item.getAddressDec()
                        + "  <FONT color=\"GRAY\">(" + item.getDescription() + ")</FONT>");
            }
            return result;   
        }
    }
}   // MemoryAddressItemCellRenderer.java
