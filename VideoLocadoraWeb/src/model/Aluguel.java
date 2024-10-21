package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Aluguel implements Serializable {

	private static final long serialVersionUID = -8581348107550054975L;

	public Aluguel(){
		itens=new ArrayList<ItemAluguel>();
		cliente = new Cliente();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id; 

	@Column(name = "data_aluguel")
	private Date dataDoAluguel;
	
	@Column(name = "data_devolucao")
	private Date dataDaDevolucao;
	
	@Column(name = "valor_aluguel")
	private double valorDoAluguel;
	
	@ManyToOne
	private ArrayList<ItemAluguel> itens;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
	private Cliente cliente;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Date getDataDoAluguel() {
		return dataDoAluguel;
	}
	
	public void setDataDoAluguel(Date dataDoAluguel) {
		this.dataDoAluguel = dataDoAluguel;
	}
	
	public Date getDataDaDevolucao() {
		return dataDaDevolucao;
	}
	
	public void setDataDaDevolucao(Date dataDaDevolucao) {
		this.dataDaDevolucao = dataDaDevolucao;
	}
	
	public double getValorDoAluguel() {
		return valorDoAluguel;
	}
	
	public void setValorDoAluguel(double valorDoAluguel) {
		this.valorDoAluguel = valorDoAluguel;
	}

	public ArrayList<ItemAluguel> getItens() {
		return itens;
	}

	public void setItens(ArrayList<ItemAluguel> itens) {
		this.itens = itens;
	}
	
}
