package org.rothmayer.UltiShot.Util;


import static java.nio.file.StandardWatchEventKinds.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.rothmayer.UltiShot.GUI.FTPWindow;
import org.rothmayer.UltiShot.GUI.UltiShot;

import com.mysql.jdbc.StringUtils;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

public class Watcher extends TimerTask{
	 

	private final WatchService watcher;
	    private final Map<WatchKey, Path> keys;
	    private boolean run;
	    private int sec;
	    private String adresse;
	    private String user;
	    private String password;
	    private int port;
	    private boolean passiv;
	    private FTPClient client;
	    private Path rootDir;
	    private FTPWindow window;
	 
	    /**
	     * Creates a WatchService and registers the given directory
	     * @throws IOException 
	     */
	    
	    public Watcher(FTPWindow window, Path rootDir, int sec, String adresse, String user,
				String password, int port, boolean passiv) throws IOException {
			this.watcher = FileSystems.getDefault().newWatchService();
			this.keys = new HashMap<WatchKey, Path>();
			this.window = window;
			this.sec = sec;
			this.adresse = adresse;
			this.user = user;
			this.password = password;
			this.port = port;
			this.passiv = passiv;
	    	run = true;
	    	this.rootDir = rootDir;
	        walkAndRegisterDirectories(rootDir);
		}
	    
	    /**Watcher(Path dir) throws IOException {
	        this.watcher = FileSystems.getDefault().newWatchService();
	        this.keys = new HashMap<WatchKey, Path>();
	 
	        walkAndRegisterDirectories(dir);
	    }**/
	 
	    /**
	     * Register the given directory with the WatchService; This function will be called by FileVisitor
	     */
	    private void registerDirectory(Path dir) throws IOException
	    {
	        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
	        UltiShot.logger.info("FTP Watcher register: " + dir.toFile().getAbsolutePath());
	        keys.put(key, dir);
	    }
	 
	    /**
	     * Register the given directory, and all its sub-directories, with the WatchService.
	     */
	    private void walkAndRegisterDirectories(final Path start) throws IOException {
	        // register directory and sub-directories
	        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
	            @Override
	            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
	                registerDirectory(dir);
	                return FileVisitResult.CONTINUE;
	            }
	        });
	    }
	 
	    /**
	     * Process all events for keys queued to the watcher
	     */
	    void createDir(){
	    	
	    }
	    
	    void processEvents() throws IllegalStateException, FTPIllegalReplyException, FTPException, FTPDataTransferException, FTPAbortedException, IOException {
	        while (run) {
	 
	            // wait for key to be signalled
	            WatchKey key;
	            try {
	                key = watcher.poll(1000, TimeUnit.MILLISECONDS);
	            } catch (InterruptedException x) {
	                return;
	            }
	            if(!run){
	            	break;
	            }
	            Path dir = keys.get(key);
	            if (dir == null) {
	                //System.err.println("WatchKey not recognized!!");
	                continue;
	            }
	            client = new FTPClient();
	            client.setSecurity(sec);
	            client.setPassive(passiv);
	            client.connect(adresse, port);
	            client.login(user, password);
	            client.setAutoNoopTimeout(1000);
	            for (WatchEvent<?> event : key.pollEvents()) {
	                @SuppressWarnings("rawtypes")
	                WatchEvent.Kind kind = event.kind();
	 
	                // Context for directory entry event is the file name of entry
	                @SuppressWarnings("unchecked")
	                Path name = ((WatchEvent<Path>)event).context();
	                Path child = dir.resolve(name);
	 
	                // print out event
	                //System.out.format("%s: %s\n", event.kind().name(), child);
	 
	                //System.out.println("Dir: " + dir);
	                //System.out.println("Name: " + name);
	                //System.out.println("Child: " + child);
	                
	                File file = new File("" + child);
	                
	                String pathSubRoot = (dir.toString()).replaceFirst(Pattern.quote(rootDir + "\\"), "");
	                //System.out.println("Relativ: " + pathSubRoot);
	                
	                
	                
	                if(file.isDirectory()){
	                	if (kind == ENTRY_CREATE) {
	                		walkAndRegisterDirectories(child);
	                	}
	                }else{
	                	if (kind != ENTRY_DELETE) {
	                		try {
								uploadFile(file, pathSubRoot, client);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
							}
	                	}
	                }
	                //String watchPath = event.
	                // if directory is created, and watching recursively, then register it and its sub-directories
	               /** if (kind == ENTRY_CREATE) {
	                    try {
	                        if (Files.isDirectory(child)) {
	                            walkAndRegisterDirectories(child);
	                        }
	                    } catch (IOException x) {
	                        // do something useful
	                    }
	                }**/
	            }
	 
	            // reset key and remove from set if directory no longer accessible
	            boolean valid = key.reset();
	            if (!valid) {
	                keys.remove(key);
	 
	                // all directories are inaccessible
	                if (keys.isEmpty()) {
	                    break;
	                }
	            }
	        }
	    }

	    public void uploadFile(File file, String pathSubRoot, FTPClient client) throws IllegalStateException, IOException, FTPIllegalReplyException, FTPException, FTPDataTransferException, FTPAbortedException{
	    	client.changeDirectory("/");
	    	String[] pathArray = pathSubRoot.split("\\\\");
	    	for(String str : pathArray){
				String t = str.replace("\\", "");
				//System.out.println(t);
				
				try {
					//System.out.println("Change Dir " + t);
					client.changeDirectory(t);
				} catch(FTPException e) {
					//System.out.println("Dir not exist");
					client.createDirectory(t);
					client.changeDirectory(t);
				}
				
				
			}
	    	//System.out.println("Current:" + client.currentDirectory());
			//System.out.println("Upload: " + file.getAbsolutePath());
			client.upload(file);
	    }
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			try {
				processEvents();
			} catch (IllegalStateException | FTPIllegalReplyException | FTPException | FTPDataTransferException
					| FTPAbortedException | IOException e) {
				// TODO Auto-generated catch block
				window.taskError(e);
			}
		}

		public boolean isRun() {
			return run;
		}

		public void setRun(boolean run) {
			this.run = run;
		}
	}