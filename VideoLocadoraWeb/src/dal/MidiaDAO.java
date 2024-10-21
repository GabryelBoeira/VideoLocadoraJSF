package dal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import conexao.Conexao;
import model.Genero;
import model.Midia;

public class MidiaDAO {
	/*
	 * 
	 * Data 26/11/2017
	 */
	// adiciona uma nova midia no bd
	public static boolean adicionarMidias(Midia midia) {
		try {
			EntityManager em = Conexao.getEntityManager();
			em.getTransaction().begin();
			em.persist(midia);
			em.getTransaction().commit();
			em.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Midia buscarMidiaPorNome(String titulo) {
		titulo.toUpperCase();
		EntityManager em = Conexao.getEntityManager();

		try {
			Query q = em
					.createQuery("SELECT c FROM Midia c WHERE c.titulo = :nomeTitulo");

			q.setParameter("nomeTitulo", titulo);

			Midia midia = (Midia) q.getSingleResult();
			return midia;
		} catch (Exception e) {
			// getSingleResult() nao retorna null e gera uma excessaçao
			return null;
		} finally {
			em.close();
		}
	}

	// Exclui a midia selhecionada
	public static boolean excluirMidias(Midia midia) {
		EntityManager em = Conexao.getEntityManager();
		em.getTransaction().begin();
		midia = em.getReference(Midia.class, midia.getId());
		em.remove(midia);
		em.getTransaction().commit();
		em.close();
		return true;
	}

	// avisa que a midia esta alugada
	public static void midiaAlugada(Midia m) {
		try {
			System.out.println("alterar" + m.getId());
			EntityManager em = Conexao.getEntityManager();
			em.getTransaction().begin();
			Midia midia = em.find(Midia.class, m.getId());
			midia.setMidiaAlugada(m.isMidiaAlugada());
			em.merge(midia);
			em.getTransaction().commit();
			em.close();
		} catch (RollbackException e) {
			System.out.println(e);
		}
	}

	// avisa que a midia voi devolvida
	public static boolean midiaDevolvida(Midia m) {
		try {
			System.out.println("alterar" + m.getId());
			EntityManager em = Conexao.getEntityManager();
			em.getTransaction().begin();
			Midia midia = em.find(Midia.class, m.getId());
			midia.setMidiaAlugada(m.isMidiaAlugada());
			em.merge(midia);
			em.getTransaction().commit();
			em.close();
			return true;
		} catch (RollbackException e) {
			return false;
		}
	}

	public static ArrayList<Midia> retornarStatus() {
		Boolean status = false;
		EntityManager em = Conexao.getEntityManager();
		try {
			Query q = em
					.createQuery("SELECT c FROM Midia c WHERE c.midiaAlugada = :status");
			q.setParameter("status", status);
			ArrayList<Midia> midiaBanco = (ArrayList<Midia>) q.getResultList();

			return midiaBanco;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}

	}

	public static List<Midia> retornarMidia() {
		// REMOVER A LINHA DO BEGIN() EM QUALQUER BUSCA NO BANCO
		EntityManager em = Conexao.getEntityManager();
		Query q = em.createQuery("SELECT c FROM Midia c");
		List<Midia> lista = (List<Midia>) q.getResultList();
		em.close();
		return lista;
	}

	public static Midia buscarMidiaPorId(int id) {

		EntityManager em = Conexao.getEntityManager();
		return em.find(Midia.class, id);
	}
}
