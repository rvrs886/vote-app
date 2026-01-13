package gl.rvrs.voteapp.election.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record ElectionDto(@NotBlank @Size(max = 50) String title, @NotBlank @Size(max = 100) String description, @NotEmpty @Size(min = 2) Set<String> candidates) {
}
