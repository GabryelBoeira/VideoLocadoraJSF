package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Conexao {

	private static final String NOME_DA_CONEXAO = "videolocadoraDB";
	private static EntityManagerFactory factory;

	static {
		factory = Persistence.createEntityManagerFactory(NOME_DA_CONEXAO);
	}

	public Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/videolocadoraDB", "sa", "");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
	public static void close() {
		factory.close();
	}

}
