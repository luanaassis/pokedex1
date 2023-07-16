package br.ifmg.edu.bsi.progmovel.pokedex1.apimodel;

import br.ifmg.edu.bsi.progmovel.pokedex1.apimodel.Evolution.PokemonEvolution;
import br.ifmg.edu.bsi.progmovel.pokedex1.apimodel.Pokemon.Pokemon;
import br.ifmg.edu.bsi.progmovel.pokedex1.apimodel.Species.PokemonSpecies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Pokeapi {
    @GET("pokemon/{name}")
    Call<Pokemon> fetch(@Path("name") String name);

    @GET("pokemon-species/{id}")
    Call<PokemonSpecies> buscaEspecie(@Path("id") int id_especie);
    @GET("evolution-chain/{id}")
    Call<PokemonEvolution> buscaEvolucao(@Path("id") int id_evolucao);
}
