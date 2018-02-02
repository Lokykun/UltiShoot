package org.rothmayer.UltiShot.DB.SSMBD2;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Treffer
 *
 */
@Entity
public class Treffer implements Serializable {

	
	public Treffer() {
		
	}

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TrefferPK trefferPK;
	
	@ManyToOne
	@JoinColumn(name = "ScheibenID", insertable = false, updatable = false, referencedColumnName="ScheibenID")
	private Scheiben scheibe;
	
	@Column(name = "x", columnDefinition = "INT(11)")
	private int x;
	
	@Column(name = "y", columnDefinition = "INT(11)")
	private int y;
	
	@Column(name = "Innenzehner", columnDefinition = "TINYINT(1)")
	private short innenzehner;
	
	@Column(name = "Ring", columnDefinition = "INT(11)")
	private int ring;
	
	@Column(name = "Ring01", columnDefinition = "INT(11)")
	private int ring01;
	
	@Column(name = "Teiler01", columnDefinition = "INT(11)")
	private int teiler01;
	
	@Column(name = "Zeitstempel", columnDefinition = "INT(11)")
	private Timestamp zeitstempel;
	
	@Column(name = "Millisekunden", columnDefinition = "SMALLINT(6)")
	private short millisekunden;

	public TrefferPK getTrefferPK() {
		return trefferPK;
	}

	public void setTrefferPK(TrefferPK trefferPK) {
		this.trefferPK = trefferPK;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public short getInnenzehner() {
		return innenzehner;
	}

	public void setInnenzehner(short innenzehner) {
		this.innenzehner = innenzehner;
	}

	public int getRing() {
		return ring;
	}

	public void setRing(int ring) {
		this.ring = ring;
	}

	public int getRing01() {
		return ring01;
	}

	public void setRing01(int ring01) {
		this.ring01 = ring01;
	}

	public int getTeiler01() {
		return teiler01;
	}

	public void setTeiler01(int teiler01) {
		this.teiler01 = teiler01;
	}

	public Timestamp getZeitstempel() {
		return zeitstempel;
	}

	public void setZeitstempel(Timestamp zeitstempel) {
		this.zeitstempel = zeitstempel;
	}

	public short getMillisekunden() {
		return millisekunden;
	}

	public void setMillisekunden(short millisekunden) {
		this.millisekunden = millisekunden;
	}

	public Scheiben getScheibe() {
		return scheibe;
	}

	public void setScheibe(Scheiben scheibe) {
		this.scheibe = scheibe;
	}

   
}
