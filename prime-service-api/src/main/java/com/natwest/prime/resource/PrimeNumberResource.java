package com.natwest.prime.resource;

import com.natwest.prime.exception.InvalidRequestException;
import com.natwest.prime.service.PrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@Validated
public class PrimeNumberResource {

    @Autowired
    private List<PrimeService> primeServices;

    @GetMapping(value = "/{number}",
            produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<PrimeNumberResponse> getPrimesResponse(@PathVariable("number") @Min(2) Long number, @RequestHeader(value = "type", defaultValue = "default") String type) {

        return ResponseEntity.ok(new PrimeNumberResponse(number, getService(type).getPrimes(number)));
        }


    private PrimeService getService(String type) {

            Optional<PrimeService> primeServiceStream = primeServices.stream()
                    .filter(ps -> ps.getServiceType().equalsIgnoreCase(type))
                    .findFirst();

        return primeServiceStream.orElseThrow(() -> new InvalidRequestException("Bad request - header parameter type is invalid"));



    }
}
