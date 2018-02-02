package org.rothmayer.UltiShot.DB.SMBD;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Starterlisten
 *
 */
@Entity
@Table(name = "Starterlisten")
public class Starterlisten implements Serializable {


	public Starterlisten() {
		
	}

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ListenID", columnDefinition = "INT(10)")
	private int listenID;
	
	@OneToMany(targetEntity = Starterliste.class, mappedBy = "starterlisten")
	private Set<Starterliste> starterListe;
	
	@Column(name = "ListenName", columnDefinition = "VARCHAR(12)")
	private String listenName;
	
	@Column(name = "ListenTyp", columnDefinition = "ENUM(13)")
	private String listenTyp;

	@Column(name = "Untertitel", columnDefinition = "VARCHAR(12)")
	private String untertitel;
	
	@Column(name = "DatumStart", columnDefinition = "DATE")
	private Date datumStart;
	
	@Column(name = "DatumEnde", columnDefinition = "DATE")
	private Date datumEnde;

	@Column(name = "Ort", columnDefinition = "VARCHAR(12)")
	private String ort;
	
	@Column(name = "StandbereichStart", columnDefinition = "SMALLINT(5)")
	private int standbereichStart;
	
	@Column(name = "StandbereichEnde", columnDefinition = "SMALLINT(5)")
	private int standbereichEnde;
	
	@Column(name = "StarterlistenFlags", columnDefinition = "INT(10)")
	private int starterlistenFlags;

	@Column(name = "StartInfoText", columnDefinition = "VARCHAR(12)")
	private String startInfoText;

	@Column(name = "ResultInfoText", columnDefinition = "VARCHAR(12)")
	private String resultInfoText;
	
	@Column(name = "VeranstaltungsLogo", columnDefinition = "MEDIUMBLOB(16777215)")
	private Blob veranstaltungsLogo;

	public int getListenID() {
		return listenID;
	}

	public void setListenID(int listenID) {
		this.listenID = listenID;
	}

	public Set<Starterliste> getStarterListe() {
		return starterListe;
	}

	public void setStarterListe(Set<Starterliste> starterListe) {
		this.starterListe = starterListe;
	}

	public String getListenName() {
		return listenName;
	}

	public void setListenName(String listenName) {
		this.listenName = listenName;
	}

	public String getListenTyp() {
		return listenTyp;
	}

	public void setListenTyp(String listenTyp) {
		this.listenTyp = listenTyp;
	}

	public String getUntertitle() {
		return untertitel;
	}

	public void setUntertitle(String untertitle) {
		this.untertitel = untertitle;
	}

	public Date getDatumStart() {
		return datumStart;
	}

	public void setDatumStart(Date datumStart) {
		this.datumStart = datumStart;
	}

	public Date getDatumEnde() {
		return datumEnde;
	}

	public void setDatumEnde(Date datumEnde) {
		this.datumEnde = datumEnde;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public int getStandbereichStart() {
		return standbereichStart;
	}

	public void setStandbereichStart(int standbereichStart) {
		this.standbereichStart = standbereichStart;
	}

	public int getStandbereichEnde() {
		return standbereichEnde;
	}

	public void setStandbereichEnde(int standbereichEnde) {
		this.standbereichEnde = standbereichEnde;
	}

	public int getStarterlistenFlags() {
		return starterlistenFlags;
	}

	public void setStarterlistenFlags(int starterlistenFlags) {
		this.starterlistenFlags = starterlistenFlags;
	}

	public String getStartInfoText() {
		return startInfoText;
	}

	public void setStartInfoText(String startInfoText) {
		this.startInfoText = startInfoText;
	}

	public String getResultInfoText() {
		return resultInfoText;
	}

	public void setResultInfoText(String resultInfoText) {
		this.resultInfoText = resultInfoText;
	}

	public Blob getVeranstaltungsLogo() {
		return veranstaltungsLogo;
	}

	public void setVeranstaltungsLogo(Blob veranstaltungsLogo) {
		this.veranstaltungsLogo = veranstaltungsLogo;
	}
	
	@Override
	public String toString(){
		return this.getListenName();
	}
}
