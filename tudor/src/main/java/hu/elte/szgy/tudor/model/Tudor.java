package hu.elte.szgy.tudor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tudor")
//@PrimaryKeyJoinColumn(name="temaId")
public class Tudor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int tudorId;
	private String nev;
	private Date szuldatum;
	private String foglalkozas;
	
	public int getAzon() { return tudorId; }
	public void setAzon(int tudorId) { this.tudorId = tudorId; }
	
	public String getNev() { return nev; }
	public void setNev(String nev) { this.nev = nev; }
	
	public Date getSzuldatum() { return szuldatum; }
	public void setSzuldatum(Date szuldatum) { this.szuldatum = szuldatum; }
	
	public String getFoglalkozas() { return foglalkozas; }
	public void setFoglalkozas(String foglalkozas) { this.foglalkozas = foglalkozas; }
	
	@ManyToOne
	@JoinColumn(nullable = true)
	@JsonIgnore
	private Tema tema;
	
	@Column(name = "tema_nev", insertable = false, updatable = false)
	private String temaId;
	
	public Tema getTema() { return tema; }
	public void setTema(Tema tema) { this.tema = tema; }
	
	public String getTemaId() { return temaId; }
	public void setTemaId(String temaId) { this.temaId = temaId; }
	
}
