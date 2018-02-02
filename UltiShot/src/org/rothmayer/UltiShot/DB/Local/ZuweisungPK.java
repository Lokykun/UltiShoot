package org.rothmayer.UltiShot.DB.Local;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ZuweisungPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "SportpassID", columnDefinition = "INT(11)")
	private int sportpassID;
	
	@Column(name = "StarterlistenID", columnDefinition = "INT(11)")
	private int starterlistenID;
	
	@Column(name = "MannschaftsID", columnDefinition = "INT(11)")
	private int mannschaftsID;

	public ZuweisungPK() {
	}

	public ZuweisungPK(int sportpassID, int starterlistenID, int mannschaftsID) {
		this.sportpassID = sportpassID;
		this.starterlistenID = starterlistenID;
		this.mannschaftsID = mannschaftsID;
	}
	
	@Override
	public boolean equals(Object other) {
	    if (this == other)
	        return true;
	    if (!(other instanceof ZuweisungPK))
	        return false;
	    ZuweisungPK castOther = (ZuweisungPK) other;
	    return ((this.sportpassID == castOther.sportpassID) && (this.starterlistenID == castOther.starterlistenID) && (this.mannschaftsID == castOther.mannschaftsID));
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int hash = 17;
	    hash = hash * prime + this.starterlistenID;
	    hash = hash * prime + this.sportpassID;
	    hash = hash * prime + this.mannschaftsID;
	    return hash;
	}

	public int getSportpassID() {
		return sportpassID;
	}

	public void setSportpassID(int sportpassID) {
		this.sportpassID = sportpassID;
	}

	public int getStarterlistenID() {
		return starterlistenID;
	}

	public void setStarterlistenID(int starterlistenID) {
		this.starterlistenID = starterlistenID;
	}

	public int getMannschaftsID() {
		return mannschaftsID;
	}

	public void setMannschaftsID(int mannschaftsID) {
		this.mannschaftsID = mannschaftsID;
	}
	

}
