package gl.rvrs.voteapp.election.domain;

import gl.rvrs.voteapp.users.domain.Voter;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
		name = "votes",
		uniqueConstraints = @UniqueConstraint(columnNames = {"election_id", "voter_id"})
)
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private Candidate choice;

	@ManyToOne
	@JoinColumn(name = "voter_id", nullable = false)
	private Voter voter;

	@ManyToOne
	private Election election;

	// JPA only
	protected Vote() {
	}

	public Vote(Candidate choice, Voter voter, Election election) {
		this.choice = choice;
		this.voter = voter;
		this.election = election;
	}

	public Long getId() {
		return id;
	}

	public Candidate getChoice() {
		return choice;
	}

	public Voter getVoter() {
		return voter;
	}

	public Election getElection() {
		return election;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null || getClass() != object.getClass()) return false;
		Vote vote = (Vote) object;
		return Objects.equals(id, vote.id) &&
				Objects.equals(choice, vote.choice) &&
				Objects.equals(voter, vote.voter) &&
				Objects.equals(election, vote.election);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, choice, voter, election);
	}
}
