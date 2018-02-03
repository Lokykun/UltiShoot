package org.rothmayer.UltiShot.GUI.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JProgressBar;

public class USProgressbar extends JProgressBar{
	
	private float lengthPerTick;
	
	private int tick;
	
	private int currentTicks = 0;
	
	private String message;
	
	private boolean haveTick = true;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1515756197151173499L;
	
	public USProgressbar(int tick, String message) {
		super();
		this.setMaximum(1000);
		this.tick = tick;
		this.message = message;
		this.lengthPerTick = (float) this.getWidth() / (float) this.tick;

		this.setBackground(Color.DARK_GRAY);
		this.setForeground(Color.ORANGE);
		this.setMinimumSize(new Dimension(this.getWidth(), 30));
		this.setPreferredSize(new Dimension(this.getWidth(), 30));
	}
	
	public USProgressbar(int tick) {
		super();
		this.setMaximum(1000);
		this.tick = tick;
		this.message = "";
        this.lengthPerTick = (float) this.getMaximum()/ (float) this.tick;
		this.setBackground(Color.DARK_GRAY);
		this.setForeground(Color.ORANGE);
		this.setMinimumSize(new Dimension(this.getWidth(), 30));
		this.setPreferredSize(new Dimension(this.getWidth(), 30));
	}
	
	public USProgressbar() {
		super();
		this.setMaximum(1000);
		this.haveTick = false;
		this.message = "";
        this.lengthPerTick = (float) this.getMaximum()/ (float) this.tick;
		this.setBackground(Color.DARK_GRAY);
		this.setForeground(Color.ORANGE);
		this.setMinimumSize(new Dimension(this.getWidth(), 30));
		this.setPreferredSize(new Dimension(this.getWidth(), 30));
	}
	
	public void addTick(){
		
		this.currentTicks++;
        this.lengthPerTick = (float) this.getMaximum()/ (float) this.tick;
		int tempValue = this.getValue();
		if(!this.haveTick){
			tempValue ++;
		}else{
			tempValue += Math.ceil(currentTicks * lengthPerTick + 0.5);
		}
		if(tempValue > this.getMaximum()){
			tempValue = this.getMaximum();
		}
		this.setValue(tempValue);
	}
	

	public int getCurrentTicks() {
		return currentTicks;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
    public void paint(Graphics g) {
        //super.paintComponent(g);
        int currentLength = Math.round(((float) this.getWidth() / (float) this.getMaximum()) * this.getValue());

        int width = g.getFontMetrics().stringWidth(message);
        int height = g.getFontMetrics().getHeight();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), this.getHeight());
        
        g.setColor(Color.ORANGE);
        g.drawString(message, this.getWidth()/2 - width/2 ,this.getHeight()/2 + height/2-2);
        
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, currentLength, this.getHeight());
        g.setColor(Color.DARK_GRAY);
        g.setClip(0, 0, currentLength, this.getHeight());
        g.drawString(message, this.getWidth()/2 - width/2 ,this.getHeight()/2 + height/2-2);
        g.setClip(null);
        
        
    }

}
