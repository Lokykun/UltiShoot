package org.rothmayer.UltiShot.GUI.elements;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class JImage extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5752878888690561794L;
	private Image img;
	
	public JImage(Image img){
		this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	}
	
	 public void paintComponent(Graphics g) {
		    g.drawImage(img, 0, 0, null);
		  }

}
