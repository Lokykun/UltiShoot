package org.rothmayer.UltiShot.GUI;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.rothmayer.UltiShot.DB.Local.Zuweisung;
import org.rothmayer.UltiShot.DB.SMBD.Mannschaft;
import org.rothmayer.UltiShot.DB.SMBD.Schuetze;
import org.rothmayer.UltiShot.DB.SMBD.Starterliste;
import org.rothmayer.UltiShot.DB.SMBD.Starterlisten;
import org.rothmayer.UltiShot.DB.SSMBD2.Scheiben;
import org.rothmayer.UltiShot.GUI.elements.MannschaftsPanel;

import com.sun.jmx.snmp.Enumerated;

import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;
import java.awt.Toolkit;

import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Color;

public class TeamAssignmentWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<Starterlisten> starterlistenBox;
	private JList listSchuetzen;
	private DefaultListModel<Schuetze> listModelSchuetzen;
	private DefaultListModel<Mannschaft> listModelMannschaft;
	private JTextField searchField;
	private JPanel panelSchuetzen;
	private Box verticalBox;
	private Component verticalStrut;
	private Component verticalStrut_1;
	private JLabel lblNewLabel;
	private Box horizontalBox_1;
	private Component horizontalStrut;
	private JPanel panelMannschaft;
	private JScrollPane scrollPaneMannschaft;
	private JList listMannschaft;
	private JLabel lblSchtzen;
	private Box horizontalBox_2;
	private Component horizontalStrut_1;
	private Box verticalBox_1;
	private Component verticalStrut_2;
	private JLabel lblMannschaften;
	private BasicArrowButton basicArrowButton;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private Component horizontalStrut_2;
	private Component horizontalStrut_3;
	private JLabel lblVeranstaltung;
	private Box boxMannschaft;
	private JPanel panel_3;
	private JPanel panel_4;
	private Component horizontalStrut_4;
	private int aktStaterliste = -1;
	private int aktSchuetze = -1;
	private JPanel panel_5;
	private Box verticalBox_2;
	private JPanel panel_6;
	private Component horizontalStrut_5;
	private Component verticalStrut_3;

	/**
	 * Create the frame.
	 */
	public TeamAssignmentWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TeamAssignmentWindow.class.getResource("/images/logo250.png")));
		setTitle("UltiShot Mannschaftszuordnung");
		setBounds(600, 800, 1200, 1000);
		setMinimumSize(new Dimension(1100, 800));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);


		listModelSchuetzen = new DefaultListModel<Schuetze>();
		listModelMannschaft = new DefaultListModel<Mannschaft>();
		panelSchuetzen = new JPanel();
		contentPane.add(panelSchuetzen, BorderLayout.WEST);
		panelSchuetzen.setLayout(new BorderLayout(0, 0));
		
		verticalBox = Box.createVerticalBox();
		panelSchuetzen.add(verticalBox, BorderLayout.NORTH);
		
		verticalStrut = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut);
		
		horizontalBox_1 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_1);
		
		lblNewLabel = new JLabel("Suche:");
		horizontalBox_1.add(lblNewLabel);
		
		horizontalStrut = Box.createHorizontalStrut(5);
		horizontalBox_1.add(horizontalStrut);
		searchField = new JTextField();
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				renewSchuetzen();
			}
		});
		horizontalBox_1.add(searchField);
		searchField.setColumns(10);
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_1);
		
		lblSchtzen = new JLabel("Sch\u00FCtzen");
		verticalBox.add(lblSchtzen);
		listSchuetzen = new JList(listModelSchuetzen);
		
		listSchuetzen.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				boxMannschaft.removeAll();
				renewActiveMannschafteb();
				listSchuetzen.setSelectedIndex(-1);
				readSerien();
				renewMannschaften();
				revalidate();
				repaint();
				aktSchuetze = -1;
			}
		});
		
		listSchuetzen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPaneSchuetzen = new JScrollPane();
		scrollPaneSchuetzen.setPreferredSize(new Dimension(300, 2));
		panelSchuetzen.add(scrollPaneSchuetzen);
		scrollPaneSchuetzen.setViewportView(listSchuetzen);
		
		horizontalStrut_4 = Box.createHorizontalStrut(200);
		panelSchuetzen.add(horizontalStrut_4, BorderLayout.SOUTH);
		
		Box horizontalBox = Box.createHorizontalBox();
		contentPane.add(horizontalBox, BorderLayout.NORTH);
		
		starterlistenBox = new JComboBox<Starterlisten>();
		starterlistenBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				//renewList();
				renweDiszi();
				renewSchuetzen();
			}
		});
		
		lblVeranstaltung = new JLabel("Veranstaltung: ");
		horizontalBox.add(lblVeranstaltung);
		horizontalBox.add(starterlistenBox);
		renewList();
		
		JButton btnNeuEinlesen = new JButton("Neu Einlesen");
		btnNeuEinlesen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				renewList();
				renewSchuetzen();
				renewMannschaften();
				aktStaterliste = ((Starterlisten) starterlistenBox.getSelectedItem()).getListenID();
			}
		});
		
		horizontalStrut_5 = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut_5);
		horizontalBox.add(btnNeuEinlesen);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		panel_5 = new JPanel();
		panel_1.add(panel_5, BorderLayout.CENTER);
		
		verticalBox_2 = Box.createVerticalBox();
		panel_5.add(verticalBox_2);
		
		panel_6 = new JPanel();
		panel_6.setPreferredSize(new Dimension(10, 90));
		panel_6.setMinimumSize(new Dimension(200, 90));
		panel_6.setMaximumSize(new Dimension(32767, 90));
		verticalBox_2.add(panel_6);
		panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		boxMannschaft = Box.createVerticalBox();
		verticalBox_2.add(boxMannschaft);
		
		panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		horizontalBox_2 = Box.createHorizontalBox();
		panel_2.add(horizontalBox_2, BorderLayout.WEST);
		
		horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalBox_2.add(horizontalStrut_1);
		
		verticalBox_1 = Box.createVerticalBox();
		horizontalBox_2.add(verticalBox_1);
		
		verticalStrut_2 = Box.createVerticalStrut(20);
		verticalBox_1.add(verticalStrut_2);
		
		JButton btnAkt = new JButton("Aktualisieren");
		btnAkt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				renweDiszi();
				renewSchuetzen();
			}
		});
		verticalBox_1.add(btnAkt);
		
		verticalStrut_3 = Box.createVerticalStrut(20);
		verticalBox_1.add(verticalStrut_3);
		
		lblMannschaften = new JLabel("Mannschaften");
		lblMannschaften.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblMannschaften.setHorizontalAlignment(SwingConstants.LEFT);
		verticalBox_1.add(lblMannschaften);
		
		panelMannschaft = new JPanel();
		verticalBox_1.add(panelMannschaft);
		panelMannschaft.setLayout(new BorderLayout(0, 0));
		
		scrollPaneMannschaft = new JScrollPane();
		scrollPaneMannschaft.setPreferredSize(new Dimension(300, 2));
		panelMannschaft.add(scrollPaneMannschaft, BorderLayout.WEST);
		
		listMannschaft = new JList(listModelMannschaft);
		scrollPaneMannschaft.setViewportView(listMannschaft);
		
		horizontalStrut_2 = Box.createHorizontalStrut(5);
		horizontalBox_2.add(horizontalStrut_2);
		
		panel = new JPanel();
		horizontalBox_2.add(panel);
		
		basicArrowButton = new BasicArrowButton(BasicArrowButton.EAST);
		basicArrowButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				addMannschaft(listMannschaft.getSelectedValuesList());
			}
		});
		basicArrowButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		basicArrowButton.setSize(new Dimension(20, 20));
		basicArrowButton.setMinimumSize(new Dimension(20, 20));
		basicArrowButton.setMaximumSize(new Dimension(20, 20));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(basicArrowButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(basicArrowButton, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
		);
		panel.setLayout(gl_panel);
		
		horizontalStrut_3 = Box.createHorizontalStrut(5);
		horizontalBox_2.add(horizontalStrut_3);
		
		
		
		renewSchuetzen();
	}
	
	public void readSerien(){
		int pass = 0;
		if(listSchuetzen.getModel().getSize() != 0){
			try {
				pass = ((List<Schuetze>) listSchuetzen.getSelectedValuesList()).get(0).getSportpassID();
			} catch (Exception e) {
				pass = 0;
			}
		}
		int starterlisten = ((Starterlisten)starterlistenBox.getSelectedItem()).getListenID();
		if(pass == 0 || starterlisten == 0){
			//TODo Serein nullen
			return;
		}
		List<Scheiben> scheiben = UltiShot.ssmdb2.find(Scheiben.class).where().eq("StarterlistenID", starterlisten).eq("SportpassID", pass).findList();
		//System.out.println("--" + scheiben.size());
	}
	
	public void addMannschaft(List<Mannschaft> mannschaften){
		int pass = ((List<Schuetze>) listSchuetzen.getSelectedValuesList()).get(0).getSportpassID();
		int starterlisten = ((Starterlisten)starterlistenBox.getSelectedItem()).getListenID();
		for(Mannschaft mannschaft : mannschaften){
			int i = boxMannschaft.getComponents().length;
			if(pass >= 0 && starterlisten >= 0 && i >= 0){
				Zuweisung zuweisung = new Zuweisung(pass , starterlisten, mannschaft.getMannschaftsID(), i, false);
				MannschaftsPanel mPanel = new MannschaftsPanel(this, zuweisung);
				boxMannschaft.add(mPanel);
				mPanel.save();
				listModelMannschaft.removeElement(mannschaft);
			}
		}
		this.revalidate();
		this.repaint();
		
		
	}
	
	public void reIndexMannschaften(){
		int i = 0;
		MannschaftsPanel[] array = new MannschaftsPanel[boxMannschaft.getComponents().length];
		for(Component comp : boxMannschaft.getComponents()){
			if(comp instanceof MannschaftsPanel){
				boxMannschaft.remove(comp);
				array[((MannschaftsPanel) comp).getZuweisung().getReihenfolge()] = (MannschaftsPanel) comp;
			}
		}
		for(MannschaftsPanel mPan : array){
			boxMannschaft.add(mPan);
		}
		
		this.revalidate();
		this.repaint();
	}
	
	public void renewList(){
		starterlistenBox.removeAllItems();
		
		//System.out.println(UltiShot.smdb.find(Starterlisten.class).findCount());
		List<Starterlisten> starterlisten = UltiShot.smdb.find(Starterlisten.class).findList();
		for(Starterlisten entry : starterlisten){
			starterlistenBox.addItem(entry);
		}
		//TODO Diszi
	}
	
	public void renweDiszi(){
		
	}
	
	public void renewSchuetzen(){
		
		
		
		try {
			listModelSchuetzen.clear();

			listModelMannschaft.clear();
		} catch (NullPointerException e) {
		}
		
		Starterlisten starterlisten = UltiShot.smdb.find(Starterlisten.class).where().eq("ListenID", ((Starterlisten)starterlistenBox.getSelectedItem()).getListenID()).findUnique();
		
		Starterlisten sListen = (Starterlisten) starterlistenBox.getSelectedItem();
		
		if(starterlisten != null){
			sListen = starterlisten;
		}
		
		if(sListen == null){
			return;
		}
		List<Schuetze> sList = new ArrayList<>();
		
		for(Starterliste entry : sListen.getStarterListe()){
			try {
				if(searchField.getText().replaceAll("\\s", "") == "" || searchField.getText() == null){
					sList.add(entry.getSchuetze());
				}else if(entry.getSchuetze().toString().trim().toLowerCase().indexOf(searchField.getText().toLowerCase()) != -1 || searchField.getText().equalsIgnoreCase("")){
					sList.add(entry.getSchuetze());
				}
			} catch (NullPointerException e) {
			}
		}
		Collections.sort(sList);
		for(Schuetze sch : sList){
			listModelSchuetzen.addElement(sch);
		}
		
	}
	
	public void renewActiveMannschafteb(){
		if(listSchuetzen.getSelectedIndex() == -1){
			return;
		}
		List<Zuweisung> zuweisungen = UltiShot.localDB.find(Zuweisung.class).where()
				.eq("sportpassid", listModelSchuetzen.getElementAt(listSchuetzen.getSelectedIndex()).getSportpassID())
				.eq("starterlistenid", ((Starterlisten) starterlistenBox.getSelectedItem()).getListenID()).orderBy("reihenfolge").findList();
		if( zuweisungen.isEmpty()){
			return;
		}
		for(Zuweisung zuweisung : zuweisungen){

			boxMannschaft.add(new MannschaftsPanel(this, zuweisung));
		}
		
	}
	
	public void renewMannschaften(){
		if(listSchuetzen.getSelectedIndex() == -1){
			return;
		}
		listModelMannschaft.clear();
		List<Mannschaft> mannschaften = UltiShot.smdb.find(Mannschaft.class).findList();
		Collections.sort(mannschaften);
		List<Zuweisung> zuweisungen = UltiShot.localDB.find(Zuweisung.class).where()
				.eq("sportpassid", listModelSchuetzen.getElementAt(listSchuetzen.getSelectedIndex()).getSportpassID())
				.eq("starterlistenid", ((Starterlisten) starterlistenBox.getSelectedItem()).getListenID()).orderBy("reihenfolge").findList();
		for(Mannschaft entity : mannschaften){
			boolean add = true;
			if(boxMannschaft.getComponents().length > 0){
				
				for(Zuweisung zuw : zuweisungen){
					if(zuw.getZuweisungPK().getMannschaftsID() == entity.getMannschaftsID()){
						add = false;
					}
				
				}
			}
			if(add){

				listModelMannschaft.addElement(entity);
			}
		}
	}

	public Box getBoxMannschaft() {
		return boxMannschaft;
	}

	public int getAktStaterliste() {
		return aktStaterliste;
	}

	public int getAktSchuetze() {
		return aktSchuetze;
	}
	
	public void setSerienStand(){
		
	}
	
	

}
