package com.natwest.prime.apache.service;

import com.natwest.prime.apache.primenumberservice.PrimeNumberServiceApache;
import com.natwest.prime.service.PrimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service("primaryServiceApache")
public class PrimeServiceApacheImpl implements PrimeService{

    @Autowired
    private PrimeNumberServiceApache primeNumberServiceApache;

    @Override
    public List<Long> getPrimes(Long number) {
        log.info("Primes for {}", number);

        if(number > Integer.MAX_VALUE) {
            throw new RuntimeException("Number too large.");
        }
        List<Long> primes = new ArrayList<>();
        if(number <= 1) {
            return primes;
        }

        List<Long> primeList = IntStream.rangeClosed(2, Math.toIntExact(number))
                    .parallel()
                    .filter(primeNumberServiceApache::isPrime)
                    .asLongStream()
                    .boxed()
                    .collect(Collectors.toList());

        primes.addAll(primeList);
        return primes;
    }

    @Override
    public String getServiceType() {
        return "apache";
    }
}
