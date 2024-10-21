package dal;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Aluguel;
import model.ItemAluguel;
import conexao.Conexao;

public class AluguelDAO {
	
	public static boolean cadastrarAluguel(Aluguel aluguel) {
        EntityManager em = Conexao.getEntityManager();

		try {

			em.getTransaction().begin();
			em.persist(aluguel);
			em.flush();
			em.getTransaction().commit();
			em.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			Conexao.close();
		}
	}

	public static boolean cadastrarCarrinho(ItemAluguel c) {
		EntityManager em = Conexao.getEntityManager();
		try {

			em.getTransaction().begin();
			em.persist(c);
			em.getTransaction().commit();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static ArrayList<ItemAluguel> buscaCarrinho() {
		EntityManager em = Conexao.getEntityManager();
		Query q = em.createQuery("SELECT c FROM ItemAluguel c");
		ArrayList<ItemAluguel> lista = (ArrayList<ItemAluguel>) q.getResultList();
		em.close();
		return lista;
	}

	public static ArrayList<Aluguel> retornarAluguel() {
		// REMOVER A LINHA DO BEGIN() EM QUALQUER BUSCA NO BANCO
		EntityManager em = Conexao.getEntityManager();
		Query q = em.createQuery("SELECT c FROM Aluguel c");
		ArrayList<Aluguel> lista = (ArrayList<Aluguel>) q.getResultList();
		em.close();
		return lista;
	}

	public static boolean excluirCarrinho(ItemAluguel i) {
		EntityManager em = Conexao.getEntityManager();
		em.getTransaction().begin();
		ItemAluguel item = em.getReference(ItemAluguel.class, i.getId());
		em.remove(item);
		em.getTransaction().commit();
		em.close();
		return true;
	}

	public static boolean excluirAluguel(Aluguel aluguel) {
		EntityManager em = Conexao.getEntityManager();
		em.getTransaction().begin();
		aluguel = em.getReference(Aluguel.class, aluguel.getId());
		em.remove(aluguel);
		em.getTransaction().commit();
		em.close();
		return true;
	}

	public static Aluguel buscarAluguelPorId(int id) {

		EntityManager em = Conexao.getEntityManager();
		return em.find(Aluguel.class, id);
	}
}
