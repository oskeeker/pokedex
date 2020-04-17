import React from "react";
import ReactDOM from "react-dom";
import "./styles.css";
import ApolloClient, { gql } from "apollo-boost";
import { ApolloProvider, Query } from "react-apollo";

const client = new ApolloClient({
  uri: "http://localhost:38080/graphql"
});

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

function App() {
  return (
    <div className="App">
      <PokemonQuery />
    </div>
  );
}

const rootElement = document.getElementById("root");
ReactDOM.render(
  <ApolloProvider client={client}>
    <App />
  </ApolloProvider>,
  rootElement
);
