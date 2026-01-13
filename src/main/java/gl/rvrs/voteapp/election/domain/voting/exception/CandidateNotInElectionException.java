package gl.rvrs.voteapp.election.domain.voting.exception;

public class CandidateNotInElectionException extends CustomException {

	public CandidateNotInElectionException(String message) {
		super(message);
	}

	@Override
	public String getErrorCode() {
		return "CANDIDATE_NOT_IN_ELECTION";
	}
}
