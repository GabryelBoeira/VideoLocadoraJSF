package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Conexao {

	public Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/videoLocadoraDB", "sa", "");
		} catch (SQLException e) {
			throw new RuntimeException(e); 
		}
	}
	private static final String NOME_DA_CONEXAO = "videoLocadoraDB";
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(NOME_DA_CONEXAO);

	public static EntityManager getEntityManager() {		
		return emf.createEntityManager();
}
}
