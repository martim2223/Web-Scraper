package org.example;

import com.opencsv.CSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.owasp.encoder.Encode;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.jsoup.select.Elements;;


public class Main {
    public static void main(String[] args) throws IOException {

        String url = "https://lista.mercadolivre.com.br/jacuzzi#D[A:jacuzzi]";

        try {
            Document document = Jsoup.connect(url).get();

            System.out.println("\n" + document.title() + "\n");

            Elements body = document.select(".ui-search-result__wrapper.shops__result-wrapper");

            List<String[]> data = new ArrayList<String[]>();


            ArrayList<String> Order = new ArrayList<>();
            ArrayList<String> Title = new ArrayList<>();
            ArrayList<String> prodLink = new ArrayList<>();
            ArrayList<String> urlImage = new ArrayList<>();
            ArrayList<String> Description = new ArrayList<>();
            ArrayList<String> Price = new ArrayList<>();


            getOrderInPage(Order);
            getTitle(body,Title);
            getProdLink(body, prodLink);
            getImageUrl(prodLink, urlImage);
            getDescription(prodLink, Description);
            getPrice(body, Price);

            printAllProducts(Order, Title, prodLink, urlImage, Description, Price);

            addCSVFile(data, Order, Title, prodLink, urlImage, Description, Price);

            }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private static void getOrderInPage(ArrayList<String> Order) {
        for(int i=0; i<10; i++){
            String id = String.valueOf(i+1);
            Order.add(id);
        }
    }
    private static void getTitle(Elements body, ArrayList<String> Title) {
        for(int i=0; i<10; i++) {
            String title = body.get(i).select(".ui-search-item__title.ui-search-item__group__element.shops__items-group-details.shops__item-title").text();
            Title.add(title);
        }
    }

    private static void getProdLink(Elements body, ArrayList<String> prodLink) {
        for(int i=0; i<10; i++){
            String link = Encode.forJava(body.get(i).select("a").attr("href"));
            prodLink.add(link);
        }
    }

    private static void getImageUrl(ArrayList<String> prodLink, ArrayList<String> urlImage) throws IOException {
        for(int i=0; i< prodLink.size(); i++){
            String url2 = prodLink.get(i);
            Document document2 = Jsoup.connect(url2).get();

            String img_url = document2.select(".ui-pdp-gallery__figure").select("img").attr("src");

            urlImage.add(img_url);
        }
    }

    private static void getDescription(ArrayList<String> prodLink, ArrayList<String> Description) throws IOException, InterruptedException {
        for(int i=0; i < 10; i++){
            String url2 = prodLink.get(i);
            Document document2 = Jsoup.connect(url2).get();
            Thread.sleep(1000);
            String description = document2.select(".ui-pdp-description__content").select("p").text();
            Description.add(description);
        }
    }

    private static void getPrice(Elements body,ArrayList<String> Price) {
        for(int i=0; i<10; i++){
            String price = body.get(i).select(".price-tag-fraction").first().text();
            Price.add(price);
        }
    }

    private static void printAllProducts(ArrayList<String> Order, ArrayList<String> Title, ArrayList<String> prodLink, ArrayList<String> urlImage, ArrayList<String> Description, ArrayList<String> Price) {
        for(int i=0; i<10; i++){
            System.out.println("Order in page: " + Order.get(i));
            System.out.println("Title: " + Title.get(i));
            System.out.println("Product Link: " + prodLink.get(i));
            System.out.println("Url Image: " + urlImage.get(i));
            System.out.println("Description: " + Description.get(i));
            System.out.println("Price: " + Price.get(i));
            System.out.println("---------------------------------------");
        }
    }

    private static void addCSVFile(List<String[]> data, ArrayList<String> Order, ArrayList<String> Title, ArrayList<String> prodLink, ArrayList<String> urlImage, ArrayList<String> Description, ArrayList<String> Price) throws IOException {

        File file = new File("/tmp/CSVFile/products.csv");

        FileWriter outputfile = new FileWriter(file); // Create
        CSVWriter writer = new CSVWriter(outputfile);

        data.add(new String[]{"Order", "Name", "Product Link", "Image URL", "Description", "Price"});
        writer.writeAll(data);

        for(int i=0; i<10;i++){
            String order = Order.get(i);
            String title = Title.get(i);
            String link = prodLink.get(i);
            String image = urlImage.get(i);
            String description = Description.get(i);
            String price = Price.get(i);

            data.removeAll(data);
            data.add(new String[]{order, title, link, image, description, price});
            writer.writeAll(data);
        }

        writer.close();
    }

}
