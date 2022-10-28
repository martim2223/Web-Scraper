import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.owasp.encoder.Encode;

import java.io.IOException;

import static org.junit.Assert.*;

public class ExamplesTest {

    @Test
    public void checkUrlProduct() throws IOException {
        String url = "https://lista.mercadolivre.com.br/jacuzzi#D[A:jacuzzi]";
        Document document = Jsoup.connect(url).get();
        Elements body = document.select(".ui-search-result__wrapper.shops__result-wrapper");
        for(int i=0; i<10; i++){
            String link = Encode.forJava(body.get(i).select("a").attr("href"));
            assertTrue(link.contains("https://"));
        }
    }

    @Test
    public void checkTitle() throws IOException {
        String url = "https://lista.mercadolivre.com.br/jacuzzi#D[A:jacuzzi]";
        Document document = Jsoup.connect(url).get();
        assertEquals("Jacuzzi | MercadoLivre \uD83D\uDCE6", document.title());
    }
}
