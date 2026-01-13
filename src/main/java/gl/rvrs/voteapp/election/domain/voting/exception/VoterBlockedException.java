package gl.rvrs.voteapp.election.domain.voting.exception;

public class VoterBlockedException extends CustomException {

	public VoterBlockedException(String message) {
		super(message);
	}

	@Override
	public String getErrorCode() {
		return "VOTER_BLOCKED";
	}
}
