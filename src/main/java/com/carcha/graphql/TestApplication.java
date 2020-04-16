package com.carcha.graphql;

import com.carcha.graphql.domain.Pokemon;
import com.carcha.graphql.domain.PokemonType;
import com.carcha.graphql.repository.PokemonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	@Bean
	public CommandLineRunner fillDatabase(PokemonRepository pokemonRepository) {
		return (args) -> {
			for (int i = 0; i < 50;  i++) {
				pokemonRepository.save(Pokemon.builder()
				.name("Pokemon"+i)
				.pokemonType(PokemonType.values()[new Random().nextInt(3)])
				.build());
			}
		};
	}

}
