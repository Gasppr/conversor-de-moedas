package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class Conversao {

 public void converter(){


     Scanner sc = new Scanner(System.in);
     Cambio cambio;

     int opBase , opTarget;
     int op = 1;
     ArrayList<Cambio> cambios = new ArrayList<>();

     while (op != 0){

        String base, target;
        double valor;


        System.out.println("Bem-vindo ao conversor Alura + Oracle");
        System.out.println("***********************************************************");

        System.out.println("Qual é a sua moeda?");
        // pelo menos 6 tipos de moedas
        System.out.println("""
                1 - BRL (Moeda Brasileira)
                2 - USD (Moeda Estadunidense)
                3 - EUR (Moeda Europeia)
                4 - CNY (Moeda Chinesa)
                5 - RUB (Moeda Russa)
                6 - ARS (Moeda Argentina)
                7 - Outro
                """);
        opBase = sc.nextInt();
        if (opBase == 1) base = "BRL";
        else if (opBase == 2) base = "USD";
        else if (opBase == 3) base = "EUR";
        else if (opBase == 4) base = "CNY";
        else if (opBase == 5) base = "RUB";
        else if (opBase == 6) base = "ARS";
        else {
            System.out.println("Digite a abreviação da moeda que deseja");
            base = sc.next().toUpperCase();
        }

        System.out.println("Para qual deseja converter?");
        System.out.println("""
                1 - BRL (moeda Brasileira)
                2 - USD (moeda Estadunidense)
                3 - EUR (moeda Europeia)
                4 - CNY (Moeda Chinesa)
                5 - RUB (Moeda Russa)
                6 - ARS (Moeda Argentina)
                7 - Outro
                """);
        opTarget = sc.nextInt();
        if (opTarget == 1) target = "BRL";
        else if (opTarget == 2) target = "USD";
        else if (opTarget == 3) target = "USD";
        else if (opTarget == 4) target = "CNY";
        else if (opTarget == 5) target = "RUB";
        else if (opTarget == 6) target = "ARS";
        else {
            System.out.println("Digite a abreviação da moeda que deseja");
            target = sc.next().toUpperCase();
        }

        System.out.println("Qual é o valor?");
        valor = sc.nextDouble();

        cambio = conversor(base , target, valor);

        System.out.println(cambio.toString());
        cambios.add(cambio);

        System.out.println("**************************************************************");
        System.out.println("Deseja continuar a converter mais um valor? \n 1- CONTINUAR  \n 0- SAIR");
        op = sc.nextInt();

    }

     salvarHistorico(cambios);
     System.out.println("*******************************************************");
     System.out.println("Até a próxima!");

 }

public Cambio conversor(String base , String target , double valor){

    URI url = URI.create("https://v6.exchangerate-api.com/v6/"+ System.getenv("API_KEY") +"/pair/"+ base +"/"+target+"/" + valor);
    HttpRequest request = HttpRequest.newBuilder().uri(url).build();

    try {
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(response.body(), Cambio.class);

    } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
    }

 }

 public void salvarHistorico(ArrayList<Cambio> cambios){

     Gson gson = new GsonBuilder().setPrettyPrinting().create();

     File historico = new File("historico.json");
     try {
         FileWriter escrita = new FileWriter(historico);
         for(Cambio cambio : cambios){
             escrita.write(gson.toJson(cambio));

         }
         escrita.close();
     } catch (IOException e) {
         throw new RuntimeException(e);
     }

 }

}
