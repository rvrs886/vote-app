package gl.rvrs.voteapp.election.domain.voting;

import gl.rvrs.voteapp.election.domain.Candidate;
import gl.rvrs.voteapp.election.domain.Election;
import gl.rvrs.voteapp.users.domain.Voter;

public record VoteRequestContext(Election election, Voter voter, Candidate candidate) {
}
