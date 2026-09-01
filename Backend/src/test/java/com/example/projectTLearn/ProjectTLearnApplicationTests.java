package com.example.projectTLearn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.example.projectTLearn.repository.AuthRepository;
import com.example.projectTLearn.security.filter.JwtAuthenticationFilter;
import com.example.projectTLearn.util.JwtTokenProvider;

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

	@Test
	void shouldSkipJwtFilterForAuthEndpoints() throws Exception {
		JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
		AuthRepository authRepository = mock(AuthRepository.class);
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtTokenProvider, authRepository);

		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/auth/login");
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();

		filter.doFilter(request, response, chain);

		assertEquals(200, response.getStatus());
		verifyNoInteractions(jwtTokenProvider, authRepository);
	}

}
