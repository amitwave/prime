package com.natwest.prime.service;

import com.natwest.prime.primenumberservice.PrimeNumberServiceDefault;
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
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestPrimeService {


    @Mock
    PrimeNumberServiceDefault primeNumberServiceDefault;

    @InjectMocks
    private PrimeServiceImpl primeService;

    @BeforeEach
    public void setUp(){
        reset(primeNumberServiceDefault);
    }

    @Test
    public void shouldReturnListOfPrimeNumbersForALimit(){
        when(primeNumberServiceDefault.isPrime(2L)).thenReturn(true);
        when(primeNumberServiceDefault.isPrime(3L)).thenReturn(true);
        when(primeNumberServiceDefault.isPrime(4L)).thenReturn(false);


        List<Long> primes = primeService.getPrimes(4L);

        assertEquals("Unexpected size of response", 2, primes.size());
        assertEquals("Unexpected list of primes", Arrays.asList(2L,3L), primes);

        verify(primeNumberServiceDefault, times(1)).isPrime(2L);
        verify(primeNumberServiceDefault, times(1)).isPrime(3L);
        verify(primeNumberServiceDefault, times(1)).isPrime(4L);
        verifyNoMoreInteractions(primeNumberServiceDefault);

    }
}
