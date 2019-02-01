package hibernate.lesson2.HW;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {

        ProductDAO productDAO = new ProductDAO();

        List<Product> products = new ArrayList<>();

        Product product1 = new Product("6AV2123-2DB03-0AX0", "SIMATIC HMI KTP400 BASIC 4", 343);
        Product product2 = new Product("6EP1332-1SH71", "SIMATIC S7-1200 Блок питания PM1207", 80);
        Product product3 = new Product("6ES214-1HG40-0XB0", "CPU 1214C (14DI 24V DC; 10 DO Relay; 2AI), PS24V DC", 353);
        Product product4 = new Product("6ES7222-1BF32-0XB0", "Digital output, 8DO, 24 VDC", 101);
        Product product5 = new Product("6ES7231-4HD32-0XB0", "SIMATIC S7-1200, ANALOG INPUT, SM1231, 4AI, +/-10V, +/-5V, +/-2.5V, OR 0-20mA, 12Bit + SIGN BIT (13 BIT ADC)", 209);



        Product[] productArray = {product1, product2, product3, product4, product5};
        for(Product prd: productArray){
            products.add(prd);
        }

        //productDAO.saveAll(products);
//        System.out.println(productDAO.findById(27));
//        System.out.println(productDAO.findById(39));
//
//        System.out.println(productDAO.findByName("6ES7222-1BF32-0XB0"));

        //System.out.println(productDAO.getAllProducts());
       // System.out.println(productDAO.findContainedNameRev1("123"));

        //System.out.println(productDAO.findContaineName("123"));
       // System.out.println(productDAO.findByPrice(300, 50));
       // System.out.println(productDAO.findByNameSortedAsc("6AV2123-2DB03-0AX0"));
         System.out.println(productDAO.findByPriceSortedDesc(300, 100));

    }
}
