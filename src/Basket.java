import java.io.*;
import java.util.Collections;
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
        String data = "";
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (int i = 0; i < products.length; i++) {
                data += purchases[i] + " ";
            }
            out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        int[] p = {0, 0, 0};
        String[] products = new String[3];

        if (!textFile.exists()) {
            return new Basket(p, products, p);
        }
        try (InputStream in = new FileInputStream(textFile)) {
            Scanner scanner = new Scanner(in);
            String[] data = scanner.nextLine().trim().split(" ");
            for (int i = 0; i < 3; i++) {
                p[i] = Integer.parseInt(data[i]);
            }
            return new Basket(p, products, p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Basket(p, products, p);
    }
}
