package com.carcha.graphql.controller;

import com.carcha.graphql.domain.QueryDTO;
import com.carcha.graphql.service.PokemonService;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Pokedex controller.
 *
 * This controller has the the GraphQL Schema generation in the constructor, and also define the GraphQL endpoint
 * with the execution of the query.
 */
@RestController
public class GraphQlController {

    private final GraphQL graphQL;

    /**
     * Constructor with the GraphQL schema generation.
     * @param pokemonService service layer
     */
    public GraphQlController(PokemonService pokemonService) {

        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withResolverBuilders(
                        new AnnotatedResolverBuilder())
                .withOperationsFromSingleton(pokemonService)
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .generate();
        this.graphQL = GraphQL.newGraphQL(schema).build();
    }


//    @PostMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public Map<String, Object> graphQl(@RequestBody Map<String, String> request, HttpServletRequest raw) {
//        ExecutionResult executionResult = this.graphQL.execute(ExecutionInput.newExecutionInput()
//                .query(request.get("query"))
//                .operationName(request.get("operationName"))
//                .context(raw)
//                .build());
//        return executionResult.toSpecification();
//    }
    @PostMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> graphQl(@RequestBody QueryDTO queryDTO, HttpServletRequest raw) {
        ExecutionResult executionResult = this.graphQL.execute(ExecutionInput.newExecutionInput()
                .query(queryDTO.getQuery())
                .operationName(queryDTO.getOperationName())
                .context(raw)
                .build());
        return executionResult.toSpecification();
    }

}
