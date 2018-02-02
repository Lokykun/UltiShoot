package org.rothmayer.UltiShot.DB.SSMBD2;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "Scheiben")
@Table(name = "Scheiben")
public class Scheiben implements Serializable, Comparable<Scheiben> {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = -6209276897338746254L;


	public Scheiben() {
		
	}


	@Id 
	@Column(name = "ScheibenID", columnDefinition = "INT(11)")
	private int scheibenID;
	
	@OneToMany(targetEntity = Treffer.class, mappedBy = "scheibe")
	private Set<Treffer> treffer;
	
	@OneToMany(targetEntity = Serien.class, mappedBy = "scheibe")
	private Set<Serien> serien;
	
	@Column(name = "Starterliste", columnDefinition = "VARCHAR(36)")
	private String starterliste;
	
	@Column(name = "StarterlistenID", columnDefinition = "INT(11)")
	private int starterlistenID;
	
	@Column(name = "StartNr", columnDefinition = "INT(11)")
	private int startNr;
	
	@Column(name = "Nachname", columnDefinition = "VARCHAR(36)")
	private String nachname;
	
	@Column(name = "Vorname", columnDefinition = "VARCHAR(36)")
	private String vorname;
	
	@Column(name = "SportpassID", columnDefinition = "INT(11)")
	private int sportpassID;
	
	@Column(name = "StandNr", columnDefinition = "SMALLINT(6)")
	private short standNr;
	
	@Column(name = "Disziplin", columnDefinition = "VARCHAR(36)")
	private String disziplin;
	
	@Column(name = "DisziplinID", columnDefinition = "INT(11)")
	private int disziplinID;
	
	@Column(name = "Klasse", columnDefinition = "VARCHAR(36)")
	private String klasse;

	@Column(name = "KlassenID", columnDefinition = "INT(11)")
	private int klassenID;
	
	@Column(name = "Verein", columnDefinition = "VARCHAR(36)")
	private String verein;
	
	@Column(name = "VereinsID", columnDefinition = "INT(11)")
	private int vereinsID;

	@Column(name = "Mannschaft", columnDefinition = "VARCHAR(36)")
	private String mannschaft;
	
	@Column(name = "MannschaftsID", columnDefinition = "INT(11)")
	private int mannschaftsID;
	
	@Column(name = "Rangliste", columnDefinition = "VARCHAR(36)")
	private String rangliste;

	@Column(name = "RanglistenID", columnDefinition = "INT(11)")
	private int ranglistenID;
	
	@Column(name = "Trefferzahl", columnDefinition = "SMALLINT(6)")
	private short trefferzahl;

	@Column(name = "TotalRing", columnDefinition = "INT(11)")
	private int totalRing;

	@Column(name = "TotalRing01", columnDefinition = "INT(11)")
	private int totalRing01;

	@Column(name = "BesterTeiler01", columnDefinition = "INT(11)")
	private int besterTeiler01;

	@Column(name = "Zeitstempel", columnDefinition = "DATETIME")
	private Timestamp zeitstempel;


	public int getScheibenID() {
		return scheibenID;
	}

	public void setScheibenID(int scheibenID) {
		this.scheibenID = scheibenID;
	}

	public String getStarterliste() {
		return starterliste;
	}

	public void setStarterliste(String starterliste) {
		this.starterliste = starterliste;
	}

	public int getStarterlistenID() {
		return starterlistenID;
	}

	public void setStarterlistenID(int starterlistenID) {
		this.starterlistenID = starterlistenID;
	}

	public int getStartNr() {
		return startNr;
	}

	public void setStartNr(int startNr) {
		this.startNr = startNr;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public int getSportpassID() {
		return sportpassID;
	}

	public void setSportpassID(int sportpassID) {
		this.sportpassID = sportpassID;
	}

	public short getStandNr() {
		return standNr;
	}

	public void setStandNr(short standNr) {
		this.standNr = standNr;
	}

	public String getDisziplin() {
		return disziplin;
	}

	public void setDisziplin(String disziplin) {
		this.disziplin = disziplin;
	}

	public int getDisziplinID() {
		return disziplinID;
	}

	public void setDisziplinID(int disziplinID) {
		this.disziplinID = disziplinID;
	}

	public String getKlasse() {
		return klasse;
	}

	public void setKlasse(String klasse) {
		this.klasse = klasse;
	}

	public int getKlassenID() {
		return klassenID;
	}

	public void setKlassenID(int klassenID) {
		this.klassenID = klassenID;
	}

	public String getVerein() {
		return verein;
	}

	public void setVerein(String verein) {
		this.verein = verein;
	}

	public int getVereinsID() {
		return vereinsID;
	}

	public void setVereinsID(int vereinsID) {
		this.vereinsID = vereinsID;
	}

	public String getMannschaft() {
		return mannschaft;
	}

	public void setMannschaft(String mannschaft) {
		this.mannschaft = mannschaft;
	}

	public int getMannschaftsID() {
		return mannschaftsID;
	}

	public void setMannschaftsID(int mannschaftsID) {
		this.mannschaftsID = mannschaftsID;
	}

	public String getRangliste() {
		return rangliste;
	}

	public void setRangliste(String rangliste) {
		this.rangliste = rangliste;
	}

	public int getRanglistenID() {
		return ranglistenID;
	}

	public void setRanglistenID(int ranglistenID) {
		this.ranglistenID = ranglistenID;
	}

	public short getTrefferzahl() {
		return trefferzahl;
	}

	public void setTrefferzahl(short trefferzahl) {
		this.trefferzahl = trefferzahl;
	}

	public int getTotalRing() {
		return totalRing;
	}

	public void setTotalRing(int totalRing) {
		this.totalRing = totalRing;
	}

	public int getTotalRing01() {
		return totalRing01;
	}

	public void setTotalRing01(int totalRing01) {
		this.totalRing01 = totalRing01;
	}

	public int getBesterTeiler01() {
		return besterTeiler01;
	}

	public void setBesterTeiler01(int besterTeiler01) {
		this.besterTeiler01 = besterTeiler01;
	}

	public Timestamp getZeitstempel() {
		return zeitstempel;
	}

	public void setZeitstempel(Timestamp zeitstempel) {
		this.zeitstempel = zeitstempel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Set<Treffer> getTreffer() {
		return treffer;
	}

	public void setTreffer(Set<Treffer> treffer) {
		this.treffer = treffer;
	}

	public Set<Serien> getSerien() {
		return serien;
	}

	public void setSerien(Set<Serien> serien) {
		this.serien = serien;
	}

	@Override
	public int compareTo(Scheiben o) {
		return this.getZeitstempel().compareTo(o.getZeitstempel());
	}
	
	

}