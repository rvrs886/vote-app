package gl.rvrs.voteapp.election.domain.voting.exception;

public abstract class CustomException extends RuntimeException {

	public CustomException(String message) {
		super(message);
	}

	public abstract String getErrorCode();
}
