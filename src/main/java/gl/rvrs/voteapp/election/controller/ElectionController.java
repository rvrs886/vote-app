package gl.rvrs.voteapp.election.controller;

import gl.rvrs.voteapp.election.domain.Election;
import gl.rvrs.voteapp.election.dto.ElectionDto;
import gl.rvrs.voteapp.election.dto.ElectionResponse;
import gl.rvrs.voteapp.election.dto.VoteDto;
import gl.rvrs.voteapp.election.service.ElectionServiceImpl;
import gl.rvrs.voteapp.election.util.ElectionMapper;
import gl.rvrs.voteapp.util.PageRequest;
import gl.rvrs.voteapp.util.PagedData;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elections")
public class ElectionController {

	private final ElectionServiceImpl electionService;
	private final ElectionMapper electionMapper;

	public ElectionController(ElectionServiceImpl electionService, ElectionMapper electionMapper) {
		this.electionMapper = electionMapper;
		this.electionService = electionService;
	}

	@GetMapping
	public PagedData<ElectionResponse> getAllElections(PageRequest pageRequest) {
		return electionMapper.toPagedData(electionResponses);
	}

	@PostMapping
	public Election createElection(@Valid @RequestBody ElectionDto electionDto) {
		return electionService.createElection(electionDto);
	}

	@PostMapping("/{id}/vote")
	public void submitVote(@PathVariable Long id, @Valid @RequestBody VoteDto voteDto) {
		electionService.submitVote(id, voteDto);
	}

	@DeleteMapping("/{id}")
	public void deleteElection(@PathVariable Long id) {
		electionService.deleteElection(id);
	}
}
