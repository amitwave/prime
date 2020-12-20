package com.natwest.prime;

import com.natwest.prime.resource.PrimeNumberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PrimeNumbersApplicationTests {

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
	public void shouldReturnPrimeNumbersForJsonAndXmlMediaType(HttpStatus httpStatus, MediaType mediaType, String type, Long number, Long expectedInitial) throws Exception {
		headers.setAccept(Arrays.asList(mediaType));
		headers.set("type", type);

		HttpEntity<PrimeNumberResponse> entity = new HttpEntity<>(null, headers);

		ResponseEntity<PrimeNumberResponse> primeNumberResponseResponseEntity =
				this.restTemplate.exchange("http://localhost:" + port + "/primes/"+ number,
						HttpMethod.GET, entity, PrimeNumberResponse.class);

		assertThat(primeNumberResponseResponseEntity);
		assertEquals(mediaType, primeNumberResponseResponseEntity.getHeaders().getContentType());
		assertEquals(httpStatus.value(), primeNumberResponseResponseEntity.getStatusCodeValue());

		PrimeNumberResponse primeNumberResponse = primeNumberResponseResponseEntity.getBody();

		assertEquals(expectedInitial, primeNumberResponse.getInitial(), "Unexpected initial value");
	}

	private static Stream<Arguments> shouldReturnPrimeNumbersForJsonAndXmlMediaType() {
		return Stream.of(
				Arguments.of(HttpStatus.OK, MediaType.APPLICATION_JSON, "default", 11L, 11L),
				Arguments.of(HttpStatus.OK, MediaType.APPLICATION_XML, "default", 11L, 11L),
				Arguments.of(HttpStatus.OK, MediaType.APPLICATION_JSON, "apache", 11L, 11L),
				Arguments.of(HttpStatus.OK, MediaType.APPLICATION_XML, "apache", 11L, 11L),
				Arguments.of(HttpStatus.BAD_REQUEST, MediaType.APPLICATION_JSON, "invalid", 11L, null),
				Arguments.of(HttpStatus.BAD_REQUEST, MediaType.APPLICATION_JSON, "default", 1L, null))
				;
	}
}
