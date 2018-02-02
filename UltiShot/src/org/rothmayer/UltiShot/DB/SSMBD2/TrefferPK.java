package org.rothmayer.UltiShot.DB.SSMBD2;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TrefferPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ScheibenID", columnDefinition = "INT(6)")
	private int scheibenID;
	
	@Column(name = "Stellung", columnDefinition = "SMALLINT(6)")
	private short stellung;
	
	@Column(name = "Treffer", columnDefinition = "SMALLINT(6)")
	private short treffer;
	
	public TrefferPK(){
	}

	public TrefferPK(int scheibenID, short stellung, short treffer) {
		this.scheibenID = scheibenID;
		this.stellung = stellung;
		this.treffer = treffer;
	}

	public int getScheibenID() {
		return scheibenID;
	}

	public void setScheibenID(int scheibenID) {
		this.scheibenID = scheibenID;
	}

	public short getStellung() {
		return stellung;
	}

	public void setStellung(short stellung) {
		this.stellung = stellung;
	}

	public short getTreffer() {
		return treffer;
	}

	public void setTreffer(short treffer) {
		this.treffer = treffer;
	}
	
	@Override
	public boolean equals(Object other) {
	    if (this == other)
	        return true;
	    if (!(other instanceof TrefferPK))
	        return false;
	    TrefferPK castOther = (TrefferPK) other;
	    return (this.treffer == castOther.treffer) && (this.stellung == castOther.stellung) && (this.scheibenID == castOther.scheibenID);
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int hash = 17;
	    hash = hash * prime + this.treffer;
	    hash = hash * prime + this.stellung;
	    hash = hash * prime + this.scheibenID;
	    return hash;
	}
	
	
	
	
	
	

}
