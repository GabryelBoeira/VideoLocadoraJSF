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
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "cliente")
public class Cliente  implements Serializable{

	private static final long serialVersionUID = -5922888212335973023L;
	
	public Cliente() {
		aluguels = new ArrayList<Aluguel>();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	
	private String nome;
	
	private String email;
	
	private String cep;
	
	@Column(name = "data_nacismento")
	private Date dataNacismento;
	
	@OneToMany(targetEntity = Aluguel.class, mappedBy = "id", fetch = FetchType.EAGER)
	private List<Aluguel> aluguels;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getCep() {
		return cep;
	}
	
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public Date getDataNacismento() {
		return dataNacismento;
	}
	
	public void setDataNacismento(Date dataNacismento) {
		this.dataNacismento = dataNacismento;
	}

	public List<Aluguel> getAluguels() {
		return aluguels;
	}

	public void setAluguels(List<Aluguel> aluguels) {
		this.aluguels = aluguels;
	}
}
