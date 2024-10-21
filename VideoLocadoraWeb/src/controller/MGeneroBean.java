package controller;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dal.GeneroDAO;
import dal.MidiaDAO;
import model.Genero;
import model.Midia;

@SessionScoped
@ManagedBean(name = "mGeneroBean")
public class MGeneroBean {
	private Genero genero = new Genero();
	private ArrayList<Genero> generos = new ArrayList<Genero>();

	// remover genero
	public String removerGenero(Genero genero) {
		ArrayList<Midia> midias = MidiaDAO.retornarMidia();
		for (Midia midia : midias) {
			if (midia.getGenero().equals(genero)) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Genero n�o Apagado por estar vinculado a uma midia."));
				return "ListarGenero.xhtml";
			}
		}
		GeneroDAO.removerGenero(genero);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Genero Apagado."));
		return "ListarGenero.xhtml";
	}

	// alterar genero
	public String mandarDadosParaAlterar(Genero g) {
		this.genero = g;
		return "AlterarGenero.xhtml";
	}

	public String alterarGenero(Genero g) {
		genero.setId(g.getId());
		genero.setNome(g.getNome());
		genero.setDescricao(g.getDescricao());

		System.out.println("altBean" + g.getId());

		if (GeneroDAO.alterarGenero(genero)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Genero Alterado."));
		}
		genero = new Genero();
		return "ListarGenero.xhtml";
	}

	// cadastrar novo genero
	public String adicionarGenero(Genero g) {	
		genero.setNome(g.getNome());
		genero.setDescricao(g.getDescricao());
		
		// Verificar valores vazios
		if (genero.getNome() != "" && genero.getDescricao() != "") {
			int verificaGenero = GeneroDAO.adicionarGenero(genero);
			
			if (verificaGenero == 1) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Genero Cadastrado."));
			} else /* fim (verificaGenero == 1) */
				if (verificaGenero == 2) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Genero j� Cadastrado."));
			} else {
				// fim (verificaGenero == 2)
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro!", "Banco indisponivel."));
			}
		} else {
			// fim if ((genero.getNome() != "") && (genero.getDescricao() !=
			// ""))
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "campos em branco."));
		}
		genero = new Genero();
		return "Template.xhtml";
	}

///////////////////GETS e SETS /////////////////////////////////
	
	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public ArrayList<Genero> getGeneros() {
		return GeneroDAO.retornarLista();
	}

	public void setGeneros(ArrayList<Genero> generos) {
		this.generos = generos;
	}

}
