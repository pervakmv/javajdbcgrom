package hibernate.lesson2.HW;

import hibernate.lesson2.HW2.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

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

    public void save(Product product) {
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


    public void saveAll(List<Product> products) {
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
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();
        }
    }

    public Product findById(long id) {
        Session session = null;
        Transaction tr = null;
        List<Product> resProduct = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query query = session.createQuery("from Product where id = : id");
            query.setParameter("id", id);
            resProduct = query.list();

            session.getTransaction().commit();
            System.out.println("find ById done");
        } catch (HibernateException e) {
            System.out.println("Find by id is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return resProduct.isEmpty()?null:resProduct.get(0);
    }

    public Product findByName(String name) {
        Session session = null;
        Transaction tr = null;
        List<Product> resProduct = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query query = session.createQuery("from Product where name = : name");
            query.setParameter("name", name);
            resProduct = query.list();

            session.getTransaction().commit();
            System.out.println("find By name done");
        } catch (HibernateException e) {
            System.out.println("Find by name is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return resProduct.isEmpty()?null:resProduct.get(0);
    }


    public List<Product> getAllProducts() {
        Session session = null;
        Transaction tr = null;
        List<Product> resProduct = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query query = session.createQuery("from Product");
            resProduct = query.list();
            session.getTransaction().commit();
            System.out.println("find ById done");
        } catch (HibernateException e) {
            System.out.println("Find by id is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return resProduct;
    }

    List<Product> findContainedNameRev1(String name){
        List<Product> products = getAllProducts();
        List<Product> resProducts = new ArrayList<>();

        for(Product product : products){
            if(product!=null){
                String productsName =product.getName();
                if(productsName.indexOf(name)>0)
                    resProducts.add(product);
            }

        }
        return resProducts;
    }


    public List<Product> findContaineName(String name) {
        Session session = null;
        Transaction tr = null;
        List<Product> resProduct = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query query = session.createQuery("from Product where name like" + "'%" + name + "%'");
            resProduct = query.list();
            session.getTransaction().commit();
            System.out.println("find ById done");
        } catch (HibernateException e) {
            System.out.println("Find by id is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return resProduct;
    }


    public List<Product> findByPrice(int price, int delta) {
        Session session = null;
        Transaction tr = null;
        List<Product> resProduct = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query query = session.createQuery("from Product where price >=:lowLimit AND price<=:highLimit");
            query.setParameter("lowLimit", price - delta);
            query.setParameter("highLimit", price + delta);
            resProduct = query.list();
            session.getTransaction().commit();
            System.out.println("find ByPrice done");
        } catch (HibernateException e) {
            System.out.println("Find by price is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return resProduct;
    }


    public List<Product> findByNameSortedAsc(String name) {
        Session session = null;
        Transaction tr = null;
        List<Product> resProduct = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query query = session.createQuery("from Product where name =: name order by name asc");
            query.setParameter("name", name);
            resProduct = query.list();
            session.getTransaction().commit();
            System.out.println("find ByPrice done");
        } catch (HibernateException e) {
            System.out.println("Find by price is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return resProduct;
    }

    public List<Product> findByNameSortedDesc(String name) {
        Session session = null;
        Transaction tr = null;
        List<Product> resProduct = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query query = session.createQuery("from Product where name =: name order by name desc");
            query.setParameter("name", name);
            resProduct = query.list();
            session.getTransaction().commit();
            System.out.println("find ByPrice done");
        } catch (HibernateException e) {
            System.out.println("Find by price is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return resProduct;
    }


    public List<Product> findByPriceSortedDesc(int price, int delta) {
        Session session = null;
        Transaction tr = null;
        List<Product> resProduct = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query query = session.createQuery("from Product where price >=:lowLimit AND price<=:highLimit order by price desc");
            query.setParameter("lowLimit", price - delta);
            query.setParameter("highLimit", price + delta);
            resProduct = query.list();
            session.getTransaction().commit();
            System.out.println("find ByPrice done");
        } catch (HibernateException e) {
            System.out.println("Find by price is failed");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return resProduct;
    }

}
