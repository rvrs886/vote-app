package gl.rvrs.voteapp.election.domain.voting;

import gl.rvrs.voteapp.election.domain.Candidate;
import gl.rvrs.voteapp.election.domain.Election;
import gl.rvrs.voteapp.election.domain.voting.exception.AlreadyVotedException;
import gl.rvrs.voteapp.election.domain.voting.exception.CandidateNotInElectionException;
import gl.rvrs.voteapp.election.domain.voting.exception.VoterBlockedException;
import gl.rvrs.voteapp.users.domain.Voter;
import org.springframework.stereotype.Component;

@Component
public class VotingPolicy {

	public void validateVote(Election election, Voter voter, Candidate candidate) {
		if (!canVote(voter)) {
			throw new VoterBlockedException("Voter with id: " + voter.getId() + " is blocked! Couldn't process vote for election with id: " + election.getId());
		}

		if (hasAlreadyVoted(election, voter)) {
			throw new AlreadyVotedException("Voter with id: " + voter.getId() + " has already voted!");
		}

		if (!isCandidateMemberOfElection(candidate, election)) {
			throw new CandidateNotInElectionException("Candidate with id: " + candidate.getId() + " is not member of election with id: " + election.getId());
		}
	}

	private boolean hasAlreadyVoted(Election election, Voter voter) {
		return election.getVotes().stream()
				.anyMatch(v -> v.getVoter().equals(voter));
	}

	private boolean canVote(Voter voter) {
		return !voter.isBlocked();
	}

	private boolean isCandidateMemberOfElection(Candidate candidate, Election election) {
		return election.getCandidates().contains(candidate);
	}

}
