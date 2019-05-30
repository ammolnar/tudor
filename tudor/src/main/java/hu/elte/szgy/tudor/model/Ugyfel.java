package hu.elte.szgy.tudor.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="ugyfel")
//@NamedQuery(name="Ugyfel.hasPendingSzolgaltatasBy", query="SELECT count(*) from szolgaltatas sz WHERE sz.kerdes.ugyfel.azon=:azon") //Errors in named queries: szolgaltatas is not mapped 
public class Ugyfel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String azon;
	private String nev;
	private Date szuldatum;
	private String foglalkozas;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ugyfel")
	@JsonIgnore
	private Set<Kerdes> kerdesek = new HashSet<Kerdes>(0);
	
	public String getAzon() { return azon; }
	public void setAzon(String azon) { this.azon = azon; }
	
	public String getNev() { return nev; }
	public void setNev(String nev) { this.nev = nev; }
	
	public Date getSzuldatum() { return szuldatum; }
	public void setSzuldatum(Date szuldatum) { this.szuldatum = szuldatum; }
	
	public String getFoglalkozas() { return foglalkozas; }
	public void setFoglalkozas(String foglalkozas) { this.foglalkozas = foglalkozas; }
	
	public Set<Kerdes> getKerdesek() {
		return this.getKerdesek();
	}
	
	public void setKerdesek(Set<Kerdes> kerdesek) {
		this.kerdesek = kerdesek;
	}
	
	public Ugyfel() {}
	public Ugyfel(String azon) { this.azon = azon; }

}
