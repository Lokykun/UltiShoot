package org.rothmayer.UltiShot.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.rothmayer.UltiShot.DB.SMBD.Starterlisten;
import org.rothmayer.UltiShot.GUI.TargetAssignmentWindow;

public class Worker extends TimerTask{
	
	TargetAssignmentWindow window;

	@Override
	public void run() {
		
		int starterlisten = ((Starterlisten)window.getStarterlistenBox().getSelectedItem()).getListenID();
		
		List<Integer> scheibeIDs = new ArrayList<>();
		
		for(Scheibe sch : ((List<Scheibe>)window.getList().getSelectedValuesList())){
			scheibeIDs.add(sch.getDisziplinID());
		}
		
		//window.getAus().doAuswertung(starterlisten, scheibeIDs, window.getComboBoxMannschaft().getSelectedIndex()+1, window.getComboBoxProfi().getSelectedIndex()+1);
		
	}

	public Worker(TargetAssignmentWindow window) {
		this.window = window;
		
		window.getList().setEnabled(false);
		window.getStarterlistenBox().setEnabled(false);
		window.getComboBoxMannschaft().setEnabled(false);
		window.getComboBoxProfi().setEnabled(false);
		window.getBtnAuswertungStarten().setText("Auswertung beenden");
	}
	
	public void cancelTask(){
		window.getList().setEnabled(true);
		window.getStarterlistenBox().setEnabled(true);
		window.getComboBoxMannschaft().setEnabled(true);
		window.getComboBoxProfi().setEnabled(true);
		window.getBtnAuswertungStarten().setText("Auswertung starten");
		this.cancel();
	}

}
