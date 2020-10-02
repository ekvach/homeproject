package application.hibernateconfig;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import application.entities.Pipe;

public class HibernateConnectionConfig {

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();

				Properties hiberProp = new Properties();

				hiberProp.put(Environment.DRIVER, "org.h2.Driver");
//				hiberProp.put(Environment.URL,
//						"jdbc:h2:tcp://localhost/D:/Devstvo/MyJavaProjects/IdeaProjects/111111/dbbesttestprojectkvach/db/db");
				hiberProp.put(Environment.URL, "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
				hiberProp.put(Environment.USER, "sa");
				hiberProp.put(Environment.PASS, "");
				hiberProp.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
				hiberProp.put(Environment.SHOW_SQL, "true");
				hiberProp.put(Environment.FORMAT_SQL, "true");
				hiberProp.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
				hiberProp.put(Environment.HBM2DDL_AUTO, "create-drop");

				configuration.setProperties(hiberProp);

				configuration.addAnnotatedClass(Pipe.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();

				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (Exception e) {
				throw new IllegalArgumentException("something went wrong upon session factory creation", e);
			}
		}
		return sessionFactory;
	}
}
