package gl.rvrs.voteapp.users.application.security;

public interface PasswordEncoder {

	String encode(String password);
}
