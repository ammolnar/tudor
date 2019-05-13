package hu.elte.szgy.tudor.data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="eset")
public class Eset implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int esid;
	
	@ManyToOne
	@JoinColumn(name="fk_azon", nullable = false)
	@JsonIgnore
	private Ugyfel ugyfel;
	
	@Column(name = "fk_azon", insertable = false, updatable = false)
	private int ugyfelAzon;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eset")
	@JsonIgnore
	private Set<Szolgaltatas> szolgaltatasok = new HashSet<Szolgaltatas>(0);
	
	public enum Statusz{ NYITOTT, LEZART }
	
	public enum ZarStat{ MEGVALASZOLVA, TOROLVE }
	
	@Enumerated(EnumType.ORDINAL)
	private Statusz statusz;
	
	private String kerdes;
	private String valasz;
	private int valaszstat;
	private Date nyitdate;
	private Date zardate;
	
	@Enumerated(EnumType.ORDINAL)
	private ZarStat zarStat;
	
	public int getEsid() { return this.esid; }
	public void setEsid(int esid) { this.esid = esid; }
	
	public Ugyfel getUgyfel() { return this.ugyfel; }
	public void setUgyfel(Ugyfel ugyfel) { this.ugyfel = ugyfel; }
	
	public Statusz getStatusz() { return this.getStatusz(); }
	public void setStatusz(Statusz statusz) { this.statusz = statusz; }
	
	public String getKerdes() { return this.kerdes; }
	public void setKerdes(String kerdes) { this.kerdes = kerdes; }
	
	public String getValasz() { return this.valasz; }
	public void setValasz(String valasz) { this.valasz = valasz; }
	
	public int getValaszstat() { return this.valaszstat; }
	public void setValaszstat(int valaszstat) { this.valaszstat = valaszstat; }
	
	public Date getNyitdate() { return this.nyitdate; }
    public void setNyitdate(Date nyitdate) { this.nyitdate = nyitdate; }
    
    public Date getZardate() { return this.zardate; }
    public void setZardate(Date zardate) { this.zardate = zardate; }
    
    public ZarStat getZarStat() { return this.zarStat; }
    public void setZarStat(ZarStat zarStat) { this.zarStat = zarStat; }
    
    public Set<Szolgaltatas> getSzolgaltatasok() { return this.szolgaltatasok; }
    public void setKezelesek(Set<Szolgaltatas> szolgaltatasok) { this.szolgaltatasok = szolgaltatasok; }

    public int getUgyfelAzon()	{ return ugyfelAzon; }
	public void setUgyfelAzon(int ugyfelAzon) { this.ugyfelAzon = ugyfelAzon; }
	
	public Eset() { }
    public Eset(Integer nid) { esid = nid; }

}
