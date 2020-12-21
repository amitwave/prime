package com.natwest.prime.resource;


import com.natwest.prime.service.PrimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PrimeNumberResourceTest {


    @Mock
    private List<PrimeService> primeServices;

    @InjectMocks
    private PrimeNumberResource primeNumberResource;

    @BeforeEach
    public void setUp(){
        reset(primeServices);
    }

    @Test
    public void shouldReturnListOfPrimeNumbersForALimit(){
        PrimeService primeService = mock(PrimeService.class);

        when(primeServices.stream()).thenReturn(Stream.of(primeService));
        when(primeService.getServiceType()).thenReturn("default");
        when(primeService.getPrimes(11L)).thenReturn(Arrays.asList(2L,3L,5L,7L,11L));

        ResponseEntity<PrimeNumberResponse> primeNumberResponseEntity = primeNumberResource.getPrimes(11L, "default");

        PrimeNumberResponse primeNumberResponse = primeNumberResponseEntity.getBody();
        assertEquals("Unexpected size of response", 5, primeNumberResponse.getPrimes().size());
        assertEquals("Unexpected list of primes", Arrays.asList(2L,3L,5L,7L,11L), primeNumberResponse.getPrimes());

        verify(primeService, times(1)).getServiceType();
        verify(primeService, times(1)).getPrimes(11L);
        verify(primeServices, times(1)).stream();

        verifyNoMoreInteractions(primeService);
        verifyNoMoreInteractions(primeServices);

    }

    @Test
    public void shouldThrowExcepthinWhenTypeIsUnrecognised(){
        PrimeService primeService = mock(PrimeService.class);

        when(primeServices.stream()).thenReturn(Stream.of(primeService));
        when(primeService.getServiceType()).thenReturn("default");

        assertThrows(RuntimeException.class, () -> primeNumberResource.getPrimes(11L, "invalid"));

        verify(primeServices, times(1)).stream();

        verifyNoMoreInteractions(primeServices);

    }

}
