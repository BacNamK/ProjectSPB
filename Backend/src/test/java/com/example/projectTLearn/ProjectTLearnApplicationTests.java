package com.example.projectTLearn;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.projectTLearn.config.JwtTokenProvider;

@SpringBootTest
class ProjectTLearnApplicationTests {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldGenerateSeparateAccessAndRefreshTokens() {
		String accessToken = jwtTokenProvider.generateAccessToken("1");
		String refreshToken = jwtTokenProvider.generateRefreshToken("1");

		assertNotNull(accessToken);
		assertNotNull(refreshToken);
		assertNotNull(jwtTokenProvider.getUserFromJWT(accessToken));
		assertNotNull(jwtTokenProvider.getUserFromJWT(refreshToken));
	}

}
