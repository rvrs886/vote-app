package gl.rvrs.voteapp.election.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "candidates")
public class Candidate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	// JPA only
	protected Candidate() {
	}

	public Candidate(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null || getClass() != object.getClass()) return false;
		Candidate candidate = (Candidate) object;
		return Objects.equals(id, candidate.id) &&
				Objects.equals(name, candidate.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}
