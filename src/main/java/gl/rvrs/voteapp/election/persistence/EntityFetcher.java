package gl.rvrs.voteapp.election.persistence;

import gl.rvrs.voteapp.election.domain.Candidate;
import gl.rvrs.voteapp.election.domain.Election;
import gl.rvrs.voteapp.users.domain.Voter;
import gl.rvrs.voteapp.users.persistence.VoterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class EntityFetcher {

	private final VoterRepository voterRepository;
	private final CandidateRepository candidateRepository;
	private final ElectionRepository electionRepository;

	public EntityFetcher(VoterRepository voterRepository, CandidateRepository candidateRepository, ElectionRepository electionRepository) {
		this.voterRepository = voterRepository;
		this.candidateRepository = candidateRepository;
		this.electionRepository = electionRepository;
	}

	public Voter getVoter(Long voterId) {
		return voterRepository.findById(voterId)
				.orElseThrow(() -> new EntityNotFoundException("Voter with id: " + voterId + " not found"));
	}

	public Candidate getCandidate(Long candidateId) {
		return candidateRepository.findById(candidateId)
				.orElseThrow(() -> new EntityNotFoundException("Candidate with id: " + candidateId + " not found"));
	}

	public Election getElection(Long electionId) {
		return electionRepository.findById(electionId)
				.orElseThrow(() -> new EntityNotFoundException("Election with id: " + electionId + " not found"));
	}
}
