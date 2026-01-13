package gl.rvrs.voteapp.election.web.dto;

import jakarta.validation.constraints.NotNull;

public record VoteDto(@NotNull Long candidateId, @NotNull Long voterId) {
}
