package gl.rvrs.voteapp.users.config;

import gl.rvrs.voteapp.users.application.security.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoterConfiguration {

	//dummy implementation, password won't be encoded, should be changed in production
	@Bean
	PasswordEncoder passwordEncoder() {
		return password -> password;
	}

}
