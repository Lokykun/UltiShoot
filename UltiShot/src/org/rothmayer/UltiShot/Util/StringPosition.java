package org.rothmayer.UltiShot.Util;

public enum StringPosition {
	
	AFTRER(0), BEVOR(1), SOMEWHERE(2), BYPASS(3);
	
	private int id; // Could be other data type besides int
    private StringPosition(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
	
	

}
