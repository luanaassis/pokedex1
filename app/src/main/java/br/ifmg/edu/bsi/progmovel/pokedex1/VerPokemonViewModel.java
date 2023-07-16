package br.ifmg.edu.bsi.progmovel.pokedex1;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.io.IOException;

import br.ifmg.edu.bsi.progmovel.pokedex1.apimodel.Evolution.Evolution;
import br.ifmg.edu.bsi.progmovel.pokedex1.apimodel.Evolution.PokemonEvolution;
import br.ifmg.edu.bsi.progmovel.pokedex1.apimodel.Pokemon.Pokemon;
import br.ifmg.edu.bsi.progmovel.pokedex1.apimodel.Species.PokemonSpecies;

public class VerPokemonViewModel extends ViewModel {
    private PokedexApplication app;
    private MutableLiveData<Integer> loading;
    private MutableLiveData<String> nome;
    private MutableLiveData<String> urlImagem;
    private MutableLiveData<Integer> height;
    private MutableLiveData<Integer> weight;

    private MutableLiveData<String> evolucoes;

    public static ViewModelInitializer<VerPokemonViewModel> initializer = new ViewModelInitializer<>(
            VerPokemonViewModel.class,
            creationExtras -> new VerPokemonViewModel((PokedexApplication) creationExtras.get(APPLICATION_KEY)));
    public VerPokemonViewModel(PokedexApplication app) {
        this.app = app;
        loading = new MutableLiveData<>(View.GONE);
        nome = new MutableLiveData<>();
        urlImagem = new MutableLiveData<>();
        height = new MutableLiveData<>();
        weight = new MutableLiveData<>();
        evolucoes =  new MutableLiveData<>();
    }

    public void loadPokemon(String nomePokemon) {
        loading.setValue(View.VISIBLE);
        app.getExecutor().execute(() -> {

            try {
                Pokemon p = app.getPokemonRepo().buscar(nomePokemon);
                nome.postValue(p.name);
                height.postValue(p.height);
                weight.postValue(p.weight);
                urlImagem.postValue(p.sprites.other.officialArtwork.front_default);
                String[] urlPokemon = p.species.url.split("/");
                int idEspecie = Integer.parseInt(urlPokemon[urlPokemon.length - 1]);
                Log.d("Especie", String.valueOf(idEspecie));
                PokemonSpecies especiePokemon = app.getPokemonRepo().buscaEspecie(idEspecie);
                String[] urlEspecie = especiePokemon.evolutionChain.url.split("/");
                int idEvolucao = Integer.parseInt(urlEspecie[urlEspecie.length - 1]);
                Log.d("Evolucao", String.valueOf(idEvolucao));
                PokemonEvolution evolucaoPokemon = app.getPokemonRepo().buscaEvolucao(idEvolucao);
                Evolution[] evo = evolucaoPokemon.chain.evolves_to;
                String nomesEvoluções = "";
                for (int i =0; i<evo.length;i++) {
                    nomesEvoluções+=("- "+evo[i].species.name + "\n");
                    for(Evolution evo2 : evo[i].evolves_to){
                        nomesEvoluções+=("- "+ evo2.species.name + "\n");
                    }
                }
                Log.d("Nome evolucao",nomesEvoluções);
                evolucoes.postValue(nomesEvoluções);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                loading.postValue(View.GONE);
            }
        });
    }

    public LiveData<Integer> getLoading() {
        return loading;
    }

    public LiveData<String> getNome() {
        return nome;
    }

    public LiveData<String> getUrlImagem() {
        return urlImagem;
    }

    public LiveData<Integer> getHeight() {
        return height;
    }

    public LiveData<Integer> getWeight() {
        return weight;
    }

    public LiveData<String> getEvolucoes() {
        return evolucoes;
    }

}
