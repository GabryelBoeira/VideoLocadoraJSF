package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import model.ItemAluguel;
@Entity
public class Aluguel implements Serializable {

	public Aluguel(){
		itens=new ArrayList<ItemAluguel>();
		cliente = new Cliente();
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id; 
	@Column(name = "itens", columnDefinition = "VARCHAR(1000)")
	private ArrayList<ItemAluguel> itens;
	@Column(name = "cliente", columnDefinition = "VARCHAR(1000)")
	private Cliente cliente;
	private Date dataDoAluguel;
	private Date dataDaDevolucao;
	private double valorDoAluguel;
	@OneToMany

	public ArrayList<ItemAluguel> getItens() {
		return itens;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setItens(ArrayList<ItemAluguel> itens) {
		this.itens = itens;
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
	
	
	
	
	
	
	
	
	
	
}
