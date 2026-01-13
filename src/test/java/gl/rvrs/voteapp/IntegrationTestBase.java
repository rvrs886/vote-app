package gl.rvrs.voteapp;

import gl.rvrs.voteapp.election.persistence.CandidateRepository;
import gl.rvrs.voteapp.election.persistence.ElectionRepository;
import gl.rvrs.voteapp.election.application.ElectionServiceImpl;
import gl.rvrs.voteapp.users.persistence.VoterRepository;
import gl.rvrs.voteapp.users.application.security.PasswordEncoder;
import gl.rvrs.voteapp.users.application.VoterService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public abstract class IntegrationTestBase {

	@Autowired
	private WebApplicationContext webAppContext;

	@Autowired
	protected VoterRepository voterRepository;

	@Autowired
	protected VoterService voterService;

	@Autowired
	protected ElectionRepository electionRepository;

	@Autowired
	protected ElectionServiceImpl electionService;

	@Autowired
	protected CandidateRepository candidateRepository;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	protected MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
				.build();
	}

	public void cleanup() {
		voterRepository.deleteAll();
		candidateRepository.deleteAll();
		electionRepository.deleteAll();
	}
}
