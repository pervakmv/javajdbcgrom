package lesson3;

public class Demo {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        Product product  = new Product(11, "test", "test description", 101);


        //productDAO.save(product);

        //System.out.println(productDAO.getProducts());
       // System.out.println(productDAO.update(product));

        try {
            System.out.println(productDAO.delete(11));
        }catch( Exception e){
            System.err.println(e.getMessage());
        }
    }

}

