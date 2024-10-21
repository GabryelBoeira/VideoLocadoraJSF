package controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.Transactional;

import dal.GeneroDAO;
import dal.MidiaDAO;
import model.Genero;
import model.Midia;

@SessionScoped
@ManagedBean(name = "mMidiaBean")
public class MMidiaBean {
	private Midia midia = new Midia();
	private Genero genero = new Genero();
	private int idGenero;
	private List<Midia> midias = new ArrayList<Midia>();
	private List<Midia> midiasStatus = new ArrayList<Midia>();

///////////////////////////////////////////////////////////// ACTION ////////////////////////////////////////////////////////////////////////////////////	
	// remover midia
	public String removerMidia(Midia midia) {
		if (midia.isMidiaAlugada() == true) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", "Midia Alugada."));
		} else {
			MidiaDAO.excluirMidias(midia);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Midia Apagada."));
		}
		
		return "#";
	}

	@Transactional
	public String adicionarMidia(Midia m) {
		clean();
		midia = m;
		genero = GeneroDAO.buscarGeneroPorId(idGenero);
		midia.setGenero(genero);
		
		// Verificar valor vazio
		if ((midia.getTitulo() != "") && (midia.getClassificacaoIdade() >= 0)) {
			if ((genero != null)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Midia Cadastrado."));
				MidiaDAO.adicionarMidias(midia);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso!", "Genero em branco."));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso!", "Campos em branco."));
		}
		midia = new Midia();
		return "#";
	}
	
	private void clean() {
		midia = new Midia();
		genero = new Genero();
	}
	
///////////////////////////////////////////////////////////// GETS E SETS////////////////////////////////////////////////////////////////////////////////////
	public List<Midia> getMidiasStatus() {
		return MidiaDAO.retornarStatus();
	}
	
	public void setMidiasStatus(ArrayList<Midia> midiasStatus) {
		this.midiasStatus = midiasStatus;
	}
	
	public Genero getGenero() {
		return genero;
	}
	
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	
	public List<Midia> getMidias() {
		return MidiaDAO.retornarMidia();
	}
	
	public void setMidias(List<Midia> midias) {
		this.midias = midias;
	}
	
	public int getIdGenero() {
		return idGenero;
	}
	
	public void setIdGenero(int idGenero) {
		this.idGenero = idGenero;
	}
	
	public Midia getMidia() {
		return midia;
	}
	
	public void setMidia(Midia midia) {
		this.midia = midia;
	}

}
