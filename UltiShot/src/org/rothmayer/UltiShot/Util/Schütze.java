package org.rothmayer.UltiShot.Util;

import java.sql.Date;

public class Sch�tze implements Comparable<Sch�tze>{
	
	int sportpass;
	int starternummer;
	String vorname;
	String nachname;
	int gesamt;
	Zuordnung zuordnung;
	Date gb;
	
	public Sch�tze(int sportpass, String vorname, String nachname, int ring, Zuordnung zuordnung, int starternummer, Date gb) {
		this.sportpass = sportpass;
		this.vorname = vorname;
		this.nachname = nachname;
		this.zuordnung = zuordnung;
		this.gesamt = ring;
		this.starternummer = starternummer;
		this.gb = gb;
	}

	@Override
	public int compareTo(Sch�tze sch�tze){
		return Integer.compare(sch�tze.getGesamt(),gesamt);
	}

	public int getSportpass() {
		return sportpass;
	}

	public String getVorname() {
		return vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public Zuordnung getZuordnung() {
		return zuordnung;
	}

	public int getGesamt() {
		return gesamt;
	}

	public int getStarternummer() {
		return starternummer;
	}

	public Date getGb() {
		return gb;
	}
}
