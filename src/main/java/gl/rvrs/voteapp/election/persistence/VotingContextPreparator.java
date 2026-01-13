package gl.rvrs.voteapp.election.persistence;

import gl.rvrs.voteapp.election.domain.Candidate;
import gl.rvrs.voteapp.election.domain.Election;
import gl.rvrs.voteapp.election.domain.VoteCreateData;
import gl.rvrs.voteapp.election.domain.voting.VoteRequestContext;
import gl.rvrs.voteapp.users.domain.Voter;
import org.springframework.stereotype.Component;

@Component
public class VotingContextPreparator {

	private final EntityFetcher entityFetcher;

	public VotingContextPreparator(EntityFetcher entityFetcher) {
		this.entityFetcher = entityFetcher;
	}

	public VoteRequestContext prepare(Long electionId, VoteCreateData voteCreateData) {
		Election election = entityFetcher.getElection(electionId);
		Candidate candidate = entityFetcher.getCandidate(voteCreateData.candidateId());
		Voter voter = entityFetcher.getVoter(voteCreateData.voterId());
		return new VoteRequestContext(election, voter, candidate);
	}
}
