package controller;

import java.awt.List;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sun.faces.taglib.html_basic.OutputFormatTag;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;

import util.Verificar;

import dal.AluguelDAO;
import dal.ClienteDAO;
import dal.MidiaDAO;


import model.Aluguel;
import model.Cliente;
import model.ItemAluguel;
import model.Midia;

@SessionScoped
@ManagedBean(name = "mAluguelBean")
public class MAluguelBean {
	
	private int idCliente;
	private int idAluguel; 
	private int idMidiaRemover;
	private int idMidia;
	private int valorTotal;
	private String nomeCliente;
	
	private Cliente cliente = new Cliente();
	private ItemAluguel item = new ItemAluguel();
	private Aluguel aluguel = new Aluguel();

	private ArrayList<Aluguel> alugueis = new ArrayList<Aluguel>();
	private ArrayList<ItemAluguel> itens = new ArrayList<ItemAluguel>();
	//provisorio
	private ArrayList<ItemAluguel> carrinho = new ArrayList<ItemAluguel>();
	
//////////////////////////////////////GETS E SETS //////////////////////////////////////////////////////////////////
	
	public int getValorTotal() {
		return valorTotal;
	}

	public ArrayList<ItemAluguel> getCarrinho() {
		return AluguelDAO.buscaCarrinho();
	}

	public void setCarrinho(ArrayList<ItemAluguel> carrinho) {
		this.carrinho = carrinho;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdAluguel() {
		return idAluguel;
	}

	public void setIdAluguel(int idAluguel) {
		this.idAluguel = idAluguel;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public int getIdMidiaRemover() {
		return idMidiaRemover;
	}

	public void setIdMidiaRemover(int idMidiaRemover) {
		this.idMidiaRemover = idMidiaRemover;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setValorTotal(int valorTotal) {
		this.valorTotal = valorTotal;
	}

	public int getIdMidia() {
		return idMidia;
	}

	public void setIdMidia(int idMidia) {
		this.idMidia = idMidia;
	}

	public ItemAluguel getItem() {
		return item;
	}

	public void setItem(ItemAluguel item) {
		this.item = item;
	}

	public Aluguel getAluguel() {
		return aluguel;
	}

	public void setAluguel(Aluguel aluguel) {
		this.aluguel = aluguel;
	}

	public ArrayList<Aluguel> getAlugueis() {
		return AluguelDAO.retornarAluguel();
	}

	public void setAlugueis(ArrayList<Aluguel> alugueis) {
		this.alugueis = alugueis;
	}

	public ArrayList<ItemAluguel> getItens() {
		return itens;
	}

	public void setItens(ArrayList<ItemAluguel> itens) {
		this.itens = itens;
	}
	
//////////////////////////////////////////////////ACTIONS///////////////////////////////////////////////////////////
	
	public void concluirAluguel() throws ParseException{
		java.util.Date data = new java.util.Date();
		aluguel = new Aluguel();
		cliente =  ClienteDAO.buscarClientePorId(idCliente);
		if(cliente  != null){
			itens = AluguelDAO.buscaCarrinho();
			if(itens != null){
				aluguel.setCliente(getCliente());
				aluguel.setDataDoAluguel(data);
				aluguel.setDataDaDevolucao(data);
				aluguel.setItens(itens);
				aluguel.setValorDoAluguel(valorTotal);
				for (ItemAluguel listaMidia : itens) {
					if(listaMidia.getMidia().isMidiaAlugada() == true) {
						Midia midia = listaMidia.getMidia();
					
						MidiaDAO.midiaDevolvida(midia);
					}
				}
				if(aluguel != null){
					if(AluguelDAO.cadastrarAluguel(aluguel)){
					
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Aluguel Concluido."));
				}else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro!", "Banco Indisponivel."));
				}
			}else{
				
			}}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro!", "Carrinho vazio."));
			}
		}
		else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro!", "Não a cliente."));
		
			
	}
		cancelarAluguel();
		}
	
	
	public String carrinho(){
		
		Cliente cliente = ClienteDAO.buscarClientePorId(idCliente);
		if(cliente != null ){
			Midia midia = MidiaDAO.buscarMidiaPorId(idMidia);
		
			if ((Verificar.getIdade(cliente.getDataNacismento())) >= midia.getClassificacaoIdade()) {
				if(midia.isMidiaAlugada()== false) {
					item = new ItemAluguel();
					midia.setMidiaAlugada(true);
					item.setMidia(midia);
					item.setGenero(midia.getGenero());
					item.setDataDaAdicao(Calendar.getInstance());
					valorTotal += midia.getPreco();
					System.out.println("total do aluguel : "+valorTotal);
					carrinho.add(item);
					AluguelDAO.cadastrarCarrinho(item);
					
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ok", "no carrinho."));
					
				}else {//fim if(midia.isMidiaAlugada()== false)
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro!", "Filme já alugado."));
				}
			}else{// fim if ((Verificar.getIdade(cliente.getDataNacismento())) <= midia.getClassificacaoIdade())
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro!", "Cliente com idade baixo do recomentado."));
			}
		}else{//fim if(cliente != null)
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro!", "Cliente não encontrado."));
		}
		return "#";
	}
	
	public String cancelarAluguel(){
		for (ItemAluguel listaMidia : itens) {
			if(listaMidia.getMidia().isMidiaAlugada() == true) {
				Midia midia = listaMidia.getMidia();
				midia.setMidiaAlugada(false);
				MidiaDAO.midiaDevolvida(midia);
			}
		}
		itens.clear();
		carrinho.clear();
		valorTotal = 0;
		nomeCliente = "";
		return "Template.xhtml";
	}
	
	
	public void devolverMidia(Aluguel a) {
		itens = new ArrayList<ItemAluguel>();
		itens = a.getItens();
			for (ItemAluguel listaMidia : itens) {
				if(listaMidia.getMidia().isMidiaAlugada() == true) {
					Midia midia = listaMidia.getMidia();
					midia.setMidiaAlugada(false);
					MidiaDAO.midiaDevolvida(midia);
				}
			}
			if(AluguelDAO.excluirAluguel(a)){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "!Sucesso ", "Filmes Devolvidos."));
				}else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "!Sucesso ", "Filmes Devolvidos."));
				}
			aluguel = new Aluguel();
			
	}
	
	
	public String removerItem(){
		
		for(ItemAluguel itensLista : carrinho) {
			if(itensLista.getId() == idMidiaRemover){
				Midia midia = itensLista.getMidia();
				midia.setMidiaAlugada(false);
				carrinho.remove(itensLista);
				AluguelDAO.excluirCarrinho(itensLista);
				return "AlugarMidia.xhtml";
			}
		}
		return "#"; 
	}


}
