package com.carcha.graphql.repository;

import com.carcha.graphql.domain.Pokemon;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;

    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @GraphQLQuery(name = "fetchAllPokemon")
    public List<Pokemon> fetchAllPokemon() {
        return pokemonRepository.findAll();
    }

    @GraphQLMutation(name = "addPokemon")
    public Pokemon addPokemon(@GraphQLArgument(name = "pokemon") Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    @GraphQLQuery(name = "fetchPokemonById")
    public Pokemon fetchPokemonById(@GraphQLArgument(name = "id") Long id) {
        return pokemonRepository.findById(id).orElse(null);
    }


}
