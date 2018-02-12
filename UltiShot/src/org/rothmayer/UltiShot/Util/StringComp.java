package org.rothmayer.UltiShot.Util;

import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.UnsupportedEncodingException;

public class StringComp extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4973565694252376055L;
	private int index;
	private JTextField textField;
	private JTextField textTitle;
	private JComboBox comboBox;
	private boolean def = false;
	
	/**
	 * @wbp.parser.constructor
	 */
	public StringComp(int index){
		this(index, false);
		
	}
	
	public StringComp(int index, boolean def) {
		this.def = def;
		setSize(new Dimension(800, 40));
		setPreferredSize(new Dimension(800, 40));
		setBounds(new Rectangle(0, 0, 800, 40));
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setMaximumSize(new Dimension(800, 40));
		setMinimumSize(new Dimension(800, 40));
		this.index = index;
		
		Box horizontalBox = Box.createHorizontalBox();
		add(horizontalBox);
		
		JLabel label = new JLabel(index + ".");
		label.setSize(new Dimension(25, 20));
		label.setPreferredSize(new Dimension(25, 20));
		label.setMinimumSize(new Dimension(25, 20));
		label.setMaximumSize(new Dimension(25, 20));
		horizontalBox.add(label);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut);
		
		JLabel lblSuchtext = new JLabel("Suchtext: ");
		horizontalBox.add(lblSuchtext);
		
		textField = new JTextField();
		horizontalBox.add(textField);
		textField.setColumns(10);
		if(def){
			textField.setText("alles andere");
			textField.setEnabled(false);
		}
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut_1);
		
		comboBox = new JComboBox();
		
		if(!def){
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"am ende", "am anfang", "\u00FCberall"}));
		}else{
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"am ende", "am anfang", "\u00FCberall", "\u00FCberall"}));
			comboBox.setSelectedIndex(2);
			comboBox.setEnabled(false);
		}
		
		JLabel lblWoWirdGesucht = new JLabel("Wo wird gesucht: ");
		horizontalBox.add(lblWoWirdGesucht);
		comboBox.setEditable(false);
		horizontalBox.add(comboBox);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut_2);
		
		JLabel lblTitel = new JLabel("Titel: ");
		horizontalBox.add(lblTitel);
		
		textTitle = new JTextField();
		horizontalBox.add(textTitle);
		textTitle.setColumns(10);
	}
	
	public boolean isVal(){
		if(def){
			return true;
		}
		if(textField.getText() ==  null){
			return false;
		}
		
		if(textField.getText().equalsIgnoreCase("")){
			return false;
		}
		
		return true;
	}
	
	public boolean compString(String text){
		
		byte ptext[];
		String tf = null;
		try {
			ptext = textField.getText().getBytes("ISO-8859-1");
			tf = new String(ptext, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		byte ptext2[];
		String te = null;
		try {
			ptext2 = text.getBytes("ISO-8859-1");
			te = new String(ptext2, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//System.out.println(te + " " + tf);
		
		if(def){
			System.out.println("Def");
				return true;
		}
		
		//System.out.println(comboBox.getSelectedIndex() + "++++++++++" + text + " " + textField.getText());
		if(comboBox.getSelectedIndex() == StringPosition.AFTRER.getId()){
			if(te.endsWith(tf)){
				return true;
			}
		}else if(comboBox.getSelectedIndex() == StringPosition.BEVOR.getId()){
			if(te.startsWith(tf)){
				return true;
			}
		}else if(comboBox.getSelectedIndex() == StringPosition.SOMEWHERE.getId()){
			if(te.contains(tf)){
				return true;
			}
		}
		
		return false;
		
		
		/**
		System.out.println(comboBox.getSelectedIndex() + "++++++++++" + text + " " + textField.getText());
		if(comboBox.getSelectedIndex() == StringPosition.AFTRER.getId()){
			if(textField.getText().endsWith(""+text)){
				System.out.println(comboBox.getSelectedIndex() + "++++++++++" + StringPosition.AFTRER.getId());
				return true;
			}
		}else if(comboBox.getSelectedIndex() == StringPosition.BEVOR.getId()){
			if(textField.getText().startsWith(""+text)){
				System.out.println(comboBox.getSelectedIndex() + "++++++++++" + StringPosition.BEVOR.getId());
				return true;
			}
		}else if(comboBox.getSelectedIndex() == StringPosition.SOMEWHERE.getId()){
			if(textField.getText().contains(""+text)){
				System.out.println(comboBox.getSelectedIndex() + "++++++++++" + StringPosition.SOMEWHERE.getId());
				return true;
			}
		}else if(comboBox.getSelectedIndex() == StringPosition.BYPASS.getId()){
			System.out.println(comboBox.getSelectedIndex() + "++++++++++" + StringPosition.BYPASS.getId());
				return true;
		}
		
		return false;
		**/
	}

	public String getTitle() {
		if(textTitle == null){
			return "";
		}
		return textTitle.getText();
	}

}
