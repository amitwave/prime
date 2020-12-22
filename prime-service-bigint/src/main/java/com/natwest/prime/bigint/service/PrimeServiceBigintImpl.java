package com.natwest.prime.bigint.service;

import com.natwest.prime.service.PrimeService;
import com.natwest.prime.service.exception.NumberNotInRangeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("primaryServiceBigint")
public class PrimeServiceBigintImpl implements PrimeService{

    @Override
    public List<Long> getPrimes(Long number) {
        log.info("Primes for {}", number);

        if(number <=1) {
            throw new NumberNotInRangeException("Number should be at least 2");
        }

        if(number > 10000000) {
            throw new NumberNotInRangeException("Number should be less than or equal to 10000000");
        }

        List<Long> primeNumbers = new ArrayList<>();

        BigInteger bigInteger =  BigInteger.valueOf(1);
        while(bigInteger.nextProbablePrime().longValue() <= number){
            primeNumbers.add(bigInteger.nextProbablePrime().longValue());
            bigInteger = BigInteger.valueOf(bigInteger.nextProbablePrime().longValue());
        }

        return primeNumbers;
    }

    @Override
    public String getServiceType() {
        return "bigint";
    }
}
