# Pokedex

Springboot GrapqhQL annotation based implementation, Apollo Client for React GrapqhQL implementation.

## Springboot

### GraphQL Schema
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

GraphiQL is also included to test GraphQL queries, using GraphiQL Spring Boot Starter:

```
 <dependency>
     <groupId>com.graphql-java</groupId>
     <artifactId>graphiql-spring-boot-starter</artifactId>
     <version>5.0.2</version>
 </dependency>
 ```

To access web-based version, by default GraphiQL endpoint is configured as /graphiql

```
 http://localhost:38080/graphiql
 ```

## React
### Apollo client
###### From Docu
Apollo Client is a complete state management library for JavaScript apps. Simply write a GraphQL query, and Apollo Client will take care of requesting and caching your data, as well as updating your UI.
```
import ApolloClient, { gql } from "apollo-boost";
```

### Apollo provider
###### From Docu
React-Apollo includes a component for providing a client instance to a React component tree, and a higher-order component for retrieving that client instance.

```
import { ApolloProvider } from 'react-apollo';
```

The <ApolloProvider/> component takes the following props:

client: The required ApolloClient instance. This ApolloClient instance will be used by all of your components enhanced with GraphQL capabilties.

In case of the current application, the uri is set as follows:
```
const client = new ApolloClient({
  uri: "http://localhost:38080/graphql"
});
```

Query example:
```
const PokemonQuery = () => {
  return (
    <Query
      query={gql`
        {
          fetchAllPokemon {
            id
            name
            pokemonType
          }
        }
      `}
    >
      {({ loading, error, data }) => {
        if (loading) return <p>Loading...</p>;
        if (error) return <p>Error!</p>;

        return data.fetchAllPokemon.map(Pokemon => {
          return (
            <p key={Pokemon.id}>
              {Pokemon.id}, {Pokemon.name}, Type: {Pokemon.pokemonType}
            </p>
          );
        });
      }}
    </Query>
  );
};

```

## Run App

To run the app, just run Springboot server.

Run following commands on React folder:

```
npm install (first installation only)
npm run
```

Once app is started it can be available on http://localhost:3000/ where initial pokemon query is shown.
