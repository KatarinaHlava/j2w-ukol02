package cz.czechitas.java2webapps.ukol2.controllers;

import org.eclipse.jetty.websocket.client.masks.RandomMasker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 @author KatkaHlava
 */

@Controller
public class QuoteController {

    private final Random random;
    private List<String> quoteList;

    public QuoteController() {
        random = new Random();

    }

    private static List<String> readAllLines(String resource)throws IOException{
        //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();

        //Pomocí metody getResourceAsStream() získáme z classloaderu InpuStream, který čte z příslušného souboru.
        //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
        try(InputStream inputStream=classLoader.getResourceAsStream(resource);
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,StandardCharsets.UTF_8))){

            //Metoda lines() vrací stream řádků ze souboru. Pomocí kolektoru převedeme Stream<String> na List<String>.
            return reader.lines().collect(Collectors.toList());
        }
    }



        @GetMapping("/")

    public ModelAndView quoteGenerator() throws IOException {
        ModelAndView result  = new ModelAndView("citaty");
        int nahodnecislo = random.nextInt(13)+1;
         result.addObject("obrazek", String.format("/images/photo-%d.jpg",nahodnecislo));

        List<String> quoteList =new ArrayList<>();
        quoteList= readAllLines("citaty.txt");
        int nahodneCislo2 =random.nextInt(quoteList.size());
        result.addObject("citace", quoteList.get(nahodneCislo2));

        return result;
    }

}
