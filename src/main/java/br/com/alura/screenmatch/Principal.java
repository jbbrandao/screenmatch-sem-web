package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.Scanner;

public class Principal {

    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();


    public void exibeMenu(){

        System.out.println("Informe o nome da Serie a pesquisar");
        String busca = teclado.nextLine();

        String ENDPOINT = "https://www.omdbapi.com/?t=";
        String API_KEY = "&apikey=4fca7a09";
        String json = consumoApi.obterDados(ENDPOINT + busca.replace(" ", "+") + API_KEY);
        DadosSerie dadosSerie = conversor.converterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        for(int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            String SEASON = "&season=";
            json = consumoApi.obterDados(ENDPOINT + busca.replace(" ", "+")  + SEASON + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.converterDados(json, DadosTemporada.class);
            System.out.println("Temporada : " + i);
            for (int j = 0; j < dadosTemporada.episodios().size(); j++) {
                System.out.println(dadosTemporada.episodios().get(j));
            }
        }
    }
}
