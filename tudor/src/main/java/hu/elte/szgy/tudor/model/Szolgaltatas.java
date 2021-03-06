package hu.elte.szgy.tudor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="szolgaltatas")
public class Szolgaltatas implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int szolgid;
	
	@ManyToOne
	@JoinColumn(name = "fk_kerdesid", nullable = false)
	@JsonIgnore
	private Kerdes kerdes;
	
	@Column(name = "fk_kerdesid", insertable = false, updatable = false)
	private int kerdesId;
	
	public enum Statusz{ NYITOTT, LEZART }
	
	@Enumerated(EnumType.ORDINAL)
	private Statusz statusz;
	
	private String specifikacio;
	private String nyitallapot;
	private String vegallapot;
	private String info;
	private int tajpontok;
	private Date nyitdate;
	private Date vegdate;
	
	public int getSzolgid() { return this.szolgid; }
	public void setSzolgid(int szolgid) { this.szolgid = szolgid; }
	
	public Kerdes getKerdes() { return this.kerdes; }
	public void setKerdes(Kerdes kerdes) { this.kerdes = kerdes; }
	
	public Statusz getStatusz() { return this.statusz; }
	public void setStatusz(Statusz statusz) { this.statusz = statusz; }
	
	public String getSpecifikacio() { return this.specifikacio; }
	public void setSpecifikacio(String specifikacio) { this.specifikacio = specifikacio; }
	
	public String getNyitallapot() { return this.nyitallapot; }
	public void setNyitallapot(String nyitallapot) { this.nyitallapot = nyitallapot; }
	
	public String getVegallapot() { return this.vegallapot; }
	public void setVegallapot(String vegallapot) { this.vegallapot = vegallapot; }
	
	public String getInfo() { return this.info; }
	public void setInfo(String info) { this.info = info; }
	
	public int getTajpontok() { return this.tajpontok; }
	public void setTajpontok(int tajpontok) { this.tajpontok = tajpontok; }
	
	public Date getNyitdate() { return this.nyitdate; }
	public void setNyitdate(Date nyitdate) { this.nyitdate = nyitdate; }
	
	public Date getVegdate() { return this.vegdate; }
	public void setVegdate(Date vegdate) { this.vegdate = vegdate; }
	
	public int getKerdesId() { return kerdesId; }
	public void setKerdesId(int kerdesId) { this.kerdesId = kerdesId; }

}
