package org.rothmayer.UltiShot.Check;

import java.io.File;

import org.rothmayer.UltiShot.GUI.UltiShot;

public class CheckWritePermission implements StartupCheck {

	@Override
	public boolean performCheck() {
		File dir = new File(UltiShot.path);
		if(dir.exists()){
			if(dir.canWrite()){
				return true;
			}
		}
		return false;
	}

	@Override
	public String getMessage() {
		return "Prüfe schreibberechtigung!";
	}

	@Override
	public String getErrorMessage() {
		return "Can't write to the programm directory " + UltiShot.path;
	}

}
