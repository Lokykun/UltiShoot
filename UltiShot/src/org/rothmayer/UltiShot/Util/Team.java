package org.rothmayer.UltiShot.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Team implements Comparable<Team>{

	int id;
	int mannschaftgr;
	int anzahlProfi;
	String name;
	List<Sch�tze> sch�tzenProfi = new ArrayList<>();
	List<Sch�tze> sch�tzenHobby = new ArrayList<>();
	List<Sch�tze> sch�tzenAnzeige = new ArrayList<>();
	int sepPlace = 0;
	int ergebniss = 0;
	
	public Team(int id, int mannschaftgr, int anzahlProfi, String name) {
		super();
		this.id = id;
		this.mannschaftgr = mannschaftgr;
		this.anzahlProfi = anzahlProfi;
		this.name = name;
	}
	
	public void addSch�tze(Sch�tze sch�tze){
		if(sch�tze.getZuordnung() == Zuordnung.HOBBY){
			sch�tzenHobby.add(sch�tze);
			Collections.sort(sch�tzenHobby);
		}else{
			sch�tzenProfi.add(sch�tze);
			Collections.sort(sch�tzenProfi);
		}
		sort();
	}
	
	public int getErgebniss(){
		
		return ergebniss;
	}
	
	public void sort(){
		
		List<Sch�tze> tempprofi = new ArrayList<>();
		tempprofi.addAll(sch�tzenProfi);
		List<Sch�tze> temphobby = new ArrayList<>();
		temphobby.addAll(sch�tzenHobby);
		List<Sch�tze> tempprofiW = new ArrayList<>();
		List<Sch�tze> temphobbyW = new ArrayList<>();
		List<Sch�tze> tempwertung = new ArrayList<>();
		List<Sch�tze> tempnon = new ArrayList<>();
		sepPlace = 0;
		
		
		//beste Profisch�tzen selectieren
		for(int i = 0; (i < tempprofi.size() && i < anzahlProfi);i++){
			tempprofiW.add(tempprofi.get(i));
		}
		
		//beste Profisch�tzen aus alter liste entfenren
		for(Sch�tze sch�tze : tempprofiW){
			tempprofi.remove(sch�tze);
		}
		
		//beste Hobbysch�tzen selectieren
		for(int i = 0; (i < temphobby.size() && i < mannschaftgr);i++){
			temphobbyW.add(temphobby.get(i));
		}
				
		//beste Hobbysch�tzen aus alter liste entfenren
		for(Sch�tze sch�tze : temphobbyW){
			temphobby.remove(sch�tze);
		}
		
		//Gewertete Sch�tzen zusammen f�hren sortieren und �berfluss entfernen
		
		//System.out.println(tempprofiW.size() + "\t" + temphobbyW.size());
		tempwertung.addAll(temphobbyW);
		tempwertung.addAll(tempprofiW);
		Collections.sort(tempwertung);
		while (tempwertung.size() > mannschaftgr) {
			tempnon.add(tempwertung.get(tempwertung.size()-1));
			tempwertung.remove(tempwertung.size()-1);
		}
		
		ergebniss = 0;
		
		for(Sch�tze sch�tze : tempwertung){
			ergebniss += sch�tze.getGesamt();
		}
		
		//Ungewertete verarbeiten
		tempnon.addAll(temphobby);
		tempnon.addAll(tempprofi);
		
		Collections.sort(tempnon);
		
		//Alles zusammen f�hren
		
		sch�tzenAnzeige = new ArrayList<>();
		sch�tzenAnzeige.addAll(tempwertung);
		sepPlace = tempwertung.size();
		sch�tzenAnzeige.addAll(tempnon);
		
		
	}
	
	public void sort2(){
		
		int profis = 0;
		int gr = mannschaftgr;
		
		List<Sch�tze> tempList = new ArrayList<>();
		
		tempList.addAll(sch�tzenProfi);
		tempList.addAll(sch�tzenHobby);
		
		if(tempList.size() < mannschaftgr){
			gr = tempList.size();
		}
		
		Collections.sort(tempList);
		
		for(int i = 0; (i < tempList.size())&& (i < gr);i++){
			if(tempList.get(i).getZuordnung() == Zuordnung.PROFI){
				if(profis <= anzahlProfi){
					profis++;
				}else{
					Collections.swap(tempList, i, gr-1);
					i--;
				}
			}
		}
		
		sch�tzenAnzeige = tempList;
		
		ergebniss = 0;
		
		for(int i = 0; (i < sch�tzenAnzeige.size())&& (i < mannschaftgr);i++){
			ergebniss = ergebniss + sch�tzenAnzeige.get(i).getGesamt();
		}
	}
	
	@Override
	  public int compareTo(Team m){
	
		return Integer.compare(m.getErgebniss(), this.getErgebniss());
	}

	public int getId() {
		return id;
	}

	public int getMannschaftgr() {
		return mannschaftgr;
	}

	public int getAnzahlProfi() {
		return anzahlProfi;
	}

	public String getName() {
		return name;
	}

	public List<Sch�tze> getSch�tzenAnzeige() {
		return sch�tzenAnzeige;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Team)){
			return false;
		}
		
		Team m = (Team) o;
		
		if(this.getId() == m.getId()){
			return true;
		}
		return false;
	}

	public int getSepPlace() {
		return sepPlace;
	}
}
