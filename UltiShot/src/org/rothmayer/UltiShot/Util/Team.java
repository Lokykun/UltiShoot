package org.rothmayer.UltiShot.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Team implements Comparable<Team>{

	int id;
	int mannschaftgr;
	int anzahlProfi;
	String name;
	List<Schütze> schützenProfi = new ArrayList<>();
	List<Schütze> schützenHobby = new ArrayList<>();
	List<Schütze> schützenAnzeige = new ArrayList<>();
	int sepPlace = 0;
	int ergebniss = 0;
	
	public Team(int id, int mannschaftgr, int anzahlProfi, String name) {
		super();
		this.id = id;
		this.mannschaftgr = mannschaftgr;
		this.anzahlProfi = anzahlProfi;
		this.name = name;
	}
	
	public void addSchütze(Schütze schütze){
		if(schütze.getZuordnung() == Zuordnung.HOBBY){
			schützenHobby.add(schütze);
			Collections.sort(schützenHobby);
		}else{
			schützenProfi.add(schütze);
			Collections.sort(schützenProfi);
		}
		sort();
	}
	
	public int getErgebniss(){
		
		return ergebniss;
	}
	
	public void sort(){
		
		List<Schütze> tempprofi = new ArrayList<>();
		tempprofi.addAll(schützenProfi);
		List<Schütze> temphobby = new ArrayList<>();
		temphobby.addAll(schützenHobby);
		List<Schütze> tempprofiW = new ArrayList<>();
		List<Schütze> temphobbyW = new ArrayList<>();
		List<Schütze> tempwertung = new ArrayList<>();
		List<Schütze> tempnon = new ArrayList<>();
		sepPlace = 0;
		
		
		//beste Profischützen selectieren
		for(int i = 0; (i < tempprofi.size() && i < anzahlProfi);i++){
			tempprofiW.add(tempprofi.get(i));
		}
		
		//beste Profischützen aus alter liste entfenren
		for(Schütze schütze : tempprofiW){
			tempprofi.remove(schütze);
		}
		
		//beste Hobbyschützen selectieren
		for(int i = 0; (i < temphobby.size() && i < mannschaftgr);i++){
			temphobbyW.add(temphobby.get(i));
		}
				
		//beste Hobbyschützen aus alter liste entfenren
		for(Schütze schütze : temphobbyW){
			temphobby.remove(schütze);
		}
		
		//Gewertete Schützen zusammen führen sortieren und überfluss entfernen
		
		//System.out.println(tempprofiW.size() + "\t" + temphobbyW.size());
		tempwertung.addAll(temphobbyW);
		tempwertung.addAll(tempprofiW);
		Collections.sort(tempwertung);
		while (tempwertung.size() > mannschaftgr) {
			tempnon.add(tempwertung.get(tempwertung.size()-1));
			tempwertung.remove(tempwertung.size()-1);
		}
		
		ergebniss = 0;
		
		for(Schütze schütze : tempwertung){
			ergebniss += schütze.getGesamt();
		}
		
		//Ungewertete verarbeiten
		tempnon.addAll(temphobby);
		tempnon.addAll(tempprofi);
		
		Collections.sort(tempnon);
		
		//Alles zusammen führen
		
		schützenAnzeige = new ArrayList<>();
		schützenAnzeige.addAll(tempwertung);
		sepPlace = tempwertung.size();
		schützenAnzeige.addAll(tempnon);
		
		
	}
	
	public void sort2(){
		
		int profis = 0;
		int gr = mannschaftgr;
		
		List<Schütze> tempList = new ArrayList<>();
		
		tempList.addAll(schützenProfi);
		tempList.addAll(schützenHobby);
		
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
		
		schützenAnzeige = tempList;
		
		ergebniss = 0;
		
		for(int i = 0; (i < schützenAnzeige.size())&& (i < mannschaftgr);i++){
			ergebniss = ergebniss + schützenAnzeige.get(i).getGesamt();
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

	public List<Schütze> getSchützenAnzeige() {
		return schützenAnzeige;
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
