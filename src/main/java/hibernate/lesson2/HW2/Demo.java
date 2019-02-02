package hibernate.lesson2.HW2;

public class Demo {
    public static void main(String[] args) {

        ProductDAO productDAO = new ProductDAO();

       // System.out.println(productDAO.findById(27));

        //System.out.println(productDAO.findByName("6AV2123-2DB03-0AX0"));

        //System.out.println(productDAO.findByContainedName("123"));

        //System.out.println(productDAO.findByPrice(300, 70));
        //System.out.println(productDAO.findByNameSortedAsc("6AV2123-2DB03-0AX0"));
        //System.out.println(productDAO.findByNameSortedDesc("6AV2123-2DB03-0AX0"));
        System.out.println(productDAO.findByPriceSortedDesc(300, 100));
    }
}
