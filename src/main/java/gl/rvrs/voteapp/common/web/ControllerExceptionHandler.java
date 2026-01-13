package gl.rvrs.voteapp.common.web;

import gl.rvrs.voteapp.election.domain.voting.exception.AlreadyVotedException;
import gl.rvrs.voteapp.election.domain.voting.exception.CandidateNotInElectionException;
import gl.rvrs.voteapp.election.domain.voting.exception.VoterBlockedException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice(annotations = RestController.class, basePackages = "gl.rvrs")
public class ControllerExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(BAD_REQUEST)
	public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		log.error("Exception occurred: ", ex);
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
				.toList();
		return new ErrorDto(errors);
	}

	@ExceptionHandler(VoterBlockedException.class)
	@ResponseStatus(BAD_REQUEST)
	public ErrorDto handleVoterBlockedException(VoterBlockedException ex) {
		log.error("Exception occurred: ", ex);
		return new ErrorDto(ex.getErrorCode(), ex.getMessage());
	}

	@ExceptionHandler(CandidateNotInElectionException.class)
	@ResponseStatus(BAD_REQUEST)
	public ErrorDto handleCandidateNotInElectionException(CandidateNotInElectionException ex) {
		log.error("Exception occurred: ", ex);
		return new ErrorDto(ex.getErrorCode(), ex.getMessage());
	}

	@ExceptionHandler(AlreadyVotedException.class)
	@ResponseStatus(CONFLICT)
	public ErrorDto handleAlreadyVotedException(AlreadyVotedException ex) {
		log.error("Exception occurred: ", ex);
		return new ErrorDto(ex.getErrorCode(), ex.getMessage());
	}

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(NOT_FOUND)
	public ErrorDto handleEntityNotFoundException(Exception ex) {
		log.error("Exception occurred: ", ex);
		return new ErrorDto(ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(INTERNAL_SERVER_ERROR)
	public ErrorDto handleException(Exception ex) {
		log.error("Unhandled exception", ex);
		return new ErrorDto(ex.getMessage());
	}
}
