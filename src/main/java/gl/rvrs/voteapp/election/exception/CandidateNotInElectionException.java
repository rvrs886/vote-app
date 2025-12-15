package gl.rvrs.voteapp.election.exception;

public class CandidateNotInElectionException extends CustomException {

	public CandidateNotInElectionException(String message) {
		super(message);
	}

	@Override
	public String getErrorCode() {
		return "CANDIDATE_NOT_IN_ELECTION";
	}
}
