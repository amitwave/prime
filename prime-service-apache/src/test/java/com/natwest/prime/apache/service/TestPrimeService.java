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
        when(primeNumberServiceApache.isPrime(2)).thenReturn(true);
        when(primeNumberServiceApache.isPrime(3)).thenReturn(true);
        when(primeNumberServiceApache.isPrime(4)).thenReturn(false);


        List<Long> primes = primeService.getPrimes(4L);

        assertEquals("Unexpected size of response", 2, primes.size());
        assertEquals("Unexpected list of primes", Arrays.asList(2L,3L), primes);

        verify(primeNumberServiceApache, times(1)).isPrime(2);
        verify(primeNumberServiceApache, times(1)).isPrime(3);
        verify(primeNumberServiceApache, times(1)).isPrime(4);
        verifyNoMoreInteractions(primeNumberServiceApache);

    }

    @Test
    public void shouldTHrowExceptionWhenNumberIsLargerThanIntegerRange(){
        assertThrows(RuntimeException.class, () -> primeService.getPrimes(new Long(Integer.MAX_VALUE) +1));
    }

}
