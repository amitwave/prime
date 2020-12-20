package com.natwest.prime.service;

import java.util.List;

public interface PrimeService {

    List<Long> getPrimes(Long number);
    String getServiceType();
}
