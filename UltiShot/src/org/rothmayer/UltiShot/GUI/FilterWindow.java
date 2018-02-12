package org.rothmayer.UltiShot.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Toolkit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;

import org.rothmayer.UltiShot.GUI.elements.FilterAction;
import org.rothmayer.UltiShot.Util.StringComp;
import org.rothmayer.UltiShot.Util.StringPosition;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FilterWindow extends JFrame implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8419511037775438757L;
	private JPanel contentPane;
	private Box verticalBox;
	private Box horizontalBox;
	private int sp;
	private JComboBox comboBox;
	private StringComp stringComp_2;
	//private transient FilterAction action;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FilterWindow frame = new FilterWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FilterWindow() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FilterWindow.class.getResource("/images/logo250.png")));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 840, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		horizontalBox = Box.createHorizontalBox();
		contentPane.add(horizontalBox, BorderLayout.NORTH);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut);
		
		JLabel lblFiltereinstellungen = new JLabel("Filtereinstellungen");
		lblFiltereinstellungen.setFont(new Font("Tahoma", Font.PLAIN, 30));
		horizontalBox.add(lblFiltereinstellungen);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setMaximumSize(new Dimension(100, 32767));
		horizontalStrut_1.setPreferredSize(new Dimension(100, 0));
		horizontalStrut_1.setMinimumSize(new Dimension(100, 0));
		horizontalBox.add(horizontalStrut_1);
		
		JLabel lblWieVileFilter = new JLabel("Wie vile Filter: ");
		horizontalBox.add(lblWieVileFilter);
		
		comboBox = new JComboBox();
		/**action = new FilterAction(this);
		comboBox.addActionListener(action);**/
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Test");
				changeFilter();
			}
		});
		comboBox.setEditable(true);
		comboBox.setMaximumSize(new Dimension(70, 32767));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		horizontalBox.add(comboBox);
		verticalBox = Box.createVerticalBox();
		contentPane.add(verticalBox, BorderLayout.CENTER);
		
		
		
		stringComp_2 = new StringComp(0, true);
		contentPane.add(stringComp_2, BorderLayout.SOUTH);
		changeFilter();
	}
	
	public List<StringComp> getFilter2(){
		ArrayList<StringComp> list = new ArrayList<StringComp>();
		
		for(int i = 0; i < verticalBox.getComponentCount(); i++){
			
			StringComp comp = (StringComp) verticalBox.getComponent(i);
			if(comp.isVal()){
				list.add(comp);
			}
			
		}
		list.add(stringComp_2);
		return list;
	}
	
	public void changeFilter(){
		int akt = verticalBox.getComponentCount();
		int tar = comboBox.getSelectedIndex()+1;
		
		if(akt > tar){
			while(akt != tar){
				verticalBox.remove(verticalBox.getComponentCount()-1);
				UltiShot.logger.debug("Remove Filter!");
				akt--;
			}
		}else if(akt < tar){
			int i = akt;
			while(akt != tar){
				i++;
				verticalBox.add(new StringComp(i));
				UltiShot.logger.debug("Add Filter!");
				akt++;
			}
			
		}
		this.validate();
		this.repaint();
	}

	public JPanel getContentPane() {
		return contentPane;
	}
	
	public void addActionL(){
		System.out.println("Test2");
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Test");
				changeFilter();
			}
		});
	}

}
