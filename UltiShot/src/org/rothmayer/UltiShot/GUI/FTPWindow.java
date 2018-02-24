package org.rothmayer.UltiShot.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.rothmayer.UltiShot.GUI.elements.FileTreePanel;
import org.rothmayer.UltiShot.Util.ListEntry;
import org.rothmayer.UltiShot.Util.ListEntryCellRenderer;
import org.rothmayer.UltiShot.Util.USFTPFile;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JSplitPane;
import javax.swing.JPasswordField;
import javax.swing.JTree;
import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;

public class FTPWindow extends JFrame {

	private JPanel contentPane;
	private File dirLocal;
	private Map<File, Long> map;
	private JPasswordField pwdTest;
	private FTPFile[] fileArray;
	private JTextField textLocalDir;
	private FTPClient client;
	private JTextField textUsername;
	private JTextField textPort;
	private JTextField textAdresse;
	private JComboBox comboBox;
	private JCheckBox chckbxPassivmodus;
	private DefaultListModel model;
	private JList list;
	private List<String> history;
	private JTextField textPath;
	private JButton btnDiconnect;
	private Timer timer;
	private boolean run = false;
	private WatchKey key;
	private JButton btnConnect;
	private JButton btnPfadWhlen;
	

		/**
	 * Create the frame.
	 */
	public FTPWindow() {
		history = new ArrayList<String>();
		client = new FTPClient();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 664, 888);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelConfig = new JPanel();
		contentPane.add(panelConfig, BorderLayout.NORTH);
		
		JPanel panelSouth = new JPanel();
		contentPane.add(panelSouth, BorderLayout.SOUTH);
		
		Box verticalBox_2 = Box.createVerticalBox();
		verticalBox_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelSouth.add(verticalBox_2);
		
