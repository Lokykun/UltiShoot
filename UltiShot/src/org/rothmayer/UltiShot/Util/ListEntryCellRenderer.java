package org.rothmayer.UltiShot.Util;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ListEntryCellRenderer extends JLabel implements ListCellRenderer
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 166725073261162237L;
private JLabel label;
 
  public Component getListCellRendererComponent(JList list, Object value,
                                                int index, boolean isSelected,
                                                boolean cellHasFocus) {
     ListEntry entry = (ListEntry) value;
 
     setText(value.toString());
     setIcon(entry.getIcon());
  
     if (isSelected) {
        setBackground(list.getSelectionBackground());
        setForeground(list.getSelectionForeground());
     }
     else {
        setBackground(list.getBackground());
        setForeground(list.getForeground());
     }
 
     setEnabled(list.isEnabled());
     setFont(list.getFont());
     setOpaque(true);
 
     return this;
  }
}