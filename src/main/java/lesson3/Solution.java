package lesson3;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public List<Product> findProductsByPrice(int price, int delta) {
        List<Product> resArrayList = new ArrayList<>();

        List<Product> products = ProductDAO.getProducts();

        for (Product element : products) {
            int priceOfProduct = element.getPrice();
            if ((priceOfProduct >= (price - delta)) && priceOfProduct <= (price + delta)) {
                resArrayList.add(element);
            }
        }
        return resArrayList;
    }

    public List<Product> findProductsByName(String word) throws Exception {

        validateWord(word);
        List<Product> resArrayList = new ArrayList<>();
        List<Product> products = ProductDAO.getProducts();
        for (Product element : products) {
            if (element.getName().equals(word)) {
                resArrayList.add(element);
            }
        }
        return resArrayList;
    }


    private void validateWord(String word) throws IllegalArgumentException {
        if (word.length() < 3)
            throw new IllegalArgumentException("number of characters less than three");
        String[] words = word.split(" ");
        if (words.length > 1)
            throw new IllegalArgumentException("the number of words is greater than one");
        char[] chrAr = word.toCharArray();
        for (char ch : chrAr) {
            if (!Character.isLetter(ch)
                    && !Character.isDigit(ch))
                throw new IllegalArgumentException("there are specials in the string");
        }
    }

    public List<Product> findProductsWithEmptyDescription() {
        List<Product> resArrayList = new ArrayList<>();
        List<Product> products = ProductDAO.getProducts();
        for (Product product : products) {

            if (product.getDescription() == null || product.getDescription().trim().isEmpty()) {
                resArrayList.add(product);

            }
        }
        return resArrayList;
    }

}
