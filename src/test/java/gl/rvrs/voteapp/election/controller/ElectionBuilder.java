package gl.rvrs.voteapp.election.controller;

import gl.rvrs.voteapp.election.domain.Candidate;
import gl.rvrs.voteapp.election.domain.Election;
import gl.rvrs.voteapp.election.domain.Vote;
import gl.rvrs.voteapp.election.repository.ElectionRepository;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.random;

public class ElectionBuilder {

	private String title;
	private String description;
	private List<Candidate> candidates = new ArrayList<>();
	private List<Vote> votes = new ArrayList<>();

	public static ElectionBuilder anElection() {
		return new ElectionBuilder();
	}

	public static ElectionBuilder testElection() {
		List<Candidate> testCandidates = List.of(
				new Candidate(random(10, true, false)),
				new Candidate(random(10, true, false)),
				new Candidate(random(10, true, false)),
				new Candidate(random(10, true, false))
		);
		return anElection()
				.withTitle(random(10, true, false))
				.withDescription(random(10, true, false))
				.withCandidates(testCandidates.toArray(Candidate[]::new));
	}

	public ElectionBuilder withTitle(String title) {
		this.title = title;
		return this;
	}

	public ElectionBuilder withDescription(String description) {
		this.description = description;
		return this;
	}

	public ElectionBuilder withVotes(Vote... votes) {
		this.votes = List.of(votes);
		return this;
	}

	public ElectionBuilder withCandidates(Candidate... candidates) {
		this.candidates = List.of(candidates);
		return this;
	}

	public Election build() {
		Election election = new Election(title, description, candidates);
		votes.forEach(election::addVote);
		return election;
	}

	public Election build(ElectionRepository electionRepository) {
		Election election = new Election(title, description, candidates);
		votes.forEach(election::addVote);
		return electionRepository.save(election);
	}
}
