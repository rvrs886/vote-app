package gl.rvrs.voteapp.users.web;

import gl.rvrs.voteapp.users.domain.Voter;
import gl.rvrs.voteapp.users.web.dto.VoterResponse;
import gl.rvrs.voteapp.users.web.dto.VoterDto;
import gl.rvrs.voteapp.users.persistence.VoterRepository;
import gl.rvrs.voteapp.users.application.VoterService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voters")
public class VoterController {

	private final VoterRepository voterRepository;
	private final VoterService voterService;

	public VoterController(VoterRepository voterRepository, VoterService voterService) {
		this.voterRepository = voterRepository;
		this.voterService = voterService;
	}

	@GetMapping
	public Page<VoterResponse> getAllVoters(Pageable pageable) {
		return voterRepository.findAll(pageable)
				.map(VoterResponse::from);
	}

	@GetMapping("/{id}")
	public VoterResponse getVoterById(@PathVariable Long id) {
		return VoterResponse.from(voterService.getVoter(id));
	}

	@PostMapping
	public VoterResponse saveVoter(@Valid  @RequestBody VoterDto voterDto) {
		Voter voter = voterService.createVoter(voterDto);
		return VoterResponse.from(voter);
	}

	@PutMapping("/{id}/block")
	public void blockVoter(@PathVariable Long id) {
		voterService.blockVoter(id);
	}

	@PutMapping("/{id}/unblock")
	public void unblockVoter(@PathVariable Long id) {
		voterService.unblockVoter(id);
	}
}
