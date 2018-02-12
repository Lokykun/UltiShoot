package org.rothmayer.UltiShot.GUI.elements;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.rothmayer.UltiShot.GUI.FilterWindow;

public class FilterAction implements ActionListener {
	
	FilterWindow window;
	
	public FilterAction(FilterWindow window){
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		window.changeFilter();
		
	}

	

	}