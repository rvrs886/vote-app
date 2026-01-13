package gl.rvrs.voteapp.election.controller;

import gl.rvrs.voteapp.IntegrationTestBase;
import gl.rvrs.voteapp.election.domain.Election;
import gl.rvrs.voteapp.election.web.dto.ElectionDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ElectionControllerTest extends IntegrationTestBase {

	private static final String ELECTIONS_URL = "/elections";
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void shouldReturnAllElections() throws Exception {
		//given
		int pageSize = 3;
		int expectedTotalPages = 2;
		List<Election> elections = List.of(
				ElectionBuilder.testElection().build(electionRepository),
				ElectionBuilder.testElection().build(electionRepository),
				ElectionBuilder.testElection().build(electionRepository),
				ElectionBuilder.testElection().build(electionRepository),
				ElectionBuilder.testElection().build(electionRepository)
		);

		//when & then
		mockMvc.perform(get(ELECTIONS_URL)
				.param("page", "0")
				.param("size", String.valueOf(pageSize))
		)
				.andExpect(jsonPath("$.content").isArray())
				.andExpect(jsonPath("$.content.length()").value(pageSize))
				.andExpect(jsonPath("$.totalElements").value(elections.size()))
				.andExpect(jsonPath("$.totalPages").value(expectedTotalPages));
	}

	@Test
	void shouldCreateElection() throws Exception {
		//given
		ElectionDto electionDto = new ElectionDto(
				"test title",
				"test description",
				Set.of("candidate 1", "candidate 2", "candidate 3")
		);

		//when & then
		mockMvc.perform(post(ELECTIONS_URL)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(electionDto)))
				.andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(jsonPath("$.title").value(electionDto.title()))
				.andExpect(jsonPath("$.description").value(electionDto.description()))
				.andExpect(jsonPath("$.candidates").isArray())
				.andExpect(jsonPath("$.candidates[0].name").value(in(electionDto.candidates())))
				.andExpect(jsonPath("$.candidates[1].name").value(in(electionDto.candidates())))
				.andExpect(jsonPath("$.candidates[2].name").value(in(electionDto.candidates())))
				.andExpect(jsonPath("$.candidates[3].name").doesNotExist());
	}

	@Test
	void shouldReturn400BadRequestWhenTitleLengthExceedsLimit() throws Exception {
		//given
		ElectionDto electionDto = new ElectionDto(
				RandomStringUtils.random(51, true, false),
				"test description",
				Set.of("candidate 1", "candidate 2", "candidate 3")
		);

		//when & then
		mockMvc.perform(post(ELECTIONS_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(electionDto)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.result").isArray())
				.andExpect(jsonPath("$.result[*]").value("title: size must be between 0 and 50"));
	}

	@Test
	void shouldReturn400BadRequestWhenDescriptionLengthExceedsLimit() throws Exception {
		//given
		ElectionDto electionDto = new ElectionDto(
				"test title",
				RandomStringUtils.random(101, true, false),
				Set.of("candidate 1", "candidate 2", "candidate 3")
		);

		//when & then
		mockMvc.perform(post(ELECTIONS_URL)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(electionDto)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.result").isArray())
				.andExpect(jsonPath("$.result[*]").value("description: size must be between 0 and 100"));
	}

	@Test
	void shouldReturn400BadRequestWhenTitleAndDescriptionLengthExceedsLimit() throws Exception {
		//given
		ElectionDto electionDto = new ElectionDto(
				RandomStringUtils.random(51, true, false),
				RandomStringUtils.random(101, true, false),
				Set.of("candidate 1", "candidate 2", "candidate 3")
		);

		//when & then
		mockMvc.perform(post(ELECTIONS_URL)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(electionDto)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.result").isArray())
				.andExpect(jsonPath("$.result[*]").value(
						containsInAnyOrder(
								"title: size must be between 0 and 50",
								"description: size must be between 0 and 100"
						)
				));
	}

	@Test
	void shouldDeleteElectionById() throws Exception {
		//given
		Election election = ElectionBuilder.testElection()
				.build(electionRepository);

		//when
		mockMvc.perform(delete(ELECTIONS_URL + "/" + election.getId()))
				.andExpect(status().isOk());

		//then
		Optional<Election> deletedElection = electionRepository.findById(election.getId());
		assertThat(deletedElection).isEmpty();
	}
}