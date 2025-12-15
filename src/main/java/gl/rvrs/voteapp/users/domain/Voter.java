package gl.rvrs.voteapp.users.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "voters")
public class Voter extends User {

	private Boolean blocked = false;

	// JPA only
	protected Voter() {
		super(null, null, null);
	}

	public Voter(String username, String email, String password) {
		super(username, email, password);
	}

	public void block() {
		this.blocked = true;
	}

	public void unblock() {
		this.blocked = false;
	}

	public Boolean isBlocked() {
		return blocked;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;
		Voter voter = (Voter) object;
		return Objects.equals(blocked, voter.blocked);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), blocked);
	}
}
