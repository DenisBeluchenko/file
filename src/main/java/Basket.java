import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Scanner;

public class Basket {
    protected int[] price;
    protected String[] products;
    protected int[] purchases;

    public Basket(int[] price, String[] products, int[] purchases) {
        this.price = price;
        this.products = products;
        this.purchases = purchases;
    }

    public void setPurchases(int[] purchases) {
        this.purchases = purchases;
    }

    public int[] getPurchases() {
        return purchases;
    }

    public void addToCart(int productNum, int amount) {
        purchases[productNum] += amount;

    }

    public void printCart() {
        int sum = 0;
        System.out.println("Ваша корзина:");
        for (int i = 0; i < products.length; i++) {
            sum += price[i] * purchases[i];
            if (purchases[i] > 0)
                System.out.println(products[i] + " " + purchases[i] + " шт " + price[i] + " руб/шт " + price[i] * purchases[i] + " руб в сумме");
        }
        System.out.println("Итого: " + sum + " руб");
    }

    public void saveTxt(File textFile) {
        JSONObject obj = new JSONObject();
        try (FileWriter out = new FileWriter(textFile)) {
            for (int i = 0; i < products.length; i++) {
                obj.put(products[i], purchases[i]);
            }
            out.write(obj.toJSONString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        int[] pu = {0, 0, 0};
        int[] p = {0, 0, 0};
        String[] products = new String[3];
        JSONParser parser = new JSONParser();
        if (!textFile.exists()) {
            return new Basket(p, products, p);
        }
        //       try (InputStream in = new FileInputStream(textFile)) {
        try {
            Object obj = parser.parse(new FileReader(textFile));
            String st = obj.toString().substring(1, obj.toString().length() - 1);
            String[] s = st.split(",");
            int i = 0;
            for (String a : s) {
                String[] c = a.split(":");
                p[i] = Integer.parseInt(c[1]);
                i++;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return new Basket(pu, products, p);
    }
}
