package model;


import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class ItemAluguel implements Serializable{
	@Id 
	@GeneratedValue(strategy=GenerationType.SEQUENCE) 
	private int id;
	private Genero genero;
	private Midia midia;
	private Calendar dataDaAdicao;
	@ManyToOne
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Genero getGenero() {
		return genero;
	}
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	
	public Midia getMidia() {
		return midia;
	}
	public void setMidia(Midia midia) {
		this.midia = midia;
	}
	
	public Calendar getDataDaAdicao() {
		return dataDaAdicao;
	}
	public void setDataDaAdicao(Calendar dataDaAdicao) {
		this.dataDaAdicao = dataDaAdicao;
	}
	
	
}
