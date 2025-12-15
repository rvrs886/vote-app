package gl.rvrs.voteapp.users.service;

import gl.rvrs.voteapp.users.domain.Voter;
import gl.rvrs.voteapp.users.dto.VoterDto;
import gl.rvrs.voteapp.users.repo.VoterRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class VoterService {

	private final VoterRepository voterRepository;
	private final PasswordEncoder passwordEncoder;

	public VoterService(VoterRepository voterRepository,
	                    PasswordEncoder passwordEncoder) {
		this.voterRepository = voterRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Voter createVoter(VoterDto voterDto) {
		String encodedPassword = passwordEncoder.encode(voterDto.password());
		Voter voter = new Voter(voterDto.username(), voterDto.email(), encodedPassword);
		return voterRepository.save(voter);
	}

	@Transactional
	public void blockVoter(Long id) {
		Voter voter = getVoter(id);
		voter.block();
	}

	@Transactional
	public void unblockVoter(Long id) {
		Voter voter = getVoter(id);
		voter.unblock();
	}

	public Voter getVoter(Long id) {
		return voterRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Voter with id: " + id + " not found"));
	}
}
