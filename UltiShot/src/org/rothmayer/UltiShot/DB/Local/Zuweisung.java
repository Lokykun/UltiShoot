package org.rothmayer.UltiShot.DB.Local;

import java.io.Serializable;
import javax.persistence.*;


/**
 * Entity implementation class for Entity: Zuweisung
 *
 */
@Entity
@Table(name = "Zuweisung")
public class Zuweisung implements Serializable, Comparable<Zuweisung> {


	public Zuweisung(int sportpassID, int starterlistenID, int mannschaftsID, int reihenfolge, boolean kombiniert) {
		super();
		this.reihenfolge = reihenfolge;
		this.kombiniert = kombiniert;
		this.zuweisungPK = new ZuweisungPK(sportpassID, starterlistenID, mannschaftsID);
	}

	public Zuweisung() {
		
	}

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ZuweisungPK zuweisungPK;
	
	@Column(name = "Reihenfolge", columnDefinition = "INT(10)")
	private int reihenfolge;
	
	@Column(name = "Kombiniert", columnDefinition = "ENUM(1)")
	private boolean kombiniert;

	public ZuweisungPK getZuweisungPK() {
		return zuweisungPK;
	}

	public void setZuweisungPK(ZuweisungPK zuweisungPK) {
		this.zuweisungPK = zuweisungPK;
	}

	public int getReihenfolge() {
		return reihenfolge;
	}

	public void setReihenfolge(int reihenfolge) {
		this.reihenfolge = reihenfolge;
	}

	public boolean isKombiniert() {
		return kombiniert;
	}

	public void setKombiniert(boolean kombiniert) {
		this.kombiniert = kombiniert;
	}

	@Override
	public int compareTo(Zuweisung o) {
		if(Integer.compare( this.getZuweisungPK().getSportpassID(), o.getZuweisungPK().getSportpassID()) != 0 ){
			return Integer.compare( this.getZuweisungPK().getSportpassID(), o.getZuweisungPK().getSportpassID());
		}
		return Integer.compare(this.getReihenfolge(), o.getReihenfolge());
	}
			   
}