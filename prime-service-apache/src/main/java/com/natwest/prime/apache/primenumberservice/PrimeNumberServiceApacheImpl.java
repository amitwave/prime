package com.natwest.prime.apache.primenumberservice;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.primes.Primes;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service("primaryNumberServiceApache")
public class PrimeNumberServiceApacheImpl implements PrimeNumberServiceApache {

    @Cacheable("primeNumberApache")
    public Boolean isPrime(Integer number) {
        return Primes.isPrime(number);
    }

}
