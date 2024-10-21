package dal;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import conexao.Conexao;

import model.Genero;

public class GeneroDAO {

	public static int adicionarGenero(Genero g) {
		try {
			if ((procurarGeneroPorNome(g)) == null) {
				EntityManager em = Conexao.getEntityManager();
				em.getTransaction().begin();
				em.persist(g);
				em.getTransaction().commit();
				em.close();
				return 1;
			} else {
				return 2;
			}
		} catch (RollbackException e) {
			System.out.println(e);
			return 3;
		}
	}

	public static void removerGenero(Genero g) {
		try {
			EntityManager em = Conexao.getEntityManager();
			em.getTransaction().begin();
			g = em.getReference(Genero.class, g.getId());
			em.remove(g);
			em.getTransaction().commit();
			em.close();
		} catch (RollbackException e) {
			System.out.println(e);
		}
	}

	public static ArrayList<Genero> retornarLista() {
		// REMOVER A LINHA DO BEGIN() EM QUALQUER BUSCA NO BANCO
		EntityManager em = Conexao.getEntityManager();
		Query q = em.createQuery("SELECT c FROM Genero c");
		@SuppressWarnings("unchecked")
		ArrayList<Genero> lista = (ArrayList<Genero>) q.getResultList();
		em.close();
		return lista;
	}

	public static Boolean alterarGenero(Genero g) {
		try {
			System.out.println("alterar" + g.getId());
			EntityManager em = Conexao.getEntityManager();
			em.getTransaction().begin();
			Genero genero = em.find(Genero.class, g.getId());
			genero.setDescricao(g.getDescricao());
			genero.setNome(g.getNome());
			em.merge(genero);
			em.getTransaction().commit();
			em.close();
			return true;
		} catch (RollbackException e) {
			return false;
		}

	}

	public static Genero retornarGenero(String nome) {
		Genero genero = new Genero();
		// REMOVER A LINHA DO BEGIN() EM QUALQUER BUSCA NO BANCO
		EntityManager em = Conexao.getEntityManager();
		Query q = em
				.createQuery("SELECT c FROM Genero WHERE c.nome = :nomeGenero");
		q.setParameter("nomeGenero", nome);
		genero = (Genero) q.getResultList();
		em.close();
		return genero;
	}

	public static Genero buscarGeneroPorId(int id) {

		EntityManager em = Conexao.getEntityManager();
		return em.find(Genero.class, id);
	}

	public static Genero procurarGeneroPorNome(Genero genero) {
		EntityManager em = Conexao.getEntityManager();
		try {
			Query q = em
					.createQuery("SELECT c FROM Genero c WHERE c.nome = :nomeGenero");

			q.setParameter("nomeGenero", genero.getNome());

			Genero generoBanco = (Genero) q.getSingleResult();
			return generoBanco;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}

	}

}
