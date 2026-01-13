package gl.rvrs.voteapp.election.domain.voting.exception;

public class AlreadyVotedException extends CustomException {

	public AlreadyVotedException(String message) {
		super(message);
	}

	public String getErrorCode() {
		return "ALREADY_VOTED";
	}
}
