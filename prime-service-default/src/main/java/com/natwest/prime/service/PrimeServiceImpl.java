package com.natwest.prime.service;

import com.natwest.prime.primenumberservice.PrimeNumberServiceDefault;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Slf4j
@Service
public class PrimeServiceImpl implements PrimeService{

    @Autowired
    private PrimeNumberServiceDefault service;

    @Override
    public List<Long> getPrimes(Long number) {
        log.info("Primes for {}", number);
        List<Long> primes = new ArrayList<>();
        if(number <= 1) {
            return primes;
        }

        List<Long> primeList =  LongStream.rangeClosed(2, number)
                .parallel()
                .filter(service::isPrime)
                .boxed()
                .collect(Collectors.toList());

        primes.addAll(primeList);
        return primes;
    }


    @Override
    public String getServiceType() {
        return "default";
    }
}
