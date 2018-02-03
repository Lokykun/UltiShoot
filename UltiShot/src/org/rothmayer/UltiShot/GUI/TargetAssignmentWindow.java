package org.rothmayer.UltiShot.GUI;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.rothmayer.UltiShot.DB.SMBD.Schuetze;
import org.rothmayer.UltiShot.DB.SMBD.Starterlisten;
import org.rothmayer.UltiShot.DB.SSMBD2.Scheiben;
import org.rothmayer.UltiShot.Util.Auswerter;
import org.rothmayer.UltiShot.Util.Scheibe;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.awt.Dimension;
import javax.swing.border.BevelBorder;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JProgressBar;

public class TargetAssignmentWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox starterlistenBox;
	private JList list;
	private DefaultListModel scheibenModel;

	private Auswerter aus = new Auswerter();
	private JComboBox comboBoxMannschaft;
	private JComboBox comboBoxProfi;
	private JButton btnAuswertungStarten;
	private TimerTask ausTask;
	private Timer timer;
	private JProgressBar progressBar;
	/**
	 * Create the frame.
	 */
	public TargetAssignmentWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TargetAssignmentWindow.class.getResource("/images/logo250.png")));
		setTitle("UltiShot Auswertung");
		setBounds(600, 800, 482, 598);
		setMinimumSize(new Dimension(500, 500));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setPreferredSize(new Dimension(500, 10));
		panel.setMinimumSize(new Dimension(500, 10));
		contentPane.add(panel);
		scheibenModel = new DefaultListModel<Scheibe>();
		
		JLabel lblVeranstaltung = new JLabel("Veranstaltung:");
		
		starterlistenBox = new JComboBox();
		starterlistenBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				renewScheibe();
			}
		});
		starterlistenBox.setPreferredSize(new Dimension(400, 25));
		renewList();
		
		JLabel lblScheibenarten = new JLabel("Scheibenarten:");
		
		list = new JList();
		list.setPreferredSize(new Dimension(400, 400));
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list.setValueIsAdjusting(true);
		list.setModel(scheibenModel);
		
		JLabel lblManschaftsgr = new JLabel("Mannschaftgr\u00F6sse:");
		
		comboBoxMannschaft = new JComboBox();
		comboBoxMannschaft.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5"}));
		
		JLabel lblProfisch = new JLabel("Profisch\u00FCtzen:");
		
		comboBoxProfi = new JComboBox();
		comboBoxProfi.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5"}));
		
		
		
		btnAuswertungStarten = new JButton("Auswertung starten");
		btnAuswertungStarten.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(timer == null){
					
					list.setEnabled(false);
					starterlistenBox.setEnabled(false);
					comboBoxMannschaft.setEnabled(false);
					comboBoxProfi.setEnabled(false);
					btnAuswertungStarten.setText("Auswertung beenden");
					
					timer = new Timer(100, new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							
							progressBar.setValue(progressBar.getValue()+1);
							//System.out.println(progressBar.getValue());
							if(progressBar.getValue() < 60){
								return;
							}else{
								progressBar.setValue(0);
							}
							int starterlisten = ((Starterlisten)starterlistenBox.getSelectedItem()).getListenID();
							
							List<Integer> scheibeIDs = new ArrayList<>();
							
							for(Scheibe sch : ((List<Scheibe>)list.getSelectedValuesList())){
								scheibeIDs.add(sch.getDisziplinID());
							}
							
							aus.doAuswertung(starterlisten, scheibeIDs, comboBoxMannschaft.getSelectedIndex()+1, comboBoxProfi.getSelectedIndex()+1);
							//System.out.println(System.currentTimeMillis());
							
						}
					});
					timer.setRepeats(true);
					timer.start();
				}else{
					timer.stop();
					list.setEnabled(true);
					starterlistenBox.setEnabled(true);
					comboBoxMannschaft.setEnabled(true);
					comboBoxProfi.setEnabled(true);
					btnAuswertungStarten.setText("Auswertung starten");
					timer = null;
				}
				
				
			}
		});
		
		progressBar = new JProgressBar();
		progressBar.setMaximum(60);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblVeranstaltung)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(starterlistenBox, 0, 0, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(list, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblScheibenarten))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnAuswertungStarten, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblManschaftsgr)
								.addComponent(lblProfisch, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(comboBoxMannschaft, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBoxProfi, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
								.addComponent(progressBar, 0, 0, Short.MAX_VALUE))
							.addGap(1)))
					.addGap(31))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVeranstaltung)
						.addComponent(starterlistenBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(28)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblScheibenarten)
						.addComponent(lblManschaftsgr))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(list, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(comboBoxMannschaft, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(21)
							.addComponent(lblProfisch)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxProfi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(36)
							.addComponent(btnAuswertungStarten)
							.addGap(9)
							.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(435))
		);
		panel.setLayout(gl_panel);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);


		
	}
	
	/*public void setEvents(){
		
		starterlistenBox.addUltiShotAuswerung.ssmdb2.find(Scheiben.class).findList();
	
	}*/
	
	public void renewList(){
		starterlistenBox.removeAllItems();
		
		//System.out.println(UltiShot.smdb.find(Starterlisten.class).findCount());
		List<Starterlisten> starterlisten = UltiShot.smdb.find(Starterlisten.class).findList();
		for(Starterlisten entry : starterlisten){
			starterlistenBox.addItem(entry);
		}
		//TODO Diszi
	}
	
	public void renewScheibe(){
		int starterlisten = ((Starterlisten)starterlistenBox.getSelectedItem()).getListenID();
		List<Scheiben> scheiben = UltiShot.ssmdb2.find(Scheiben.class).where().eq("StarterlistenID", starterlisten).findList();
		
		List<Scheibe> s = new ArrayList<>();
		
		for(Scheiben scheibe : scheiben){
			Scheibe sch = new Scheibe(scheibe.getDisziplin(), scheibe.getDisziplinID());
			if(!s.contains(sch)){
				
				s.add(sch);
			}
		}
		scheibenModel.clear();
		for(Scheibe scheibe: s){
			scheibenModel.addElement(scheibe);
		}
	
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public JComboBox getStarterlistenBox() {
		return starterlistenBox;
	}

	public JList getList() {
		return list;
	}

	public DefaultListModel getScheibenModel() {
		return scheibenModel;
	}

	public Auswerter getAus() {
		return aus;
	}

	public JComboBox getComboBoxMannschaft() {
		return comboBoxMannschaft;
	}

	public JComboBox getComboBoxProfi() {
		return comboBoxProfi;
	}

	public JButton getBtnAuswertungStarten() {
		return btnAuswertungStarten;
	}
}
