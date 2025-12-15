package gl.rvrs.voteapp.users.service;

public interface PasswordEncoder {

	String encode(String password);
}
