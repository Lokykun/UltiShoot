package org.rothmayer.UltiShot.Util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.rothmayer.UltiShot.DB.Local.Zuweisung;
import org.rothmayer.UltiShot.DB.SMBD.Mannschaft;
import org.rothmayer.UltiShot.DB.SMBD.Schuetze;
import org.rothmayer.UltiShot.DB.SSMBD2.Scheiben;
import org.rothmayer.UltiShot.GUI.UltiShotAuswerung;

public class Auswerter {

	int startListenID;
	int manschaftsGr��e;
	int profisch�tzen;
	String listname = null;
	List<Team> teams = new ArrayList<>();
	
	
	public void doAuswertung(int listenID, List<Integer> scheibenIDs, int gr, int profi){
		teams = new ArrayList<>();
		List<Scheiben> tempList = UltiShotAuswerung.ssmdb2.find(Scheiben.class).where().eq("starterlistenID", listenID).findList();
		//System.out.println(tempList.size());
		List<Scheiben> scheibenList = new ArrayList<>();
		
		for(Scheiben s : tempList){
			if(scheibenIDs.contains(s.getDisziplinID())){
				scheibenList.add(s);
			}
		}
		
		Collections.sort(scheibenList);
		
		
		List<Mannschaft> mannschaften = UltiShotAuswerung.smdb.find(Mannschaft.class).findList();
		List<Zuweisung> zuweisungen = UltiShotAuswerung.localDB.find(Zuweisung.class).where().eq("starterlistenid", listenID).findList();
		Collections.sort(zuweisungen); 
		
		
		for(Mannschaft manns : mannschaften){
			//Ausnahme f�r Sch�tzenjugend
			if(manns.getMannschaftsName().equalsIgnoreCase("Sch�tzenjugend")){
				teams.add(new Team(manns.getMannschaftsID(), gr, gr, manns.getMannschaftsName()));
			}else{

				teams.add(new Team(manns.getMannschaftsID(), gr, profi, manns.getMannschaftsName()));
			}
		}
		
		
		HashMap<Integer,Integer> maxScheibe = new HashMap<>();
		HashMap<Integer,Integer> currentScheiben = new HashMap<>();
		
		for(Scheiben scheibe : scheibenList){
			if(listname == null && scheibe.getStarterliste() != null){
				listname = scheibe.getStarterliste();
			}
			if(!maxScheibe.containsKey(scheibe.getSportpassID())){
				maxScheibe.put(scheibe.getSportpassID(), 1);
				currentScheiben.put(scheibe.getSportpassID(), 0);
			}else{
				int count = maxScheibe.get(scheibe.getSportpassID());
				count++;
				maxScheibe.put(scheibe.getSportpassID(), count);
			}
		}
		
		
		
		
		Scheiben akScheibe = null;
		for(Zuweisung zuweisung : zuweisungen){
			
			//System.out.println(zuweisung.getZuweisungPK().getSportpassID() + "\t" + zuweisung.getZuweisungPK().getMannschaftsID() + "\t" + zuweisung.getReihenfolge());
			
			//Teams erstellen
			Team team = null;
			for(Team t : teams){
				if(t.getId() == zuweisung.getZuweisungPK().getMannschaftsID()){
					team = t;
					break;
				}
			}
			if(team == null){
				continue;
			}
			
			
			//Kombination
			if(!zuweisung.isKombiniert() || zuweisung.getReihenfolge() == 0){
				
				for(Scheiben scheibe : scheibenList){
					if(scheibe.getSportpassID() == zuweisung.getZuweisungPK().getSportpassID()){
						akScheibe = scheibe;
						break;
					}
				}
				scheibenList.remove(akScheibe);
				
			}
			Zuordnung zu = Zuordnung.PROFI;
			if(akScheibe == null){
				zu = Zuordnung.HOBBY;
			}else if(akScheibe.getVereinsID()==0){
				zu = Zuordnung.HOBBY;
			}
			
			/*if(!currentScheiben.containsKey(zuweisung.getZuweisungPK().getSportpassID())){
				continue;
			}*/
			
			if(!zuweisung.isKombiniert()){
				//System.out.println("Sportpass: " + zuweisung.getZuweisungPK().getSportpassID());
				//System.out.println(currentScheiben.containsKey(zuweisung.getZuweisungPK().getSportpassID()));
				if(!currentScheiben.containsKey(zuweisung.getZuweisungPK().getSportpassID())){
					continue;
				}
				int count = currentScheiben.get(zuweisung.getZuweisungPK().getSportpassID());
				count++;
				currentScheiben.put(zuweisung.getZuweisungPK().getSportpassID(), count);
			}
			
			//System.out.println("c " + currentScheiben.containsKey(zuweisung.getZuweisungPK().getSportpassID()));
			//System.out.println("max " + maxScheibe.containsKey(zuweisung.getZuweisungPK().getSportpassID()));
			if(!currentScheiben.containsKey(zuweisung.getZuweisungPK().getSportpassID())){
				continue;
			}
			
			if(currentScheiben.get(zuweisung.getZuweisungPK().getSportpassID()) > maxScheibe.get(zuweisung.getZuweisungPK().getSportpassID())){
				continue;
			}
			
			
			
			//System.out.println(currentScheiben.get(zuweisung.getZuweisungPK().getSportpassID()) + "\t" + maxScheibe.get(zuweisung.getZuweisungPK().getSportpassID()));
			
			
			//System.out.println(team.getName() + "\t" + akScheibe.getVorname() +" " + akScheibe.getNachname() + "\t" + akScheibe.getTotalRing());
			Date date = new Date(System.currentTimeMillis());
			try {
				date = ((Schuetze) UltiShotAuswerung.smdb.find(Schuetze.class).where().eq("SportpassID", akScheibe.getSportpassID()).findUnique()).getGeburtsdatum();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			team.addSch�tze(new Sch�tze(akScheibe.getSportpassID(), akScheibe.getVorname(), akScheibe.getNachname(), akScheibe.getTotalRing(), zu, akScheibe.getStartNr(), date));
			
			
			
		}
		int id = -1;
		int i = 0;
		for (Team team : teams) {
			if(team.name.equalsIgnoreCase("Ungewertet")){
				id = i;
				break;
			}
			i++;
			
		}
		if(id != -1){
			teams.remove(id);
		}
		
		Collections.sort(teams);
		//System.out.println(System.getProperty( "user.home" ));
		
		if(listname == null){
			listname = "";
		}
		
		new HtmlWorker(teams, listname, gr);
		//DEBUG
		/*
		for(Team team : teams){
			if(team.getErgebniss() == 0){
				continue;
			}
			System.out.println(team.getName() +"\t"+ team.getErgebniss());
			
			for(Sch�tze sch�tze : team.getSch�tzenAnzeige()){
				System.out.println("\t" + sch�tze.getNachname() + " " + sch�tze.getVorname() + "\t" + sch�tze.getGesamt() + "\t" + sch�tze.getZuordnung());
			}
		}*/
	}
	
	
}
