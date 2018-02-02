package org.rothmayer.UltiShot.DB.SMBD;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StarterlistePK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "ListenID", columnDefinition = "INT(11)")
	private int listenID;
	
	@Column(name = "StartID", columnDefinition = "INT(11)")
	private int startID;

	public StarterlistePK() {
	}

	public StarterlistePK(int listenID, int startID) {
		this.listenID = listenID;
		this.startID = startID;
	}
	
	@Override
	public boolean equals(Object other) {
	    if (this == other)
	        return true;
	    if (!(other instanceof StarterlistePK))
	        return false;
	    StarterlistePK castOther = (StarterlistePK) other;
	    return ((this.startID == castOther.startID) && (this.listenID == castOther.listenID));
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int hash = 17;
	    hash = hash * prime + this.startID;
	    hash = hash * prime + this.listenID;
	    return hash;
	}

	public int getListenID() {
		return listenID;
	}

	public void setListenID(int listenID) {
		this.listenID = listenID;
	}

	public int getStartID() {
		return startID;
	}

	public void setStartID(int startID) {
		this.startID = startID;
	}
	
	

}
