package org.rothmayer.UltiShot.DB.SSMBD2;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Serien
 *
 */
@Entity
public class Serien implements Serializable {


	public Serien() {
		
	}

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private SerienPK serienPK;
	
	@ManyToOne
	@JoinColumn(name = "ScheibenID", insertable = false, updatable = false, referencedColumnName="ScheibenID")
	private Scheiben scheibe;
	
	@Column(name = "Ring", columnDefinition = "INT(11)")
	private int ring;
	
	@Column(name = "Ring01", columnDefinition = "INT(11)")
	private int ring01;

	public SerienPK getSerienPK() {
		return serienPK;
	}

	public void setSerienPK(SerienPK serienPK) {
		this.serienPK = serienPK;
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

	public Scheiben getScheibe() {
		return scheibe;
	}

	public void setScheibe(Scheiben scheibe) {
		this.scheibe = scheibe;
	}

	   
}
