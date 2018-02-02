package org.rothmayer.UltiShot.DB.SMBD;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Mannschaft
 *
 */
@Entity
@Table(name = "Mannschaft")
public class Mannschaft implements Serializable, Comparable<Mannschaft> {


	public Mannschaft() {
		
	}

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "MannschaftsID", columnDefinition = "INT(11)")
	private int mannschaftsID;
	
	@OneToMany(targetEntity = Starterliste.class, mappedBy = "mannschaft")
	private Set<Mannschaft> mannschaft;
	
	@Column(name = "MannschaftsName", columnDefinition = "VARCHAR(36)")
	private String mannschaftsName;
	
	@Column(name = "VereinsID", columnDefinition = "INT(11)")
	private int vereinsID;

	public int getMannschaftsID() {
		return mannschaftsID;
	}

	public void setMannschaftsID(int mannschaftsID) {
		this.mannschaftsID = mannschaftsID;
	}

	public String getMannschaftsName() {
		return mannschaftsName;
	}

	public void setMannschaftsName(String mannschaftsName) {
		this.mannschaftsName = mannschaftsName;
	}

	public int getVereinsID() {
		return vereinsID;
	}

	public void setVereinsID(int vereinsID) {
		this.vereinsID = vereinsID;
	}

	public Set<Mannschaft> getMannschaft() {
		return mannschaft;
	}

	public void setMannschaft(Set<Mannschaft> mannschaft) {
		this.mannschaft = mannschaft;
	}
	
	@Override
	public String toString(){
		return this.getMannschaftsName();
	}

	@Override
	public int compareTo(Mannschaft o) {
		return this.toString().compareTo(o.toString());
	}
		   
}
