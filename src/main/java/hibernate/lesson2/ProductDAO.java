package hibernate.lesson2;

import hibernate.lesson1.HibernateUtils;
import hibernate.lesson1.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.OptimisticLockException;
import java.util.Arrays;
import java.util.List;

public class ProductDAO {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
//        Product product = new Product();
//        product.setName("table new");
//        product.setDescription("yellow & red");
//        product.setPrice(80);

  //      save(product);

        Product product1 = new Product();
       product1.setId(21);
        product1.setName("table new111!");
        product1.setDescription("broyn & yellos");
        product1.setPrice(90);

        Product product2 = new Product();
        product2.setId(22);
        product2.setName("table new222!");
        product2.setDescription("carton & yellos");
        product2.setPrice(190);

        Product product3 = new Product();
        product3.setId(24);
        product3.setName("table new333!");
        product3.setDescription("carton & green");
        product3.setPrice(850);


        List<Product> products  = Arrays.asList(product1, product2, product3);
        deleteAll(products);

        //saveAll(products);

//        Product productUpdating = new Product();
//        productUpdating.setId(2);
//        productUpdating.setName("table one");
//        productUpdating.setDescription("Java table");
//        productUpdating.setPrice(45);
//        update(productUpdating);

//        Product productUpdating = new Product();
//        productUpdating.setId(4);
//        productUpdating.setName("table four");
//        productUpdating.setDescription("Java vs C#");
//        productUpdating.setPrice(45);
//        update(productUpdating);
//
//        Product productDelate = new Product();
//        productDelate.setId(2);
//        delete(productDelate);


    }

    public static void save(Product product) {
        //create sesion/tr

        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            session.save(product);

            //close session/tr
            session.getTransaction().commit();
            System.out.println("Save is done");

        } catch (HibernateException e) {
            System.err.println("Save is failded");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();
        }

    }


    public static void update(Product product) {
        //create sesion/tr

        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            session.update(product);

            //close session/tr
            session.getTransaction().commit();
            System.out.println("Update is done");

        } catch (HibernateException e) {
            System.err.println("Update is failded");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();
        }

    }

    public static void delete(Product product) {
        //create sesion/tr

        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            session.delete(product);

            //close session/tr
            session.getTransaction().commit();
            System.out.println("Delete is done");

        } catch (HibernateException e) {
            System.err.println("Delete is failded");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();
        }

    }


    public static void saveAll(List<Product> products) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            for (Product product : products) {
                session.save(product);
            }
            //close session/tr
            session.getTransaction().commit();
            System.out.println("Save is done");

        } catch (HibernateException e) {
            System.err.println("Save is failded");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();
        }
    }

    public static void updateAll(List<Product> products) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            for (Product product : products) {
                session.update(product);
            }
            //close session/tr
            session.getTransaction().commit();
            System.out.println("Update all is done");

        } catch (HibernateException e) {
            System.err.println("Update all is failded");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();
        }
    }


public static void deleteAll(List<Product> products) {
    Session session = null;
    Transaction tr = null;
    try {
        session = createSessionFactory().openSession();
        tr = session.getTransaction();
        tr.begin();

        //action
        for (Product product : products) {
            session.delete(product);
        }
        //close session/tr
        session.getTransaction().commit();
        System.out.println("delete all is done");

    } catch (HibernateException e) {
        System.err.println("delete all is failded");
        System.err.println(e.getMessage());
        if (tr != null)
            tr.rollback();
    }
    finally {
        if (session != null)
            session.close();
    }
}


    public static SessionFactory createSessionFactory() {
        //singleton pattern
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
