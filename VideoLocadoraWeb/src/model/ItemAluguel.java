package model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ItemAluguel implements Serializable{

	private static final long serialVersionUID = -4450962609539925795L;

	@Id 
	@GeneratedValue(strategy=GenerationType.SEQUENCE) 
	private int id;

	@ManyToOne
	private Midia midia;
	
	@Column(name = "data_adicao")
	private Date dataDaAdicao;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Midia getMidia() {
		return midia;
	}

	public void setMidia(Midia midia) {
		this.midia = midia;
	}

	public Date getDataDaAdicao() {
		return dataDaAdicao;
	}

	public void setDataDaAdicao(Date dataDaAdicao) {
		this.dataDaAdicao = dataDaAdicao;
	}


	
}
