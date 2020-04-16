# pokedex

Springboot GraphQL implementation example.

## GraphQL Schema
GraphQL schema is generated using annotations instead of the regular .graphqls schema. For that purpose, some annotations must be added in the domain and in the service layer:

### Domain layer
Type classes generations is done by the Schema Generation (we will see later), and for that purpose we need to specify in the class entity which fields will be used for the queries. This is done using the @GraphQLQuery annotation:


```
      @Column
      @GraphQLQuery(name = "name", description = "Pokemon's name")
      private String name;
      @Column
      @GraphQLQuery(name = "pokemonType", description = "Pokemon's pokemonType")
      private PokemonType pokemonType;
```
    
### Service layer
Same as the Type classes, Query and Mutations are generated using the following annotations:

  -  @GraphQLQuery for QueryResolver
  -  @GraphQLMutation for MutationResolver
  
For both is necessary to specify the name:

```
    @GraphQLQuery(name = "fetchAllPokemon")
    public List<Pokemon> fetchAllPokemon() {
        return pokemonRepository.findAll();
    }

    @GraphQLMutation(name = "addPokemon")
    public Pokemon addPokemon(@GraphQLArgument(name = "pokemon") Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }
```

With this configuration we can get the following result:


```
GraphQL query

    {
      fetchAllPokemon {
        id
        name
        pokemonType
      }
    }
```

```
Query result

    {
      "data": {
        "fetchAllPokemon": [
          {
            "id": 1,
            "name": "Squirtle",
            "pokemonType": "water"
          }
        ]
      }
    }
```
### Schema generation
 GraphQL schema generation is done under GraphQlController constructor:
 
 ```
 private final GraphQL graphQL;
  
      public GraphQlController(PokemonService pokemonService) {
  
          GraphQLSchema schema = new GraphQLSchemaGenerator()
                  .withResolverBuilders(
                          new AnnotatedResolverBuilder())
                  .withOperationsFromSingleton(pokemonService)
                  .withValueMapperFactory(new JacksonValueMapperFactory())
                  .generate();
          this.graphQL = GraphQL.newGraphQL(schema).build();
      }
 ```

### GraphiQL

GraphiQL is also include to test GraphQL queries, using GraphiQL Spring Boot Starter:

```
 <dependency>
     <groupId>com.graphql-java</groupId>
     <artifactId>graphiql-spring-boot-starter</artifactId>
     <version>5.0.2</version>
 </dependency>
 ```

