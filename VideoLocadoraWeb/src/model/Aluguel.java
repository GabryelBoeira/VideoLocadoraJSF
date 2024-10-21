package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "aluguel")
public class Aluguel implements Serializable {

	private static final long serialVersionUID = -8581348107550054975L;

	public Aluguel(){
		itens = new ArrayList<ItemAluguel>();
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
		
	@OneToMany(targetEntity = ItemAluguel.class, mappedBy = "id", fetch = FetchType.EAGER)
	private List<ItemAluguel> itens;

	@ManyToOne
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

	public List<ItemAluguel> getItens() {
		return itens;
	}

	public void setItens(List<ItemAluguel> itens) {
		this.itens = itens;
	}
	
}
