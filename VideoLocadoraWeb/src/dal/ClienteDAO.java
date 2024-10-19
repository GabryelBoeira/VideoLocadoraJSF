package dal;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import conexao.Conexao;

import model.Cliente;
import model.Genero;

public class ClienteDAO{

	public static int cadastrarCliente(Cliente cliente) {
		try {

			if ((procurarClientePorNome(cliente.getNome())) == null) {
				EntityManager em = Conexao.getEntityManager();
				em.getTransaction().begin();
				em.persist(cliente);
				em.getTransaction().commit();
				em.close();
				return 1;
			} else {
				return 2;
			}
		} catch (Exception e) {
			return 3;
		}
	}
	
	// exclusao de clientes
	public static boolean excluirCliente(Cliente c) {
		EntityManager em = Conexao.getEntityManager();
		em.getTransaction().begin();
		c = em.getReference(Cliente.class, c.getId());
		em.remove(c);
		em.getTransaction().commit();
		em.close();
		return true;
	}
		
	//Lista de clientes
	@SuppressWarnings("unchecked")
	public static ArrayList<Cliente> listarCliente() {
		EntityManager em = Conexao.getEntityManager();
		Query q = em.createQuery("SELECT c FROM Cliente c");
		ArrayList<Cliente> clienteBanco = 
				(ArrayList<Cliente>) q.getResultList();
		em.close();
		return clienteBanco;
	}

	public static Cliente buscarClientePorId(int idCliente) {
		EntityManager em = Conexao.getEntityManager();
		Cliente c = em.find(Cliente.class, idCliente);
		em.close();
		return c;
	}
	public static Boolean alterarCliente(Cliente g) {
		try {
			System.out.println("alterar" + g.getId());
			EntityManager em = Conexao.getEntityManager();
			em.getTransaction().begin();
			Cliente cliente = em.find(Cliente.class, g.getId());
			cliente.setNome(g.getNome());
			cliente.setEmail(g.getEmail());
			cliente.setDataNacismento(g.getDataNacismento());
			em.merge(cliente);
			em.getTransaction().commit();
			em.close();
			return true;
		} catch (RollbackException e) {
			return false;
		}

	}
	public static Cliente procurarClientePorNome(String cliente) {
		EntityManager em = Conexao.getEntityManager();
		try {
			Query q = em
					.createQuery("SELECT c FROM Cliente c WHERE c.nome = :nomeCliente");

			q.setParameter("nomeCliente", cliente);

			Cliente clienteBanco = (Cliente) q.getSingleResult();
			return clienteBanco;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}

	}
}

