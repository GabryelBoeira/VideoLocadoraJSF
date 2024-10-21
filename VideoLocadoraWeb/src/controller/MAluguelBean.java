package controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import model.Aluguel;
import model.Cliente;
import model.ItemAluguel;
import model.Midia;
import util.Verificar;
import dal.AluguelDAO;
import dal.ClienteDAO;
import dal.MidiaDAO;

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

	private List<Aluguel> alugueis = new ArrayList<Aluguel>();
	private List<ItemAluguel> itens = new ArrayList<ItemAluguel>();
	
	// provisorio
	private ArrayList<ItemAluguel> carrinho = new ArrayList<ItemAluguel>();

	// ////////////////////////////////////////////////ACTIONS///////////////////////////////////////////////////////////

	public void concluirAluguel() throws ParseException {
		Date data = new Date();
		aluguel = new Aluguel();
		cliente = ClienteDAO.buscarClientePorId(idCliente);
		if (cliente != null) {
			itens = AluguelDAO.buscaCarrinho();
			if (itens != null) {
				aluguel.setCliente(getCliente());
				aluguel.setDataDoAluguel(data);
				aluguel.setDataDaDevolucao(data);
				aluguel.setItens(itens);
				aluguel.setValorDoAluguel(valorTotal);
				for (ItemAluguel listaMidia : itens) {
					if (listaMidia.getMidia().isMidiaAlugada() == true) {
						Midia midia = listaMidia.getMidia();
						MidiaDAO.midiaDevolvida(midia);
					}
				}
				if (aluguel != null) {
					if (AluguelDAO.cadastrarAluguel(aluguel)) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Aluguel Concluido."));
					} else {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro!", "Banco Indisponivel."));
					}
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso!", "Erro inesperado ao tentar alugar."));
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro!", "Carrinho vazio."));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro!", "N�o a cliente."));
		}		
	}

	public String carrinho() {

		Cliente cliente = ClienteDAO.buscarClientePorId(idCliente);
		if (cliente != null) {
			Midia midia = MidiaDAO.buscarMidiaPorId(idMidia);

			if ((Verificar.getIdade(cliente.getDataNacismento())) >= midia
					.getClassificacaoIdade()) {
				if (midia.isMidiaAlugada() == false) {
					item = new ItemAluguel();
					midia.setMidiaAlugada(true);
					item.setMidia(midia);
					item.setDataDaAdicao(new Date());
					valorTotal += midia.getPreco();
					System.out.println("total do aluguel : " + valorTotal);
					carrinho.add(item);
					AluguelDAO.cadastrarCarrinho(item);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ok", "no carrinho."));
					
				} else {// fim if(midia.isMidiaAlugada()== false)
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro!", "Filme j� alugado."));
				}
			} else {// fim if ((Verificar.getIdade(cliente.getDataNacismento())) // <= midia.getClassificacaoIdade())
				FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro!", "Cliente com idade baixo do recomentado."));
			}
		} else {// fim if(cliente != null)
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro!", "Cliente n�o encontrado."));
		}
		return "#";
	}

	public String cancelarAluguel() {
		for (ItemAluguel listaMidia : itens) {
			if (listaMidia.getMidia().isMidiaAlugada() == true) {
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

	public void devolverMidia(Aluguel devolucao) {
		itens = new ArrayList<ItemAluguel>();
		itens = devolucao.getItens();
		for (ItemAluguel listaMidia : itens) {
			if (listaMidia.getMidia().isMidiaAlugada() == true) {
				Midia midia = listaMidia.getMidia();
				midia.setMidiaAlugada(false);
				MidiaDAO.midiaDevolvida(midia);
			}
		}
		if (AluguelDAO.excluirAluguel(devolucao)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "!Sucesso ", "Filmes Devolvidos."));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "!Sucesso ", "Filmes Devolvidos."));
		}
		aluguel = new Aluguel();
	}

	public String removerItem() {
		for (ItemAluguel itensLista : carrinho) {
			if (itensLista.getId() == idMidiaRemover) {
				Midia midia = itensLista.getMidia();
				midia.setMidiaAlugada(false);
				carrinho.remove(itensLista);
				AluguelDAO.excluirCarrinho(itensLista);
				return "AlugarMidia.xhtml";
			}
		}
		return "#";
	}
//////////////////////////////////////GETS E SETS ////////////////////////////////////////////////////////////////////

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
	
	public List<Aluguel> getAlugueis() {
		return AluguelDAO.retornarAluguel();
	}
	
	public void setAlugueis(List<Aluguel> alugueis) {
		this.alugueis = alugueis;
	}
	
	public List<ItemAluguel> getItens() {
		return itens;
	}
	
	public void setItens(List<ItemAluguel> itens) {
		this.itens = itens;
	}
}
