package org.rothmayer.UltiShot.Util;

import it.sauronsoftware.ftp4j.FTPFile;

public class USFTPFile{
	private boolean backEntry = false;
	
	public USFTPFile(boolean backEntry) {
		this.backEntry = backEntry;
	}

	
	public USFTPFile(FTPFile file) {
		this.file = file;
	}

	private FTPFile file;

	@Override
	public String toString() {
		if(backEntry){
			return "... ZURÜCK";
		}
		return "    " + file.getName();
	}

	public FTPFile getFile() {
		return file;
	}


	public boolean isBackEntry() {
		return backEntry;
	}

}
