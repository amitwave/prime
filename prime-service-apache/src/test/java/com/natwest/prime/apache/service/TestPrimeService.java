package com.natwest.prime.apache.service;


import com.natwest.prime.apache.primenumberservice.PrimeNumberServiceApache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestPrimeService {


    @Mock
    PrimeNumberServiceApache primeNumberServiceApache;

    @InjectMocks
    private PrimeServiceApacheImpl primeService;

    @BeforeEach
    public void setUp(){
        reset(primeNumberServiceApache);
    }

    @Test
    public void shouldReturnListOfPrimeNumbersForALimit(){
        when(primeNumberServiceApache.isPrime(7)).thenReturn(true);
        when(primeNumberServiceApache.isPrime(11)).thenReturn(true);

        List<Long> primes = primeService.getPrimes(11L);

        assertEquals("Unexpected size of response", 5, primes.size());
        assertEquals("Unexpected list of primes", Arrays.asList(2L,3L,5L,7L,11L), primes);

        verify(primeNumberServiceApache, times(1)).isPrime(7);
        verify(primeNumberServiceApache, times(1)).isPrime(11);
        verifyNoMoreInteractions(primeNumberServiceApache);

    }

    @ParameterizedTest
    @MethodSource
    public void shouldReturnListOfPrimeNumbersWhenNumberIsLessThan7(Long number, List<Long> expected){
        List<Long> primes = primeService.getPrimes(number);

        assertEquals("Unexpected size of response", expected.size(), primes.size());
        assertEquals("Unexpected list of primes", expected, primes);

        verifyNoMoreInteractions(primeNumberServiceApache);
    }

    private static Stream<Arguments> shouldReturnListOfPrimeNumbersWhenNumberIsLessThan7() {
        return Stream.of(
                Arguments.of(0L, Collections.EMPTY_LIST),
                Arguments.of(1L, Collections.EMPTY_LIST),
                Arguments.of(6L, Arrays.asList(2L,3L,5L)),
                Arguments.of(2L, Arrays.asList(2L)),
                Arguments.of(3L, Arrays.asList(2L,3L)),
                Arguments.of(5L, Arrays.asList(2L,3L,5L)))
                ;
    }

    @Test
    public void shouldTHrowExceptionWhenNumberIsLargerThanIntegerRange(){
        assertThrows(RuntimeException.class, () -> primeService.getPrimes(new Long(Integer.MAX_VALUE) +1));
    }

}
