package gl.rvrs.voteapp.election.util;

import gl.rvrs.voteapp.election.domain.Candidate;
import gl.rvrs.voteapp.election.domain.Election;
import gl.rvrs.voteapp.election.domain.VotingContext;
import gl.rvrs.voteapp.election.dto.VoteDto;
import gl.rvrs.voteapp.users.domain.Voter;
import org.springframework.stereotype.Component;

@Component
public class VotingContextPreparator {

	private final EntityFetcher entityFetcher;

	public VotingContextPreparator(EntityFetcher entityFetcher) {
		this.entityFetcher = entityFetcher;
	}

	public VotingContext prepare(Long electionId, VoteDto voteDto) {
		Election election = entityFetcher.getElection(electionId);
		Candidate candidate = entityFetcher.getCandidate(voteDto.candidateId());
		Voter voter = entityFetcher.getVoter(voteDto.voterId());
		return new VotingContext(election, voter, candidate);
	}
}
