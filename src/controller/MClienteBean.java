package controller;

import java.text.ParseException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import util.Verificar;

import dal.ClienteDAO;
import dal.GeneroDAO;
import model.Cliente;
import model.Genero;

@SessionScoped
@ManagedBean(name = "mClienteBean")
public class MClienteBean {
	private Cliente cliente = new Cliente();
	private ArrayList<Cliente> lista = new ArrayList<Cliente>(); 
////////////////////////////////////////////////////////GET E SET /////////////////////////////////////////////////////////////////////////////////////////
	
	
public ArrayList<Cliente> getLista() {
return ClienteDAO.listarCliente();
}

public void setLista(ArrayList<Cliente> lista) {
this.lista = lista;
}

public Cliente getCliente() {
return cliente;
}

public void setCliente(Cliente cliente) {
this.cliente = cliente;
}
	
	
	// Action
	public void cadastrarCliente(Cliente c) throws ParseException {
		cliente = new Cliente();
		cliente = c;
		if((Verificar.validEmail(cliente.getEmail()))){
			if ((cliente.getDataNacismento() != null)) {
				int veriDtNasc = Verificar.comparaData(cliente.getDataNacismento());
				if (veriDtNasc == 3) {
					
					int status = ClienteDAO.cadastrarCliente(cliente);
					if (status == 1) {
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cliente Cadastrado."));
						} else if(status == 2) {
							
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro!", "Cliente já cadastrado."));
						}else{
							//fim (status == 2) 
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro!", "Cliente não encontrado."));
						}
					}else if((veriDtNasc == 1) || (veriDtNasc == 2)){
						// fim if (veriDtNasc == 3)
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Campo data com valor invalido"));
					}
			}else{
				//fim ((cliente.getDataNacismento() != null))
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Campo data vazio"));
			}
		}else{
			//fim ((Verificar.validEmail(cliente.getEmail())))
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Email invalido"));
		}
		cliente = new Cliente();
	}
	
	
	public String mandarDadosParaAlterarCliente(Cliente c) {
		this.cliente = c;
		return "AlterarCliente.xhtml";
	}
	public String alterarCliente(Cliente c) throws ParseException{
		
		int veriDtNasc = 0;
		
		if((Verificar.validEmail(c.getEmail()))){
			if ((c.getDataNacismento() != null)) {
				 veriDtNasc = Verificar.comparaData(c.getDataNacismento());
				 if(veriDtNasc == 3){
					boolean status = ClienteDAO.alterarCliente(c);
					if (status) {
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cliente alterado ."));
						}else{
							//fim (status == 2) 
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro!", "Cliente não encontrado."));
						}
					}else if((veriDtNasc == 1) || (veriDtNasc == 2)){
						// fim if (veriDtNasc == 3)
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Campo data com valor invalido"));
					}
			}else{
				//fim ((cliente.getDataNacismento() != null))
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Campo data vazio"));
			}
		}else{
			//fim ((Verificar.validEmail(cliente.getEmail())))
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Email invalido"));
		}
		cliente = new Cliente();
		return "ListarCliente.xhtml";
	}

	
}
