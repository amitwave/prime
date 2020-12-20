package com.natwest.prime.primenumberservice;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestPrimeNumberService {

    PrimeNumberServiceDefault primeNumberServiceDefault;

    @BeforeEach
    public void setUp(){
        primeNumberServiceDefault = new PrimeNumberServiceDefaultDefaultImpl();
    }

    @ParameterizedTest
    @MethodSource
    public void shouldReturnPrimeStatusForANumber(Long number, boolean status){

        assertEquals(status, primeNumberServiceDefault.isPrime(number));

    }

    private static Stream<Arguments> shouldReturnPrimeStatusForANumber() {
        return Stream.of(
                Arguments.of(11L, true),
                Arguments.of(2L, true),
                Arguments.of(9L, false))
                ;
    }
}
