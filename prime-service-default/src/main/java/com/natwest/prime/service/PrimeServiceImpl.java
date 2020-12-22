package com.natwest.prime.service;

import com.natwest.prime.primenumberservice.PrimeNumberServiceDefault;
import com.natwest.prime.service.exception.NumberNotInRangeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        if(number <=1) {
            throw new NumberNotInRangeException("Number should be at least 2");
        }

        if(number > 100000L) {
            throw new NumberNotInRangeException("Number too large. Maximum value of 100000 is supported");
        }

        List<Long> primes =  LongStream.rangeClosed(2, number)
                .parallel()
                .filter(service::isPrime)
                .boxed()
                .collect(Collectors.toList());

        return primes;
    }


    @Override
    public String getServiceType() {
        return "default";
    }
}
