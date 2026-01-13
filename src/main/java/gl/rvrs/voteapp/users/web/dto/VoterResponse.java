package gl.rvrs.voteapp.users.web.dto;

import gl.rvrs.voteapp.users.domain.Voter;

public record VoterResponse(Long id, String username, String email) {

	public static VoterResponse from(Voter voter) {
		return new VoterResponse(voter.getId(), voter.getUsername(), voter.getEmail());
	}
}
