package com.natwest.prime.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PrimeNumbersApplicationTestsIT {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private HttpHeaders headers;

	@BeforeEach
	public void before() {
		headers = new HttpHeaders();
	}

	@ParameterizedTest
	@MethodSource
	public void shouldReturnPrimeNumbersForJsonAndXmlMediaType(HttpStatus httpStatus, MediaType mediaType, String type, Long number, Long expectedInitial, List<Long> primes) throws Exception {
		headers.setAccept(Arrays.asList(mediaType));
		headers.set("serviceType", type);

		HttpEntity<PrimeNumberResponse> entity = new HttpEntity<>(null, headers);

		ResponseEntity<PrimeNumberResponse> primeNumberResponseResponseEntity =
				this.restTemplate.exchange("http://localhost:" + port + "/primes/"+ number,
						HttpMethod.GET, entity, PrimeNumberResponse.class);


		assertEquals("Unexpected Media Type", mediaType, primeNumberResponseResponseEntity.getHeaders().getContentType());
		assertEquals("Unexpected status", httpStatus.value(), primeNumberResponseResponseEntity.getStatusCodeValue());

		PrimeNumberResponse primeNumberResponse = primeNumberResponseResponseEntity.getBody();

		assertEquals("Unexpected initial value", expectedInitial, primeNumberResponse.getInitial());
		assertEquals("Unexpected size of returned primes", primes.size(), primeNumberResponse.getPrimes().size() );
		assertEquals("unexpected primes", primes, primeNumberResponse.getPrimes());
	}

	private static Stream<Arguments> shouldReturnPrimeNumbersForJsonAndXmlMediaType() {
		return Stream.of(
				Arguments.of(HttpStatus.OK, MediaType.APPLICATION_JSON, "default", 10L, 10L, Arrays.asList(2L,3L,5L,7L)),
				Arguments.of(HttpStatus.OK, MediaType.APPLICATION_XML, "default", 10L, 10L, Arrays.asList(2L,3L,5L,7L)),

				Arguments.of(HttpStatus.OK, MediaType.APPLICATION_JSON, "default", 11L, 11L, Arrays.asList(2L,3L,5L,7L,11L)),
				Arguments.of(HttpStatus.OK, MediaType.APPLICATION_XML, "default", 11L, 11L, Arrays.asList(2L,3L,5L,7L,11L)),

				Arguments.of(HttpStatus.OK, MediaType.APPLICATION_JSON, "apache", 11L, 11L, Arrays.asList(2L,3L,5L,7L,11L)),
				Arguments.of(HttpStatus.OK, MediaType.APPLICATION_XML, "apache", 11L, 11L, Arrays.asList(2L,3L,5L,7L,11L)),

				Arguments.of(HttpStatus.BAD_REQUEST, MediaType.APPLICATION_JSON, "invalid", 11L, null, Collections.EMPTY_LIST),

				Arguments.of(HttpStatus.BAD_REQUEST, MediaType.APPLICATION_JSON, "default", 1L, null, Collections.EMPTY_LIST),
				Arguments.of(HttpStatus.BAD_REQUEST, MediaType.APPLICATION_JSON, "default", 1000001L, null, Collections.EMPTY_LIST),
				Arguments.of(HttpStatus.BAD_REQUEST, MediaType.APPLICATION_JSON, "bigint", 10000001L, null, Collections.EMPTY_LIST)
		);
	}
}
