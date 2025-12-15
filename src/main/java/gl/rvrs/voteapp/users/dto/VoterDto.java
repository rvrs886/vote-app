package gl.rvrs.voteapp.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//Some @Pattern could be introduced for password also, but omitted for simplicity
//Or event custom annotation for password validation
public record VoterDto(@NotBlank String username, @Email @NotBlank String email, @Size(min = 8) @NotBlank String password) {
}
