package gl.rvrs.voteapp.election.controller;

import gl.rvrs.voteapp.election.domain.Election;
import gl.rvrs.voteapp.election.dto.ElectionDto;
import gl.rvrs.voteapp.election.dto.VoteDto;
import gl.rvrs.voteapp.election.repository.ElectionRepository;
import gl.rvrs.voteapp.election.service.ElectionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elections")
public class ElectionController {

	private final ElectionRepository electionRepository;
	private final ElectionService electionService;

	public ElectionController(ElectionRepository electionRepository, ElectionService electionService) {
		this.electionRepository = electionRepository;
		this.electionService = electionService;
	}

	@GetMapping
	public Page<Election> getAllElections(Pageable pageable) {
		return electionService.getAllElections(pageable);
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
		electionRepository.deleteById(id);
	}
}
