import java.io.*;
import java.io.ObjectOutputStream;

public class Basket implements Serializable {
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

    public void saveBin(File file, Basket basket) {

        try (
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        ) {
            out.writeObject(basket);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Basket loadFromBinFile(File file) {
        int[] p = {0, 0, 0};
        String[] products = new String[3];
        if (!file.exists()) {
            return new Basket(p, products, p);
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (Basket) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Basket(p, products, p);
    }
}
