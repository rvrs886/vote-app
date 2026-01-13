package gl.rvrs.voteapp.election.application;

import gl.rvrs.voteapp.election.domain.*;
import gl.rvrs.voteapp.election.domain.voting.Vote;
import gl.rvrs.voteapp.election.domain.voting.VoteRequestContext;
import gl.rvrs.voteapp.election.domain.voting.VotingPolicy;
import gl.rvrs.voteapp.election.web.mapper.ElectionMapper;
import gl.rvrs.voteapp.common.web.paging.PageRequest;
import gl.rvrs.voteapp.common.web.paging.PageRequestMapper;
import gl.rvrs.voteapp.common.web.paging.PagedData;
import gl.rvrs.voteapp.election.persistence.ElectionRepository;
import gl.rvrs.voteapp.election.persistence.VotingContextPreparator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ElectionServiceImpl implements ElectionService {

	private final ElectionRepository electionRepository;
	private final VotingContextPreparator votingContextPreparator;
	private final PageRequestMapper pageRequestMapper;
	private final VotingPolicy votingPolicy;

	public ElectionServiceImpl(ElectionRepository electionRepository,
	                           VotingContextPreparator votingContextPreparator,
	                           PageRequestMapper pageRequestMapper,
	                           VotingPolicy votingPolicy) {
		this.electionRepository = electionRepository;
		this.votingContextPreparator = votingContextPreparator;
		this.pageRequestMapper = pageRequestMapper;
		this.votingPolicy = votingPolicy;
	}

	@Override
	public PagedData<Election> getElectionsPaged(PageRequest pageRequest) {
		Pageable pageable = pageRequestMapper.toPageable(pageRequest);
		Page<Election> elections = electionRepository.findAll(pageable);
		return pageRequestMapper.toPagedData(elections);
	}

	@Transactional
	@Override
	public Election createElection(ElectionCreateData electionCreateData) {
		Election election = new Election(electionCreateData.title(), electionCreateData.description());
		electionCreateData.candidates().forEach(election::addCandidate);
		return electionRepository.save(election);
	}

	@Override
	public void submitVote(Long electionId, VoteCreateData voteCreateData) {
		VoteRequestContext votingCtx = votingContextPreparator.prepare(electionId, voteCreateData);
		votingPolicy.validateVote(votingCtx.election(), votingCtx.voter(), votingCtx.candidate());

		Election election = getElectionById(electionId);

		Vote vote = new Vote(votingCtx.candidate(), votingCtx.voter(), election);

		election.addVote(vote);
		electionRepository.save(election);
	}

	@Transactional
	@Override
	public void deleteElection(Long electionId) {
		electionRepository.deleteById(electionId);
	}

	@Override
	public Election getElectionById(Long id) {
		return electionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Election with id: " + id + " not found"));
	}
}
