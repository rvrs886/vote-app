package gl.rvrs.voteapp.election.domain;

import gl.rvrs.voteapp.users.domain.Voter;

public record VotingContext(Election election, Voter voter, Candidate candidate) {
}
