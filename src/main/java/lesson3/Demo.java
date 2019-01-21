package lesson3;

public class Demo {
    public static void main(String[] args) throws Exception{
        ProductDAO productDAO = new ProductDAO();
        Product product  = new Product(15, "test3", " ", 101);


      // productDAO.save(product);

        //System.out.println(productDAO.getProducts());
       // System.out.println(productDAO.update(product));

//        try {
//            System.out.println(productDAO.delete(11));
//        }catch( Exception e){
//            System.err.println(e.getMessage());
//        }

       // System.out.println("getProducts");
       // System.out.println(ProductDAO.getProducts());
        Solution solution = new Solution();
 //       System.out.println("findProductsByPrice");
 //       System.out.println(solution.findProductsByPrice(100, 50));

        //System.out.println(solution.findProductsByName("toy"));
      //  System.out.println(solution.findProductsWithEmptyDescription());
        //solution.testSavePerformance();
        //solution.testDeleteByIdPerformance();
        //solution.testDeletePerformance();
        //solution.testSelectPerformance();
        //solution.testSelectByIdPerformance();


        solution.testSelectPerformanceFile();
    }

}

