package com.carcha.graphql.repository;

import com.carcha.graphql.domain.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Pokemon JPA Repository
 */
@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
}
