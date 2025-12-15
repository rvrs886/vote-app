package gl.rvrs.voteapp.election.exception;

public class AlreadyVotedException extends CustomException {

	public AlreadyVotedException(String message) {
		super(message);
	}

	public String getErrorCode() {
		return "ALREADY_VOTED";
	}
}