		JButton btnberwachungStarten = new JButton("\u00DCberwachung Starten");
		btnberwachungStarten.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(run){
					if(key != null){
						key.cancel();
					}
					run = false;
					enableUI(true);
					return;
				}
				if(timer == null){
					
					
					run = false;
					//int time = ((int)spinnerTime.getValue())*1000;
					final WatchService watchService;
					Path dirToWatch = Paths.get(textLocalDir.getText());
					try {
						watchService = FileSystems.getDefault().newWatchService();
						
						dirToWatch.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
						
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null,"Angegebener Localen Pfad kann nicht gelesen werden!");
						return;
					}
					if(textLocalDir.getText().equalsIgnoreCase("")){
						JOptionPane.showMessageDialog(null,"Keinen Localen Pfad gewählt!");
						return;
					}
					if(!(new File(textLocalDir.getText()).exists())){
						JOptionPane.showMessageDialog(null,"Localer Pfad existiert nicht!");
						return;
					}
					if(textPath.getText().equalsIgnoreCase("")){
						JOptionPane.showMessageDialog(null,"Kein FTP Ordner gewählt!");
						return;
					}
					btnberwachungStarten.setText("\u00DCberwachung Stoppen");
					enableUI(false);
					UltiShot.logger.info("Start Ordner Überwachung");
					timer = new Timer();
					TimerTask task = new TimerTask() {
						
						@Override
						public void run() {
							//System.out.println("1" + time);
								
								try {
									run = true;
									

									while(run) {
										// Obtaining watch keys
										key = watchService.poll(1000, TimeUnit.MILLISECONDS);
										if (!run) {
							                break;
							            }
										if (key == null) {
											continue;
										}
										// key value can be null if no event was triggered
										List<WatchEvent<?>> eventList = key.pollEvents();
										
							            System.out.println("size = " + eventList.size());
							            /**if(eventList.size() > 1){
							            	break;
							            }**/
							            
										FTPClient uClient = new FTPClient();
										uClient.setSecurity(comboBox.getSelectedIndex());

										uClient.setPassive(!chckbxPassivmodus.isSelected());

										uClient.connect(textAdresse.getText(), Integer.parseInt(textPort.getText()));

										uClient.login(textUsername.getText(), new String(pwdTest.getPassword()));

										uClient.setAutoNoopTimeout(10000);
										
							            for(WatchEvent<?> ev : eventList)
							            {
							               System.out.print(ev.kind() + " -> ");
							               Path name = (Path)ev.context();
							               //System.out.print(name.getParent());
							               // context liefert nur den Dateinamen, parent ist null !
							               Path path = dirToWatch.resolve(name);
							               System.out.print(path);
							               
							               if (Files.isDirectory(path))
							                  System.out.println(" <dir>");
							               else
							                  System.out.println(" <file<");
							               		
							               		if(ev.kind() != StandardWatchEventKinds.ENTRY_DELETE){
							               			File temp = new File(path.toString());
								               		while(!temp.exists()&& !temp.canRead()){
								               			Thread.sleep(1000);
								               		}
								               		uploadFile(uClient, textLocalDir.getText(), path.toString());
							               			
							               		}
							            }
							            
							            boolean valid = key.reset();
							            if (!valid && !run) {
							                break;
							            }
							            System.out.println("Durch");
										uClient.disconnect(true);
									}
									//watchService = null;
									timer = null;
									System.out.println("Fertig");
									btnberwachungStarten.setText("\u00DCberwachung Starten");
								} catch (NumberFormatException | IllegalStateException | IOException | FTPIllegalReplyException | FTPException | InterruptedException | FTPDataTransferException | FTPAbortedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
							
							
						}
						
						public void uploadFile(FTPClient uClient, String root, String file) throws IllegalStateException, IOException, FTPIllegalReplyException, FTPException, FTPDataTransferException, FTPAbortedException{
							String v1 = file.replace(root, "");
							System.out.println(v1);
							
							String[] path = v1.split("\\");
							String[] array = Arrays.copyOf(path, path.length-1);
							uClient.changeDirectory("/");
							
							for(String str : array){
								String t = str.replace("\\", "");
								System.out.println(t);
								
								try {
									uClient.changeDirectory(t);
								} catch(FTPException e) {
									uClient.createDirectory(t);
									uClient.changeDirectory(t);
								}
								
							}
							System.out.println(new File(file).getAbsolutePath());
							if(new File(file).isDirectory()){
								return;
							}
							uClient.upload(new File(file));
							
						}
						
						
				
					};
					timer.schedule(task, 0);
				}
				
				
			}
		});
		btnberwachungStarten.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox_2.add(btnberwachungStarten);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		verticalStrut_3.setPreferredSize(new Dimension(0, 10));
		verticalStrut_3.setMinimumSize(new Dimension(0, 10));
		verticalBox_2.add(verticalStrut_3);
		
		Box horizontalBox = Box.createHorizontalBox();
		verticalBox_2.add(horizontalBox);
		
		JLabel lblStatusTitle = new JLabel("Status: ");
		horizontalBox.add(lblStatusTitle);
		
		JLabel lblStatus = new JLabel("gestoppt");
		horizontalBox.add(lblStatus);
		
		Box verticalBox = Box.createVerticalBox();
		contentPane.add(verticalBox, BorderLayout.CENTER);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut);
		
		JPanel panelLocal = new JPanel();
		panelLocal.setMinimumSize(new Dimension(10, 200));
		panelLocal.setMaximumSize(new Dimension(32767, 400));
		panelLocal.setBorder(new LineBorder(new Color(0, 0, 0)));
		verticalBox.add(panelLocal);
		
		JLabel lblLokylesVerzeichnisZur = new JLabel("Lokyles Verzeichnis zur \u00DCberwachung:");
		
		textLocalDir = new JTextField();
		textLocalDir.setText("G:\\testLocal");
		textLocalDir.setColumns(10);
		
		btnPfadWhlen = new JButton("Pfad w\u00E4hlen");
		btnPfadWhlen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser fc = new JFileChooser();
		        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        int returnVal = fc.showOpenDialog(null);
		        File f;
		        if (returnVal == JFileChooser.APPROVE_OPTION)
		        {
		            f = fc.getSelectedFile();
		            textLocalDir.setText(f.getPath());
		        }
			}
		});
		
		JLabel lblLocaleEinstellungen = new JLabel("Locale Einstellungen");
		lblLocaleEinstellungen.setFont(new Font("Tahoma", Font.PLAIN, 22));
		GroupLayout gl_panelLocal = new GroupLayout(panelLocal);
		gl_panelLocal.setHorizontalGroup(
			gl_panelLocal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelLocal.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelLocal.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelLocal.createSequentialGroup()
							.addGroup(gl_panelLocal.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(textLocalDir, Alignment.LEADING)
								.addComponent(lblLokylesVerzeichnisZur, Alignment.LEADING))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPfadWhlen))
						.addComponent(lblLocaleEinstellungen))
					.addContainerGap(216, Short.MAX_VALUE))
		);
		gl_panelLocal.setVerticalGroup(
			gl_panelLocal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelLocal.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLocaleEinstellungen)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLokylesVerzeichnisZur)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelLocal.createParallelGroup(Alignment.BASELINE)
						.addComponent(textLocalDir, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPfadWhlen))
					.addContainerGap(169, Short.MAX_VALUE))
		);
		panelLocal.setLayout(gl_panelLocal);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_1);
		
		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(32767, 400));
		panel.setMinimumSize(new Dimension(10, 400));
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		verticalBox.add(panel);
		
		JLabel lblFtpEinstellungen = new JLabel("FTP Einstellungen");
		lblFtpEinstellungen.setFont(new Font("Tahoma", Font.PLAIN, 22));
		
		JLabel lblAdresse = new JLabel("Adresse:");
		
		textAdresse = new JTextField();
		textAdresse.setText("127.0.0.1");
		textAdresse.setColumns(10);
		
		JLabel lblBenutzernamen = new JLabel("Benutzernamen: ");
		
		textUsername = new JTextField();
		textUsername.setText("test");
		textUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password: ");
		
		pwdTest = new JPasswordField();
		pwdTest.setText("test1");
		pwdTest.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		
		textPort = new JTextField();
		textPort.setText("21");
		textPort.setColumns(10);
		
		JLabel lblSicherheit = new JLabel("Sicherheit:");
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"ohne Verschl\u00FCsselung", "FTP over implicit TLS/SSL", "FTP over explicit TLS/SSL"}));
		
		chckbxPassivmodus = new JCheckBox("Passivmodus");
		
		btnConnect = new JButton("Verbinden");
		btnConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
					boolean error = false;
					try {
						client = new FTPClient();
						
						client.setSecurity(comboBox.getSelectedIndex());
						client.setPassive(!chckbxPassivmodus.isSelected());
						client.connect(textAdresse.getText(), Integer.parseInt(textPort.getText()));
						client.login(textUsername.getText(), new String(pwdTest.getPassword()));
						client.setAutoNoopTimeout(10000);
						//model = new DefaultListModel<USFTPFile>();
						//model.addElement(new USFTPFile(true));
						model.removeAllElements();
						history.clear();
						textPath.setText("/");
						for(FTPFile file: client.list()){
							if(file.getType() == FTPFile.TYPE_DIRECTORY){
								model.addElement(new ListEntry(new USFTPFile(file),new ImageIcon(Toolkit.getDefaultToolkit().getImage(FTPWindow.class.getResource("/images/folder.png")))));
									
							}
						}
						textAdresse.setEnabled(false);
						textPort.setEnabled(false);
						textUsername.setEnabled(false);
						pwdTest.setEnabled(false);
						chckbxPassivmodus.setEnabled(false);
						comboBox.setEnabled(false);
						btnConnect.setEnabled(false);
						btnDiconnect.setEnabled(true);
					} catch (IllegalStateException e) {
						error = true;
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						error = true;
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FTPIllegalReplyException e) {
						error = true;
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FTPException e) {
						error = true;
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FTPDataTransferException e) {
						error = true;
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FTPAbortedException e) {
						error = true;
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FTPListParseException e) {
						error = true;
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						//client.logout();
						client.disconnect(true);
						textPath.setText("/");
					} catch (IllegalStateException | IOException | FTPIllegalReplyException | FTPException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					client = null;
					
					list.setEnabled(true);
				}
				
			
		});
		
		model = new DefaultListModel<USFTPFile>();
		
		
		JLabel lblFtpVerzeichnisse = new JLabel("FTP Verzeichnisse:");
		
		JScrollPane scrollPane = new JScrollPane();
		
		model = new DefaultListModel<USFTPFile>();
		list = new JList(model);
		list.setMaximumSize(new Dimension(0, 32000));
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2) {
					
					
						
						try {
							client = new FTPClient();
							client.setSecurity(comboBox.getSelectedIndex());
							client.setPassive(!chckbxPassivmodus.isSelected());
							client.connect(textAdresse.getText(), Integer.parseInt(textPort.getText()));
							client.login(textUsername.getText(), new String(pwdTest.getPassword()));
							client.setAutoNoopTimeout(10000);
							
							
							if(list.getSelectedIndex() == 0 && history.size() == 0){
								
								client.changeDirectory("/" + ((ListEntry)list.getSelectedValue()).getFile().getName());
								history.add(((ListEntry)list.getSelectedValue()).getFile().getName());
								model.removeAllElements();
								model.addElement(new ListEntry(new USFTPFile(true),new ImageIcon(Toolkit.getDefaultToolkit().getImage(FTPWindow.class.getResource("/images/previous.png")))));
								
								String path = "";
								if(history.size() == 0){
									path = "/";
								}else{
									for(String dir : history){
										path = path + "/" + dir;
									}
								}
								textPath.setText(path);
								
								for(FTPFile file: client.list()){
									if(file.getType() == FTPFile.TYPE_DIRECTORY){
										model.addElement(new ListEntry(new USFTPFile(file),new ImageIcon(Toolkit.getDefaultToolkit().getImage(FTPWindow.class.getResource("/images/folder.png")))));
									}
									
								}
							}else{
								if(list.getSelectedIndex() == 0){
									history.remove(history.size()-1);
								}else{
									history.add(((ListEntry)list.getSelectedValue()).getFile().getName());
								}
								
								String path = "";
								if(history.size() == 0){
									path = "/";
								}else{
									for(String dir : history){
										path = path + "/" + dir;
									}
								}
								textPath.setText(path);
								client.changeDirectory(path);
								model.removeAllElements();
								if(history.size() != 0){
									model.addElement(new ListEntry(new USFTPFile(true),new ImageIcon(Toolkit.getDefaultToolkit().getImage(FTPWindow.class.getResource("/images/previous.png")))));
								}
								
								for(FTPFile file: client.list()){
									if(file.getType() == FTPFile.TYPE_DIRECTORY){
										model.addElement(new ListEntry(new USFTPFile(file),new ImageIcon(Toolkit.getDefaultToolkit().getImage(FTPWindow.class.getResource("/images/folder.png")))));
									}
									
								}
								
								
							}
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (FTPIllegalReplyException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (FTPException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (FTPDataTransferException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (FTPAbortedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (FTPListParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							//client.logout();
							client.disconnect(true);
						} catch (IllegalStateException | IOException | FTPIllegalReplyException | FTPException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						client = new FTPClient();
					
				}
			}
		});
		list.setEnabled(false);
		list.setCellRenderer(new ListEntryCellRenderer());
		scrollPane.setViewportView(list);
		
		btnDiconnect = new JButton("Disconnect");
		btnDiconnect.setEnabled(false);
		btnDiconnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					if(client != null){
						client.disconnect(true);
						
					}
				} catch (IllegalStateException | IOException | FTPIllegalReplyException | FTPException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}finally {
					textAdresse.setEnabled(true);
					textPort.setEnabled(true);
					textUsername.setEnabled(true);
					pwdTest.setEnabled(true);
					chckbxPassivmodus.setEnabled(true);
					comboBox.setEnabled(true);
					btnConnect.setEnabled(true);
					btnDiconnect.setEnabled(false);
					textPath.setText("");
					model.removeAllElements();
					
				}
			}
		});
		
		textPath = new JTextField();
		textPath.setEditable(false);
		textPath.setColumns(10);
		
		Box verticalBox_1 = Box.createVerticalBox();
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFtpEinstellungen)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(btnConnect)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnDiconnect))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(textUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(pwdTest, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(textAdresse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblAdresse)
										.addComponent(lblBenutzernamen))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblPassword)
										.addComponent(lblPort)
										.addComponent(textPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addComponent(lblSicherheit)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(chckbxPassivmodus))
							.addGap(33)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(verticalBox_1, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblFtpVerzeichnisse))
								.addComponent(textPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(72, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblFtpEinstellungen)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblAdresse)
							.addComponent(lblPort)
							.addComponent(lblFtpVerzeichnisse))
						.addComponent(verticalBox_1, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textAdresse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblBenutzernamen)
								.addComponent(lblPassword))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(textUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(pwdTest, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblSicherheit)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(chckbxPassivmodus)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnConnect)
								.addComponent(btnDiconnect)))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))
					.addGap(30))
		);
		panel.setLayout(gl_panel);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_2);
		File test = new File(System.getProperty( "user.home" ));
		
		
	}
	
	/*public void fillLocalTree(){
		treeLocal.setModel(new DefaultTreeModel(fillLocalNode(new File(System.getProperty( "user.home" )), true)));
		
	}*/
	public DefaultMutableTreeNode fillLocalNode(File dir, boolean newRoot){
		DefaultMutableTreeNode node = new DefaultMutableTreeNode();
		fillLocalNode(node, dir, newRoot);
		return node;
	}
	
	public void fillLocalNode(DefaultMutableTreeNode node, File dir, boolean newRoot){
		if(newRoot){
			node = new DefaultMutableTreeNode();
		}
		if(dir.listFiles() == null){
			return;
		}
		for(File subDir : dir.listFiles()){
			
			if(!subDir.isDirectory()){
				continue;
			}
			DefaultMutableTreeNode entry = new DefaultMutableTreeNode(subDir.getName());
			fillLocalNode(entry, subDir, false);
			node.add(entry);
			
		}
		
	}
	
	public void enableUI(boolean value){

		textUsername.setEnabled(value);
		textAdresse.setEnabled(value);
		textLocalDir.setEnabled(value);
		textPort.setEnabled(value);
		btnDiconnect.setEnabled(value);
		list.setEnabled(value);
		comboBox.setEnabled(value);
		btnPfadWhlen.setEnabled(value);
		chckbxPassivmodus.setEnabled(value);
		
	}
}
