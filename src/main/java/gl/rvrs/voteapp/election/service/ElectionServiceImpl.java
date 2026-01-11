package gl.rvrs.voteapp.election.service;

import gl.rvrs.voteapp.util.PageRequest;
import gl.rvrs.voteapp.util.PageRequestMapper;
import gl.rvrs.voteapp.util.PagedData;
import gl.rvrs.voteapp.election.domain.Candidate;
import gl.rvrs.voteapp.election.domain.Election;
import gl.rvrs.voteapp.election.domain.Vote;
import gl.rvrs.voteapp.election.domain.VotingContext;
import gl.rvrs.voteapp.election.dto.ElectionDto;
import gl.rvrs.voteapp.election.dto.VoteDto;
import gl.rvrs.voteapp.election.repository.ElectionRepository;
import gl.rvrs.voteapp.election.util.ElectionMapper;
import gl.rvrs.voteapp.election.util.VotingContextPreparator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ElectionServiceImpl implements ElectionService {

	private final ElectionRepository electionRepository;
	private final VotingContextPreparator votingContextPreparator;
	private final ElectionMapper electionMapper;
	private final PageRequestMapper pageRequestMapper;
	private final VotingPolicy votingPolicy = new VotingPolicy();

	public ElectionServiceImpl(ElectionRepository electionRepository,
	                           VotingContextPreparator votingContextPreparator,
	                           ElectionMapper electionMapper,
	                           PageRequestMapper pageRequestMapper) {
		this.electionRepository = electionRepository;
		this.votingContextPreparator = votingContextPreparator;
		this.electionMapper = electionMapper;
		this.pageRequestMapper = pageRequestMapper;
	}

	@Override
	public PagedData<Election> getElectionsPaged(PageRequest pageRequest) {
		Pageable pageable = pageRequestMapper.toPageable(pageRequest);
		Page<Election> elections = electionRepository.findAll(pageable);
		return electionMapper.toPagedData(elections);
	}

	@Transactional
	@Override
	public Election createElection(ElectionDto electionDto) {
		List<Candidate> candidates = electionDto.candidates().stream()
				.map(Candidate::new)
				.toList();

		Election election = new Election(electionDto.title(), electionDto.description(), candidates);
		return electionRepository.save(election);
	}

	@Override
	public void submitVote(Object electionId, VoteDto voteDto) {
		VotingContext votingCtx = votingContextPreparator.prepare((Long) electionId, voteDto);
		votingPolicy.validateVote(votingCtx.election(), votingCtx.voter(), votingCtx.candidate());

		Election election = getElectionById(electionId);

		Vote vote = new Vote(votingCtx.candidate(), votingCtx.voter(), votingCtx.election());
		election.addVote(vote);

		electionRepository.save(election);
	}

	@Transactional
	@Override
	public void deleteElection(Object electionId) {
		electionRepository.deleteById((Long) electionId);
	}

	@Override
	public Election getElectionById(Object id) {
		return electionRepository.findById((Long) id)
				.orElseThrow(() -> new EntityNotFoundException("Election with id: " + id + " not found"));
	}
}
