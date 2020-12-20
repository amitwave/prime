package com.natwest.prime.apache.primenumberservice;


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

    PrimeNumberServiceApache primeNumberServiceApache;

    @BeforeEach
    public void setUp(){
        primeNumberServiceApache = new PrimeNumberServiceApacheImpl();
    }

    @ParameterizedTest
    @MethodSource
    public void shouldReturnPrimeStatusForANumber(Integer number, boolean status){

        assertEquals(status, primeNumberServiceApache.isPrime(number));

    }

    private static Stream<Arguments> shouldReturnPrimeStatusForANumber() {
        return Stream.of(
                Arguments.of(11, true),
                Arguments.of(2, true),
                Arguments.of(9, false))
                ;
    }
}
