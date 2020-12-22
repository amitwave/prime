package com.natwest.prime.apache.service;

import com.natwest.prime.apache.primenumberservice.PrimeNumberServiceApache;
import com.natwest.prime.service.PrimeService;
import com.natwest.prime.service.exception.NumberNotInRangeException;
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

        if(number <=1) {
            throw new NumberNotInRangeException("Number should be at least 2");
        }
        if(number > 500000) {
            throw new NumberNotInRangeException("Number should be less than or equal to 500000");
        }

        return IntStream.rangeClosed(2, Math.toIntExact(number))
                    .parallel()
                    .filter(primeNumberServiceApache::isPrime)
                    .asLongStream()
                    .boxed()
                    .collect(Collectors.toList());
    }

    @Override
    public String getServiceType() {
        return "apache";
    }
}
