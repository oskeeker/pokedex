package com.carcha.graphql.service;

import com.carcha.graphql.domain.Pokemon;
import com.carcha.graphql.repository.PokemonRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service with the GraphQL Query and Mutation definition.
 */
@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;

    /**
     * Constructor injection
     * @param pokemonRepository repo
     */
    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    /**
     * Fetch all pokemon GraphQL Query
     * @return ArrayList of pokemon
     */
    @GraphQLQuery(name = "fetchAllPokemon")
    public List<Pokemon> fetchAllPokemon() {
        return pokemonRepository.findAll();
    }

    /**
     * Add pokemon GraphQL Mutation
     * @param pokemon entity
     * @return
     */
    @GraphQLMutation(name = "addPokemon")
    public Pokemon addPokemon(@GraphQLArgument(name = "pokemon") Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    /**
     * Fetch pokemon by id GraphQL Query
     * @param id pokemon id
     * @return Pokemon entity
     */
    @GraphQLQuery(name = "fetchPokemonById")
    public Pokemon fetchPokemonById(@GraphQLArgument(name = "id") Long id) {
        return pokemonRepository.findById(id).orElse(null);
    }


}
