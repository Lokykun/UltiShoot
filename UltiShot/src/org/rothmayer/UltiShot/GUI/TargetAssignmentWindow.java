package org.rothmayer.UltiShot.GUI;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.rothmayer.UltiShot.DB.SMBD.Mannschaft;
import org.rothmayer.UltiShot.DB.SMBD.Schuetze;
import org.rothmayer.UltiShot.DB.SMBD.Starterlisten;
import org.rothmayer.UltiShot.DB.SSMBD2.Scheiben;
import org.rothmayer.UltiShot.Util.Auswerter;
import org.rothmayer.UltiShot.Util.Scheibe;
import org.rothmayer.UltiShot.Util.StringComp;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.InputVerifier;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.ComponentOrientation;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.beans.PropertyChangeEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

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
	private MenuWindow mWindow;
	private JLabel lblListenBennenung;
	private JTextField textName;
	private JTextField textPath;
	private JSpinner spinner;
	private int time;
	private FilterWindow fWindow;
	/**
	 * Create the frame.
	 */
	public TargetAssignmentWindow(MenuWindow mWindow) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				writeFilter(fWindow);
				fWindow.dispatchEvent(new WindowEvent(fWindow, WindowEvent.WINDOW_CLOSING));

			}
		});
		fWindow = readFilter();
		fWindow.setVisible(false);
		setResizable(false);
		this.mWindow = mWindow;
		setIconImage(Toolkit.getDefaultToolkit().getImage(TargetAssignmentWindow.class.getResource("/images/logo250.png")));
		setTitle("UltiShot Auswertung");
		setBounds(600, 800, 660, 830);
		setMinimumSize(new Dimension(660, 850));
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
					
					try {
						time = (int) spinner.getValue();
					} catch (Exception e1) {
						time = 5;
						UltiShot.logger.warn("Timer value not a Integer set time to 5 min");
					}
					time = time *1000*60;
					list.setEnabled(false);
					starterlistenBox.setEnabled(false);
					comboBoxMannschaft.setEnabled(false);
					comboBoxProfi.setEnabled(false);
					btnAuswertungStarten.setText("Auswertung beenden");
					mWindow.lblStatus.setText("l\u00E4uft");
					progressBar.setMaximum(time);
					progressBar.setValue(time);
					UltiShot.logger.info("Start Timer with " + time +"ms");
					timer = new Timer(time/100, new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							
							progressBar.setValue(progressBar.getValue()+time/100);
							//System.out.println(progressBar.getValue());
							if(progressBar.getValue() < time){
								return;
							}else{
								progressBar.setValue(0);
							}
							UltiShot.logger.debug("Timer is running!");
							int starterlisten = ((Starterlisten)starterlistenBox.getSelectedItem()).getListenID();
							
							List<Integer> scheibeIDs = new ArrayList<>();
							
							for(Scheibe sch : ((List<Scheibe>)list.getSelectedValuesList())){
								scheibeIDs.add(sch.getDisziplinID());
							}
							/**
							//NEW
							List<Mannschaft> mannschaften = UltiShot.smdb.find(Mannschaft.class).findList();
							Map<Integer, String> mans = new HashMap<Integer, String>();
							for(Mannschaft manns : mannschaften){
								mans.put(manns.getMannschaftsID(), manns.getMannschaftsName());
							}
							
							Map<Integer, String> mans2 = new HashMap<Integer, String>(mans);
							
							for(StringComp com : fWindow.getFilter2()){
								
								mans2 = new HashMap<Integer, String>(mans);
								
								Map<Integer, String> manComp = new HashMap<Integer, String>();
								for(Integer id : mans2.keySet()){
									if(com.compString(mans.get(id))){
										manComp.put(id, mans.get(id));
										mans.remove(id);
									}
									aus.doAuswertung(starterlisten, manComp, scheibeIDs, comboBoxMannschaft.getSelectedIndex()+1, comboBoxProfi.getSelectedIndex()+1,textName.getText(),textPath.getText(), com);
									
									
								}
							}**/
							
							
							
							
							
							
							
							
								aus.doAuswertung(starterlisten, scheibeIDs, comboBoxMannschaft.getSelectedIndex()+1, comboBoxProfi.getSelectedIndex()+1,textName.getText(),textPath.getText(), fWindow.getFilter2());
							
							//aus.doAuswertung(starterlisten, scheibeIDs, comboBoxMannschaft.getSelectedIndex()+1, comboBoxProfi.getSelectedIndex()+1,textName.getText(),textPath.getText());
							//System.out.println(System.currentTimeMillis());
							
						}
					});
					timer.setRepeats(true);
					timer.start();
				}else{
					timer.stop();
					UltiShot.logger.info("Stop Timer");
					progressBar.setValue(0);
					mWindow.lblStatus.setText("gestoppt");
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
		progressBar.setMaximum(100);
		
		lblListenBennenung = new JLabel("Listen Bennenung");
		
		textName = new JTextField();
		textName.setText("TestBen");
		textName.setColumns(10);
		
		JLabel lblExportpfad = new JLabel("Exportpfad");
		
		textPath = new JTextField();
		textPath.setText("C:\\Users\\Florian\\Documents\\Test");
		textPath.setColumns(10);
		
		JButton btnPfadWhlen = new JButton("Pfad W\u00E4hlen");
		btnPfadWhlen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fc = new JFileChooser();
		        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        int returnVal = fc.showOpenDialog(null);
		        File f;
		        if (returnVal == JFileChooser.APPROVE_OPTION)
		        {
		            f = fc.getSelectedFile();
		            textPath.setText(f.getPath());
		        }
			}
		});
		
		JLabel lblIntervall = new JLabel("Intervall");
		
	/**	textTime.setInputVerifier(new InputVerifier() {
	         public boolean verify(JComponent textTime) {
	             JTextComponent jtc = (JTextComponent) textTime;
	             String text = jtc.getText();
	             //Nur Zahlen eingaben zulassen
	             return text.matches("\\d*");
	             /* Alternativ für nur Buchstaben
	             return text.matches("\\D*"); 
	         }
		});
	*/
		
		
		
		
		JLabel lblMinuten = new JLabel("Minuten");
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(5, 1, 60, 1));
		
		JButton btnFiltereinstellungen = new JButton("Filtereinstellungen");
		btnFiltereinstellungen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fWindow.setVisible(true);
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblVeranstaltung)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(starterlistenBox, 0, 0, Short.MAX_VALUE))
							.addGroup(gl_panel.createSequentialGroup()
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addComponent(lblScheibenarten)
									.addComponent(list, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addComponent(btnFiltereinstellungen)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblIntervall)
										.addComponent(lblExportpfad)
										.addComponent(textName)
										.addComponent(lblListenBennenung)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
											.addComponent(lblManschaftsgr)
											.addComponent(lblProfisch, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(comboBoxMannschaft, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
											.addComponent(comboBoxProfi, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_panel.createSequentialGroup()
												.addComponent(textPath, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnPfadWhlen)))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblMinuten))))))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnAuswertungStarten)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(progressBar, 0, 0, Short.MAX_VALUE)))
					.addContainerGap(39, Short.MAX_VALUE))
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
						.addComponent(list, GroupLayout.PREFERRED_SIZE, 584, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(comboBoxMannschaft, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(21)
							.addComponent(lblProfisch)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxProfi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(21)
							.addComponent(lblListenBennenung)
							.addGap(4)
							.addComponent(textName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(22)
							.addComponent(lblExportpfad)
							.addGap(6)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(textPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnPfadWhlen))
							.addGap(21)
							.addComponent(lblIntervall)
							.addGap(8)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblMinuten))
							.addGap(34)
							.addComponent(btnFiltereinstellungen)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnAuswertungStarten, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(23))
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

	private FilterWindow readFilter(){
		ObjectInputStream objectinputstream = null;
		File cfg = new File(System.getProperty( "user.home" ) + File.separator + "UltiShot"+ File.separator +"filter" + File.separator + "filter.cfg" );
		File cfgdir = new File(System.getProperty( "user.home" ) + File.separator + "UltiShot" + File.separator + "filter\\" );
		if(!cfgdir.exists()){
			if(!cfgdir.mkdirs()){
				UltiShot.logger.error("Cant create Directory");
				
				return new FilterWindow();
			}
		}
		
		try {
			FileInputStream fin = new FileInputStream(cfg);
			 objectinputstream = new ObjectInputStream(fin);
			 FilterWindow wf = (FilterWindow) objectinputstream.readObject();
				wf.addActionL();
			 return wf;
		} catch (FileNotFoundException e) {
			UltiShot.logger.info("Filter file not found!");
			return new FilterWindow();
		} catch (IOException e) {
			UltiShot.logger.error("Filter read IO exception!");
			e.printStackTrace();
			return new FilterWindow();
		} catch (ClassNotFoundException e) {
			UltiShot.logger.error("Filter file Corrupt!");
			return new FilterWindow();
		} finally {
		    if(objectinputstream != null){
		        try {
					objectinputstream .close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    } 
		}
	}
	
	private void writeFilter(FilterWindow window){
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
		try{
		    fout = new FileOutputStream(System.getProperty( "user.home" ) + File.separator + "UltiShot"+ File.separator +"filter" + File.separator + "filter.cfg");
		    oos = new ObjectOutputStream(fout);
		    oos.writeObject(window);
		} catch (Exception ex) {
		    ex.printStackTrace();
		} finally {
		    if(oos != null){
		        try {
					oos.close();
				} catch (IOException e) {
					UltiShot.logger.error("Filter write IO exception!");
				}
		    } 
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
