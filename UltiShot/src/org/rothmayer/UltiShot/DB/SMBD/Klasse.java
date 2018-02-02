package org.rothmayer.UltiShot.DB.SMBD;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Mannschaft
 *
 */
@Entity
@Table(name = "Klasse")
public class Klasse implements Serializable {


	public Klasse() {
		
	}

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "KlassenID", columnDefinition = "INT(4)")
	private int klassenID;
	
	@OneToMany(targetEntity = Starterliste.class, mappedBy = "klasse")
	private Set<Klasse> klasse;
	
	@Column(name = "KlassenName", columnDefinition = "VARCHAR(36)")
	private String klassenName;
	
	@Column(name = "minAlter", columnDefinition = "INT(4)")
	private int minAlter;
	
	@Column(name = "maxAlter", columnDefinition = "INT(4)")
	private int maxAlter;
	
	@Column(name = "Geschlecht", columnDefinition = "ENUM(9)")
	private String geschlecht;

	public int getKlassenID() {
		return klassenID;
	}

	public void setKlassenID(int klassenID) {
		this.klassenID = klassenID;
	}

	public Set<Klasse> getKlasse() {
		return klasse;
	}

	public void setKlasse(Set<Klasse> klasse) {
		this.klasse = klasse;
	}

	public String getKlassenName() {
		return klassenName;
	}

	public void setKlassenName(String klassenName) {
		this.klassenName = klassenName;
	}

	public int getMinAlter() {
		return minAlter;
	}

	public void setMinAlter(int minAlter) {
		this.minAlter = minAlter;
	}

	public int getMaxAlter() {
		return maxAlter;
	}

	public void setMaxAlter(int maxAlter) {
		this.maxAlter = maxAlter;
	}

	public String getGeschlecht() {
		return geschlecht;
	}

	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}
		   
}
