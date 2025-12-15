package gl.rvrs.voteapp.election.exception;

public class VoterBlockedException extends CustomException {

	public VoterBlockedException(String message) {
		super(message);
	}

	@Override
	public String getErrorCode() {
		return "VOTER_BLOCKED";
	}
}
