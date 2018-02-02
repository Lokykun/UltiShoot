package org.rothmayer.UltiShot.DB.SSMBD2;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Embeddable
public class SerienPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "ScheibenID", columnDefinition = "INT(11)")
	private int scheibenID;
	
	@Column(name = "Stellung", columnDefinition = "SMALLINT(6)")
	private short stellung;
	
	@Column(name = "Serie", columnDefinition = "SMALLINT(6)")
	private short serie;

	public SerienPK() {
	}

	public SerienPK(int scheibenID, short stellung, short serie) {
		this.scheibenID = scheibenID;
		this.stellung = stellung;
		this.serie = serie;
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

	public short getSerie() {
		return serie;
	}

	public void setSerie(short serie) {
		this.serie = serie;
	}
	
	@Override
	public boolean equals(Object other) {
	    if (this == other)
	        return true;
	    if (!(other instanceof SerienPK))
	        return false;
	    SerienPK castOther = (SerienPK) other;
	    return (this.serie == castOther.serie) && (this.stellung == castOther.stellung) && (this.scheibenID == castOther.scheibenID);
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int hash = 17;
	    hash = hash * prime + this.stellung;
	    hash = hash * prime + this.serie;
	    hash = hash * prime + this.scheibenID;
	    return hash;
	}
	
	

}
