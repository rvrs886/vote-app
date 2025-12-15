package gl.rvrs.voteapp.election.dto;

import jakarta.validation.constraints.NotNull;

public record VoteDto(@NotNull Long candidateId, @NotNull Long voterId) {
}
