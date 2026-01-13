package gl.rvrs.voteapp.election.web;

import gl.rvrs.voteapp.election.domain.Election;
import gl.rvrs.voteapp.election.domain.ElectionCreateData;
import gl.rvrs.voteapp.election.domain.VoteCreateData;
import gl.rvrs.voteapp.election.web.dto.ElectionDto;
import gl.rvrs.voteapp.election.web.dto.ElectionResponse;
import gl.rvrs.voteapp.election.web.dto.VoteDto;
import gl.rvrs.voteapp.election.application.ElectionServiceImpl;
import gl.rvrs.voteapp.election.web.mapper.ElectionMapper;
import gl.rvrs.voteapp.common.web.paging.PageRequest;
import gl.rvrs.voteapp.common.web.paging.PagedData;
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
		PagedData<Election> elections = electionService.getElectionsPaged(pageRequest);
		return electionMapper.toPagedElectionResponse(elections);
	}

	@PostMapping
	public ElectionResponse createElection(@Valid @RequestBody ElectionDto electionDto) {
		ElectionCreateData electionCreateData = new ElectionCreateData(electionDto.title(), electionDto.description(), electionDto.candidates());
		Election election = electionService.createElection(electionCreateData);
		return electionMapper.toElectionResponse(election);
	}

	@PostMapping("/{id}/vote")
	public void submitVote(@PathVariable Long id, @Valid @RequestBody VoteDto voteDto) {
		VoteCreateData voteCreateData = new VoteCreateData(voteDto.candidateId(), voteDto.voterId());
		electionService.submitVote(id, voteCreateData);
	}

	@DeleteMapping("/{id}")
	public void deleteElection(@PathVariable Long id) {
		electionService.deleteElection(id);
	}
}
