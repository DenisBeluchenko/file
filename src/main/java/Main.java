import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        ClientLog client = new ClientLog();
        Config config = new Config();
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] price = {100, 200, 300};
        int[] p = {0, 0, 0};
        Basket basket = new Basket(price, products, p);
        if (config.isLoad()) {
            if (config.getLoadType().equals("json")) {
                basket.setPurchases(Basket.loadJson(new File(config.getLoadFileName())).getPurchases());
            } else {
                basket.setPurchases(Basket.loadFromTxtFile(new File(config.getLoadFileName())).getPurchases());
            }
        }
        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < price.length; i++) {
            System.out.println(i + 1 + ". " + products[i] + " " + price[i] + "руб/шт");
        }
        while (true) {
            int productNumber = 0;
            int productCount = 0;
            System.out.println("Выберите товар и количество или введите `end`");
            String inputString = scanner.nextLine();
            if (inputString.equals("end")) {
                break;
            }
            String[] parts = inputString.split(" ");
            if (parts.length != 2) {
                System.out.println("Должно быть 2 числа через пробел или end");
                continue;
            }
            try {
                productNumber = Integer.parseInt(parts[0]);
                productCount = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Должны быть числа, попробуйте ещё раз");
            }
            if (productNumber < 1 | productNumber > 3) {
                System.out.println("В наличии товар от 1-3, попробуйте ещё раз");
                continue;
            }
            if (productCount < 1) {
                System.out.println("Количество тавара должго быть больше 0, попробуйте ещё раз");
                continue;
            }
            basket.addToCart(productNumber - 1, productCount);
            client.log(productNumber, productCount);
            if (config.isSave()) {
                if (config.getSaveType().equals("json")) {
                    basket.saveJson(new File(config.getSaveFileName()));
                } else {
                    basket.saveTxt(new File(config.getSaveFileName()));
                }
            }
        }
        if (config.isLog()) {
            client.exportAsCSV(new File(config.getLogFileName()));
        }
        basket.printCart();
    }
}
