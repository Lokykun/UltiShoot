package org.rothmayer.UltiShot.DB.SMBD;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Starterlisten
 *
 */
@Entity
@Table(name = "Starterliste")
public class Starterliste implements Serializable {


	public Starterliste() {
		
	}

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private StarterlistePK startListePK;
	
	@ManyToOne
	@JoinColumn(name = "SportpassID", insertable = false, updatable = false, referencedColumnName="SportpassID")
	private Schuetze schuetze;

	@ManyToOne
	@JoinColumn(name = "ListenID", insertable = false, updatable = false, referencedColumnName="ListenID")
	private Starterlisten starterlisten;
	
	@ManyToOne
	@JoinColumn(name = "MannschaftsID", insertable = false, updatable = false, referencedColumnName="MannschaftsID")
	private Mannschaft mannschaft;
	
	@ManyToOne
	@JoinColumn(name = "KlassenID", insertable = false, updatable = false, referencedColumnName="KlassenID")
	private Klasse klasse;
	
	@Column(name = "RueckenNr", columnDefinition = "INT(10)")
	private int rueckenNr;
	
	@Column(name = "Startzeit", columnDefinition = "TIMESTAMP")
	private Timestamp startzeit;
	
	@Column(name = "StandNr", columnDefinition = "INT(10)")
	private int standNr;
	
	@Column(name = "SportpassID", columnDefinition = "INT(10)")
	private int sportpassID;
	
	@Column(name = "MannschaftsID", columnDefinition = "INT(11)")
	private int mannschaftsID;
	
	@Column(name = "VerbandsID", columnDefinition = "INT(11)")
	private int verbandsID;

	@Column(name = "KlassenID", columnDefinition = "INT(11)")
	private int klassenID;
	
	@Column(name = "VereinsID", columnDefinition = "INT(11)")
	private int vereinsID;
	
	@Column(name = "DzplID", columnDefinition = "INT(11)")
	private int dzplID;
	
	@Column(name = "RanglistenID", columnDefinition = "INT(11)")
	private int ranglistenID;
	
	@Column(name = "Vorkampf", columnDefinition = "SMALLINT(5)")
	private int vorkampf;
	
	@Column(name = "Meldeergebnis", columnDefinition = "SMALLINT(5)")
	private int meldeergebnis;
	
	@Column(name = "GruppenID", columnDefinition = "INT(11)")
	private int gruppenID;
	
	@Column(name = "StarterFlags", columnDefinition = "INT(11)")
	private int starterFlags;
	
	@Column(name = "Erfasst", columnDefinition = "TIMESTAMP")
	private Timestamp erfasste;

	public StarterlistePK getStartListePK() {
		return startListePK;
	}

	public void setStartListePK(StarterlistePK startListePK) {
		this.startListePK = startListePK;
	}

	public int getRueckenNr() {
		return rueckenNr;
	}

	public void setRueckenNr(int rueckenNr) {
		this.rueckenNr = rueckenNr;
	}

	public Timestamp getStartzeit() {
		return startzeit;
	}

	public void setStartzeit(Timestamp startzeit) {
		this.startzeit = startzeit;
	}

	public int getStandNr() {
		return standNr;
	}

	public void setStandNr(int standNr) {
		this.standNr = standNr;
	}

	public int getSportpassID() {
		return sportpassID;
	}

	public void setSportpassID(int sportpassID) {
		this.sportpassID = sportpassID;
	}

	public int getMannschaftsID() {
		return mannschaftsID;
	}

	public void setMannschaftsID(int mannschaftsID) {
		this.mannschaftsID = mannschaftsID;
	}

	public int getVerbandsID() {
		return verbandsID;
	}

	public void setVerbandsID(int verbandsID) {
		this.verbandsID = verbandsID;
	}

	public int getKlassenID() {
		return klassenID;
	}

	public void setKlassenID(int klassenID) {
		this.klassenID = klassenID;
	}

	public int getVereinsID() {
		return vereinsID;
	}

	public void setVereinsID(int vereinsID) {
		this.vereinsID = vereinsID;
	}

	public int getDzplID() {
		return dzplID;
	}

	public void setDzplID(int dzplID) {
		this.dzplID = dzplID;
	}

	public int getRanglistenID() {
		return ranglistenID;
	}

	public void setRanglistenID(int ranglistenID) {
		this.ranglistenID = ranglistenID;
	}

	public int getVorkampf() {
		return vorkampf;
	}

	public void setVorkampf(int vorkampf) {
		this.vorkampf = vorkampf;
	}

	public int getMeldeergebnis() {
		return meldeergebnis;
	}

	public void setMeldeergebnis(int meldeergebnis) {
		this.meldeergebnis = meldeergebnis;
	}

	public int getGruppenID() {
		return gruppenID;
	}

	public void setGruppenID(int gruppenID) {
		this.gruppenID = gruppenID;
	}

	public int getStarterFlags() {
		return starterFlags;
	}

	public void setStarterFlags(int starterFlags) {
		this.starterFlags = starterFlags;
	}

	public Timestamp getErfasste() {
		return erfasste;
	}

	public void setErfasste(Timestamp erfasste) {
		this.erfasste = erfasste;
	}

	public Schuetze getSchuetze() {
		return schuetze;
	}

	public Starterlisten getStarterlisten() {
		return starterlisten;
	}

	public void setStarterlisten(Starterlisten starterlisten) {
		this.starterlisten = starterlisten;
	}

	public Klasse getKlasse() {
		return klasse;
	}

	public void setKlasse(Klasse klasse) {
		this.klasse = klasse;
	}

	public Mannschaft getMannschaft() {
		return mannschaft;
	}

	public void setMannschaft(Mannschaft mannschaft) {
		this.mannschaft = mannschaft;
	}

	public void setSchuetze(Schuetze schuetze) {
		this.schuetze = schuetze;
	}
		   
}
