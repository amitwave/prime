package com.natwest.prime.primenumberservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.stream.LongStream;

@Slf4j
@Service("primaryNumberServiceDefault")
public class PrimeNumberServiceDefaultDefaultImpl implements PrimeNumberServiceDefault {

    @Cacheable("primeNumber")
    public Boolean isPrime(Long number) {
        boolean isPrime = LongStream.rangeClosed(2, (long) (Math.sqrt(number)))
                .parallel()
                .noneMatch(i -> number % i == 0);
        return isPrime;
    }

}
