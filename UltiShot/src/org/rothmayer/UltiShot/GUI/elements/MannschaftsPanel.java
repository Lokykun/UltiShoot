package org.rothmayer.UltiShot.GUI.elements;

import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicArrowButton;

import org.rothmayer.UltiShot.DB.Local.Zuweisung;
import org.rothmayer.UltiShot.DB.SMBD.Mannschaft;
import org.rothmayer.UltiShot.GUI.TeamAssignmentWindow;
import org.rothmayer.UltiShot.GUI.UltiShot;

import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class MannschaftsPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private Zuweisung zuweisung;
	private String mannschaft;
	private TeamAssignmentWindow window;
	
	public MannschaftsPanel(TeamAssignmentWindow window ,Zuweisung zuweisung) {
		this.window = window;
		setMinimumSize(new Dimension(500, 50));
		setMaximumSize(new Dimension(500, 50));
		setPreferredSize(new Dimension(500, 50));
		setBorder(new LineBorder(new Color(0, 0, 0)));
		this.zuweisung = zuweisung;
		mannschaft = UltiShot.smdb.find(Mannschaft.class).where().eq("MannschaftsID", zuweisung.getZuweisungPK().getMannschaftsID()).findUnique().getMannschaftsName();
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.WEST);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		BasicArrowButton basicArrowButton = new BasicArrowButton(BasicArrowButton.NORTH);
		basicArrowButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//UP
				if(zuweisung.getReihenfolge() == 0){
					return;
				}
				int old = zuweisung.getReihenfolge();
				Zuweisung zuweisungNext = UltiShot.localDB.find(Zuweisung.class).where()
						.eq("sportpassid", zuweisung.getZuweisungPK().getSportpassID())
						.eq("starterlistenid", zuweisung.getZuweisungPK().getStarterlistenID()).eq("reihenfolge", old-1).findUnique();
				zuweisung.setReihenfolge(old-1);
				UltiShot.localDB.save(zuweisung);
				zuweisungNext.setReihenfolge(old);
				UltiShot.localDB.save(zuweisungNext);
				renewList();
			}
		});
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(5);
		panel.add(horizontalStrut_4);
		panel.add(basicArrowButton);
		
		BasicArrowButton basicArrowButton_1 = new BasicArrowButton(BasicArrowButton.SOUTH);
		basicArrowButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//DOWN
				int max = UltiShot.localDB.find(Zuweisung.class).where()
						.eq("sportpassid", zuweisung.getZuweisungPK().getSportpassID())
						.eq("starterlistenid", zuweisung.getZuweisungPK().getStarterlistenID()).findList().size()-1;
				if(zuweisung.getReihenfolge() == max){
					return;
				}
				int old = zuweisung.getReihenfolge();
				Zuweisung zuweisungNext = UltiShot.localDB.find(Zuweisung.class).where()
						.eq("sportpassid", zuweisung.getZuweisungPK().getSportpassID())
						.eq("starterlistenid", zuweisung.getZuweisungPK().getStarterlistenID()).eq("reihenfolge", old+1).findUnique();
				zuweisung.setReihenfolge(old+1);
				UltiShot.localDB.save(zuweisung);
				
				zuweisungNext.setReihenfolge(old);
				UltiShot.localDB.save(zuweisungNext);
				renewList();
			}
		});
		panel.add(basicArrowButton_1);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(5);
		panel.add(horizontalStrut_2);
		
		JLabel label = new JLabel((zuweisung.getReihenfolge()+1) + ".");
		panel.add(label);
		
		JLabel label_1 = new JLabel("");
		panel.add(label_1);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		panel.add(horizontalStrut_1);
		
		JLabel lblMannschaft = new JLabel(mannschaft);
		panel.add(lblMannschaft);
		
		JLabel label_3 = new JLabel("");
		panel.add(label_3);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JCheckBox chckbxKombiniert = new JCheckBox("Kombiniert");
		chckbxKombiniert.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				getZuweisung().setKombiniert(chckbxKombiniert.isSelected());
				save();
			}
		});
		panel_1.add(chckbxKombiniert);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut);
		
		JButton btnEntfernen = new JButton("Entfernen");
		panel_1.add(btnEntfernen);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(5);
		panel_1.add(horizontalStrut_3);
		
		Component verticalStrut = Box.createVerticalStrut(2);
		add(verticalStrut, BorderLayout.NORTH);
		
		Component verticalStrut_1 = Box.createVerticalStrut(2);
		add(verticalStrut_1, BorderLayout.SOUTH);
		btnEntfernen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				remove();
				
			}
		});
		chckbxKombiniert.setSelected(zuweisung.isKombiniert());

	}
	
	public void setIndex(int i){
		zuweisung.setReihenfolge(i);
	}
	
	public void save(){
		UltiShot.localDB.save(zuweisung);
	}
	
	public void renewList(){
		window.getBoxMannschaft().removeAll();
		//window.renewMannschaften();
		window.renewActiveMannschafteb();
		window.revalidate();
		window.repaint();
	}
	
	public void remove(){
		UltiShot.localDB.delete(zuweisung);
		window.getBoxMannschaft().removeAll();
		window.renewActiveMannschafteb();
		window.renewMannschaften();
		window.revalidate();
		window.repaint();
	}

	public Zuweisung getZuweisung() {
		return zuweisung;
	}

	public String getMannschaft() {
		return mannschaft;
	}
	
}
