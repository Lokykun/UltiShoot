package org.rothmayer.UltiShot.Util;

public class Scheibe {
	
	String disziplin;
	int disziplinID;
	
	public Scheibe(String disziplin, int disziplinID) {
		super();
		this.disziplin = disziplin;
		this.disziplinID = disziplinID;
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
	
	@Override
	public String toString(){
		return this.disziplin;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Scheibe){
			Scheibe s = (Scheibe) o;
			if(s.getDisziplinID() == this.disziplinID){
				return true;
			}
		}
		return false;
	}

}
