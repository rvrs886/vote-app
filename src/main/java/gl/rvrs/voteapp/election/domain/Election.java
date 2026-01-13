package gl.rvrs.voteapp.election.domain;

import gl.rvrs.voteapp.election.domain.voting.Vote;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "elections")
public class Election {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String description;

	@OneToMany(cascade = {CascadeType.PERSIST})
	private List<Candidate> candidates;

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "election")
	private List<Vote> votes = new ArrayList<>();

	// JPA only
	protected Election() {
	}

	public Election(String title, String description, List<Candidate> candidates) {
		this.title = title;
		this.description = description;
		this.candidates = candidates;
	}

	public Election(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public List<Candidate> getCandidates() {
		return candidates;
	}

	public List<Vote> getVotes() {
		return votes;
	}

	public void addVote(Vote vote) {
		this.votes.add(vote);
	}

	public void addCandidate(String candidateName) {
		Candidate candidate = new Candidate(candidateName);
		candidates.add(candidate);
	}

	@Override
	public boolean equals(Object object) {
		if (object == null || getClass() != object.getClass()) return false;
		Election election = (Election) object;
		return Objects.equals(id, election.id) &&
				Objects.equals(title, election.title) &&
				Objects.equals(description, election.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, description);
	}
}
