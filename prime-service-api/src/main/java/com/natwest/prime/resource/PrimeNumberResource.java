package com.natwest.prime.resource;

import com.natwest.prime.exception.InvalidRequestException;
import com.natwest.prime.service.PrimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@Api( "An Api to return the list of prime numbers up to a top limit of maximum value 1000000")
@RestController
@Validated
@RequestMapping("/primes")
public class PrimeNumberResource {

    @Autowired
    private List<PrimeService> primeServices;

    @Operation(
            tags = "Prime Service",
            summary = "Api returns list of all prime numbers starting from 2 for a given number with top limit of 1000000"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful Response"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping(value = "/{number}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<PrimeNumberResponse> getPrimes(@PathVariable("number") @Min(2) @Max(1000000) Long number, @RequestHeader(value = "serviceType", defaultValue = "default") String type) {

        return ResponseEntity.ok(new PrimeNumberResponse(number, getService(type).getPrimes(number)));
    }


    private PrimeService getService(String type) {

        Optional<PrimeService> primeServiceStream = primeServices.stream()
                .filter(primeService -> primeService.getServiceType().equalsIgnoreCase(type))
                .findFirst();

        return primeServiceStream.orElseThrow(() -> new InvalidRequestException("Bad request - header parameter type is invalid"));


    }
}
