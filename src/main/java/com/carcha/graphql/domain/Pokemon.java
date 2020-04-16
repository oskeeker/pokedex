package com.carcha.graphql.domain;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {
    @Id
    @GeneratedValue
    @GraphQLQuery(name = "id", description = "Pokemon's Id")
    private Long id;
    @Column
    @GraphQLQuery(name = "name", description = "Pokemon's name")
    private String name;
    @Column
    @GraphQLQuery(name = "pokemonType", description = "Pokemon's pokemonType")
    private PokemonType pokemonType;

}
