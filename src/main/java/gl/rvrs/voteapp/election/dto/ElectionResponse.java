package gl.rvrs.voteapp.election.dto;

import gl.rvrs.voteapp.election.domain.Candidate;

import java.util.List;

public record ElectionResponse(Long id, String title, String description, List<Candidate> candidates) {
}
