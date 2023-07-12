package br.ifmg.edu.bsi.progmovel.pokedex1.apimodel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Pokeapi {
    @GET("pokemon/{name}")
    Call<Pokemon> fetch(@Path("name") String name);
}
