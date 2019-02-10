package hibernate.lesson2.HW2;

import hibernate.lesson2.HW.Product;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;


public class ProductDAO {

    private static SessionFactory sessionFactory;


    public static SessionFactory createSessionFactory() {
        //singleton pattern
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }


    /*Method to findById(Long id) - поиск продуктов по имени     */
    public Product findById(long id) {
        Session session = null;
        Transaction tr = null;
        List<Product> products = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String sql = "SELECT *FROM PRODUCT WHERE ID=" + id;
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(Product.class);
            products = query.list();
            tr.commit();
            System.out.println("find by id done");
        } catch (HibernateException e) {
            System.out.println("Find by id is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }

        return products.isEmpty()? null: products.get(0);
    }


    public List<Product> findByName(String name) {
        Session session = null;
        Transaction tr = null;
        List<Product> products = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String sql = "SELECT *FROM PRODUCT WHERE NAME=:name";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(Product.class);
            query.setParameter("name", name);
            products = query.list();
            tr.commit();
            System.out.println("find by name done");
        } catch (HibernateException e) {
            System.out.println("Find by name is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }

        return products;
    }

    public List<Product> findByContainedName(String name) {
        Session session = null;
        Transaction tr = null;
        List<Product> products = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String sql = "SELECT *FROM PRODUCT WHERE NAME LIKE" + "'%" + name + "%'";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(Product.class);
            //query.setParameter("name", name);
            products = query.list();
            tr.commit();
            System.out.println("find by name done");
        } catch (HibernateException e) {
            System.out.println("Find by name is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }

        return products;
    }


    public List<Product> findByPrice(int price, int delta) {
        Session session = null;
        Transaction tr = null;
        List<Product> products = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String sql = "SELECT *FROM PRODUCT WHERE PRICE >=:lowLimit AND PRICE <=:highLimit";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(Product.class);
            query.setParameter("lowLimit", price - delta);
            query.setParameter("highLimit", price + delta);
            products = query.list();
            tr.commit();
            System.out.println("find by name done");
        } catch (HibernateException e) {
            System.out.println("Find by name is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }

        return products;
    }


    public List<Product> findByNameSortedAsc(String name) {
        Session session = null;
        Transaction tr = null;
        List<Product> products = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String sql = "SELECT *FROM PRODUCT WHERE NAME =:name ORDER BY NAME ASC";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(Product.class);
            query.setParameter("name", name);

            products = query.list();
            tr.commit();
            System.out.println("find by name sorted Asc");
        } catch (HibernateException e) {
            System.out.println("Find by name sorted asc is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }

        return products;
    }



    public List<Product> findByNameSortedDesc(String name) {
        Session session = null;
        Transaction tr = null;
        List<Product> products = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String sql = "SELECT *FROM PRODUCT WHERE NAME =:name ORDER BY NAME DESC";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(Product.class);
            query.setParameter("name", name);

            products = query.list();
            tr.commit();
            System.out.println("find by name sorted Asc");
        } catch (HibernateException e) {
            System.out.println("Find by name sorted asc is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }

        return products;
    }


    public List<Product> findByPriceSortedDesc(int price, int delta) {
        Session session = null;
        Transaction tr = null;
        List<Product> products = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            String sql = "SELECT *FROM PRODUCT WHERE PRICE >=:lowLimit AND price<=:highLimit ORDER BY PRICE DESC";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(Product.class);
            query.setParameter("lowLimit", price - delta);
            query.setParameter("highLimit", price + delta);

            products = query.list();
            tr.commit();
            System.out.println("find by price sorted desc");
        } catch (HibernateException e) {
            System.out.println("Find by price sorted desc is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }

        return products;
    }



}
