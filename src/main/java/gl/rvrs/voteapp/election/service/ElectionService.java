package gl.rvrs.voteapp.election.service;

import gl.rvrs.voteapp.election.domain.Candidate;
import gl.rvrs.voteapp.election.domain.Election;
import gl.rvrs.voteapp.election.domain.Vote;
import gl.rvrs.voteapp.election.domain.VotingContext;
import gl.rvrs.voteapp.election.dto.ElectionDto;
import gl.rvrs.voteapp.election.dto.VoteDto;
import gl.rvrs.voteapp.election.repository.ElectionRepository;
import gl.rvrs.voteapp.election.util.VotingContextPreparator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectionService {

	private final ElectionRepository electionRepository;
	private final VotingContextPreparator votingContextPreparator;
	private final VotingPolicy votingPolicy = new VotingPolicy();

	public ElectionService(ElectionRepository electionRepository,
	                       VotingContextPreparator votingContextPreparator) {
		this.electionRepository = electionRepository;
		this.votingContextPreparator = votingContextPreparator;
	}

	public Page<Election> getAllElections(Pageable pageable) {
		return electionRepository.findAll(pageable);
	}

	@Transactional
	public Election createElection(ElectionDto electionDto) {
		List<Candidate> candidates = electionDto.candidates().stream()
				.map(Candidate::new)
				.toList();

		Election election = new Election(electionDto.title(), electionDto.description(), candidates);
		return electionRepository.save(election);
	}

	@Transactional
	public void submitVote(Long electionId, VoteDto voteDto) {
		VotingContext votingCtx = votingContextPreparator.prepare(electionId, voteDto);
		votingPolicy.validateVote(votingCtx.election(), votingCtx.voter(), votingCtx.candidate());

		Election election = getElection(electionId);

		Vote vote = new Vote(votingCtx.candidate(), votingCtx.voter(), votingCtx.election());
		election.addVote(vote);

		electionRepository.save(election);
	}

	private Election getElection(Long id) {
		return electionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Election with id: " + id + " not found"));
	}
}
