package com.carcha.graphql.domain;

import lombok.Data;

@Data
public class QueryDTO {
    private String query;
    private Object variables;
    private String operationName;
}
