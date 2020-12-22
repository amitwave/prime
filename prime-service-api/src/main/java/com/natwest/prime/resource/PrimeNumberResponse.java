package com.natwest.prime.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@JsonRootName("PrimeNumbers")
public class PrimeNumberResponse {

    @JsonProperty("Initial")
    Long initial;

    @JsonProperty("Primes")
    List<Long> primes;



}
