package gl.rvrs.voteapp.users.domain;

public record VoterView(Long id, String username, String email) {

	public static VoterView from(Voter voter) {
		return new VoterView(voter.getId(), voter.getUsername(), voter.getEmail());
	}
}
