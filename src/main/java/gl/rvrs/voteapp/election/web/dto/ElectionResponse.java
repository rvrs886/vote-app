package gl.rvrs.voteapp.election.web.dto;

import gl.rvrs.voteapp.election.domain.Candidate;

import java.util.List;

public record ElectionResponse(Long id, String title, String description, List<CandidateResponse> candidates) {
}
