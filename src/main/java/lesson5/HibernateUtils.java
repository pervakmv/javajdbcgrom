package lesson5;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private SessionFactory sessionFactory;

    SessionFactory createSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }


}
