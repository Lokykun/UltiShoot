package org.rothmayer.UltiShot.DB.SMBD;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Schuetze
 *
 */
@Entity
@Table(name = "Schuetze")
public class Schuetze implements Serializable, Comparable<Schuetze>{


	public Schuetze() {
		
	}

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SportpassID", columnDefinition = "INT(11)")
	private int sportpassID;
	
	@OneToMany(targetEntity = Starterliste.class, mappedBy = "schuetze")
	private Set<Starterliste> starterListe;
	
	@Column(name = "VereinsID", columnDefinition = "INT(11)")
	private int vereinsID;
	
	@Column(name = "Nachname", columnDefinition = "VARCHAR(36)")
	private String nachname;
	
	@Column(name = "Vorname", columnDefinition = "VARCHAR(36)")
	private String vorname;
	
	@Column(name = "Geschlecht", columnDefinition = "ENUM(9)")
	private String geschlecht;
	
	@Column(name = "Geburtsdatum", columnDefinition = "DATE")
	private java.sql.Date geburtsdatum;
	
	@Column(name = "LandesKuerzel", columnDefinition = "VARCHAR(3)")
	private String landesKuerzel;
	
	@Column(name = "Strasse", columnDefinition = "VARCHAR(72)")
	private String strasse;
	
	@Column(name = "PLZ", columnDefinition = "INT(4)")
	private int plz;
	
	@Column(name = "Ort", columnDefinition = "VARCHAR(36)")
	private String ort;
	
	@Column(name = "Telefonnummer", columnDefinition = "VARCHAR(36)")
	private String telefonnummer;
	
	@Column(name = "EMail", columnDefinition = "VARCHAR(36)")
	private String email;
	
	@Column(name = "SchuetzenFlags", columnDefinition = "INT(11)")
	private int schuetzenFlags;
	
	@Column(name = "SchuetzenFoto", columnDefinition = "MEDIUMBLOB(16777215)")
	@Lob
	private byte[] schuetzenFoto;

	public int getSportpassID() {
		return sportpassID;
	}

	public void setSportpassID(int sportpassID) {
		this.sportpassID = sportpassID;
	}

	public int getVereinsID() {
		return vereinsID;
	}

	public void setVereinsID(int vereinsID) {
		this.vereinsID = vereinsID;
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

	public String isGeschlecht() {
		return geschlecht;
	}

	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}

	public java.sql.Date  getGeburtsdatum() {
		return geburtsdatum;
	}

	public void setGeburtsdatum(java.sql.Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public String getLandesKuerzel() {
		return landesKuerzel;
	}

	public void setLandesKuerzel(String landesKuerzel) {
		this.landesKuerzel = landesKuerzel;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getTelefonnummer() {
		return telefonnummer;
	}

	public void setTelefonnummer(String telefonnummer) {
		this.telefonnummer = telefonnummer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSchuetzenFlags() {
		return schuetzenFlags;
	}

	public void setSchuetzenFlags(int schuetzenFlags) {
		this.schuetzenFlags = schuetzenFlags;
	}

	public byte[] getSchuetzenFoto() {
		return schuetzenFoto;
	}

	public void setSchuetzenFoto(byte[] schuetzenFoto) {
		this.schuetzenFoto = schuetzenFoto;
	}

	public Set<Starterliste> getStarterListe() {
		return starterListe;
	}

	public void setStarterListe(Set<Starterliste> starterListe) {
		this.starterListe = starterListe;
	}

	public String getGeschlecht() {
		return geschlecht;
	}
	
	@Override
	public String toString(){
		return this.getNachname() + " " + vorname + " " + geburtsdatum;
	}

	@Override
	public int compareTo(Schuetze o) {
		return this.toString().compareTo(o.toString());
	}
	   
}
