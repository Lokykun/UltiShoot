package org.rothmayer.UltiShot.Util;

import javax.swing.ImageIcon;

import it.sauronsoftware.ftp4j.FTPFile;

public class ListEntry {
	   private USFTPFile file;
	   private ImageIcon icon;
	  
	   public ListEntry(USFTPFile file, ImageIcon icon) {
	      this.file = file;
	      this.icon = icon;
	   }
	  
	   public String getValue() {
	      return file.toString();
	   }
	  
	   public ImageIcon getIcon() {
	      return icon;
	   }
	  
	   public String toString() {
		   return file.toString();
	   }
	   
	   public FTPFile getFile(){
		   return file.getFile();
	   }
	}
