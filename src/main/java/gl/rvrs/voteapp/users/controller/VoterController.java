package gl.rvrs.voteapp.users.controller;

import gl.rvrs.voteapp.users.domain.Voter;
import gl.rvrs.voteapp.users.domain.VoterView;
import gl.rvrs.voteapp.users.dto.VoterDto;
import gl.rvrs.voteapp.users.repo.VoterRepository;
import gl.rvrs.voteapp.users.service.VoterService;
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
	public Page<VoterView> getAllVoters(Pageable pageable) {
		return voterRepository.findAll(pageable)
				.map(VoterView::from);
	}

	@GetMapping("/{id}")
	public VoterView getVoterById(@PathVariable Long id) {
		return VoterView.from(voterService.getVoter(id));
	}

	@PostMapping
	public VoterView saveVoter(@Valid  @RequestBody VoterDto voterDto) {
		Voter voter = voterService.createVoter(voterDto);
		return VoterView.from(voter);
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
