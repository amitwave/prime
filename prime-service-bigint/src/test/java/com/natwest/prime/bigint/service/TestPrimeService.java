package com.natwest.prime.bigint.service;

import com.natwest.prime.service.exception.NumberNotInRangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestPrimeService {


    @InjectMocks
    private PrimeServiceBigintImpl primeService;

    @BeforeEach
    public void setUp(){
        primeService = new PrimeServiceBigintImpl();
    }

    @Test
    public void shouldReturnListOfPrimeNumbersForALimit(){


        List<Long> primes = primeService.getPrimes(4L);

        assertEquals("Unexpected size of response", 2, primes.size());
        assertEquals("Unexpected list of primes", Arrays.asList(2L,3L), primes);



    }


    @Test
    public void shouldThrowExceptionWhenNumberIsSmallerThan2(){
        assertThrows(NumberNotInRangeException.class, () -> primeService.getPrimes(1L));
    }

    @Test
    public void shouldThrowExceptionWhenNumberIslargerThan10000000(){
        assertThrows(NumberNotInRangeException.class, () -> primeService.getPrimes(10000001L));
    }
}
