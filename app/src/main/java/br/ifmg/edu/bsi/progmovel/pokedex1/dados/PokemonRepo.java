package br.ifmg.edu.bsi.progmovel.pokedex1.dados;

import java.io.IOException;

import br.ifmg.edu.bsi.progmovel.pokedex1.apimodel.Evolution.PokemonEvolution;
import br.ifmg.edu.bsi.progmovel.pokedex1.apimodel.Pokeapi;
import br.ifmg.edu.bsi.progmovel.pokedex1.apimodel.Pokemon.Pokemon;
import br.ifmg.edu.bsi.progmovel.pokedex1.apimodel.Species.PokemonSpecies;

public class PokemonRepo {

    private Pokeapi api;

    public PokemonRepo(Pokeapi api) {
        this.api = api;
    }

    public Pokemon buscar(String nome) throws IOException {
        Pokemon p = api.fetch(nome).execute().body();
        return p;
    }

    public PokemonSpecies buscaEspecie(int id_especie) throws IOException {
        return api.buscaEspecie(id_especie).execute().body();
    }

    public PokemonEvolution buscaEvolucao(int id_evolucao) throws IOException {
        return api.buscaEvolucao(id_evolucao).execute().body();
    }
}
