package model;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "midia")
public class Midia implements Serializable {

	private static final long serialVersionUID = 3833368715882820344L;

	public Midia() {
		genero = new Genero();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	
	private String titulo;
	
	@Column(name = "tipo_midia")
	private String tipoMidia;
	
	private String diretor;
	
	private Date  duracao;
	
	@Column(name = "classificacao_idade")
	private int classificacaoIdade;
	
	private double preco;
	
	@Column(name = "midia_alugada")
	private boolean midiaAlugada = false;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genero_id")
	private Genero genero;
	
	public int getId() {
		return id;
	}

	public String getTipoMidia() {
		return tipoMidia;
	}

	public void setTipoMidia(String tipoMidia) {
		this.tipoMidia = tipoMidia;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public String getDiretor() {
		return diretor;
	}

	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

	public Date getDuracao() {
		return duracao;
	}

	public void setDuracao(Date duracao) {
		this.duracao = duracao;
	}

	public int getClassificacaoIdade() {
		return classificacaoIdade;
	}

	public void setClassificacaoIdade(int classificacaoIdade) {
		this.classificacaoIdade = classificacaoIdade;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public boolean isMidiaAlugada() {
		return midiaAlugada;
	}

	public void setMidiaAlugada(boolean midiaAlugada) {
		this.midiaAlugada = midiaAlugada;
	}

}
