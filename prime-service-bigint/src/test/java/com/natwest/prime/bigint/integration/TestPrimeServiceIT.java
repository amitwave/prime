package com.natwest.prime.bigint.integration;


import com.natwest.prime.service.PrimeService;
import com.natwest.prime.service.exception.NumberNotInRangeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@ComponentScan({"com.natwest.prime.bigint"})
@EnableAutoConfiguration
@SpringBootConfiguration
public class TestPrimeServiceIT {

    @Autowired
    private PrimeService primeService;


    @Test
    public void shouldReturnListOfPrimeNumbersForALimit(){


        List<Long> primes = primeService.getPrimes(11L);

        assertEquals("Unexpected size of response", 5, primes.size());
        assertEquals("Unexpected list of primes", Arrays.asList(2L,3L,5L,7L,11L), primes);



    }

    @ParameterizedTest
    @MethodSource
    public void shouldReturnListOfPrimeNumbersWhenNumberIsLessThan7(Long number, List<Long> expected){
        List<Long> primes = primeService.getPrimes(number);

        assertEquals("Unexpected size of response", expected.size(), primes.size());
        assertEquals("Unexpected list of primes", expected, primes);


    }

    private static Stream<Arguments> shouldReturnListOfPrimeNumbersWhenNumberIsLessThan7() {
        return Stream.of(
                Arguments.of(6L, Arrays.asList(2L,3L,5L)),
                Arguments.of(2L, Arrays.asList(2L)),
                Arguments.of(3L, Arrays.asList(2L,3L)),
                Arguments.of(5L, Arrays.asList(2L,3L,5L)),
                Arguments.of(11L, Arrays.asList(2L,3L,5L,7L,11L)))
                ;
    }

    @Test
    public void shouldThrowExceptionWhenNumberIsSmallerThan2(){
        assertThrows(NumberNotInRangeException.class, () -> primeService.getPrimes(1L));
    }
}
