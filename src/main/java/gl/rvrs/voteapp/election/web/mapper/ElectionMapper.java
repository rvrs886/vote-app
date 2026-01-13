package gl.rvrs.voteapp.election.web.mapper;

import gl.rvrs.voteapp.common.web.paging.PagedData;
import gl.rvrs.voteapp.election.domain.Election;
import gl.rvrs.voteapp.election.web.dto.CandidateResponse;
import gl.rvrs.voteapp.election.web.dto.ElectionResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElectionMapper {

	public ElectionResponse toElectionResponse(Election election) {
		List<CandidateResponse> candidateResponses = election.getCandidates().stream()
				.map(candidate -> new CandidateResponse(candidate.getId(), candidate.getName()))
				.toList();
		return new ElectionResponse(
				election.getId(),
				election.getTitle(),
				election.getDescription(),
				candidateResponses
		);
	}

	public PagedData<ElectionResponse> toPagedElectionResponse(PagedData<Election> elections) {
		List<ElectionResponse> electionResponseList = elections.content().stream()
				.map(this::toElectionResponse)
				.toList();

		return new PagedData<>(
				electionResponseList,
				elections.pageNumber(),
				elections.pageSize(),
				elections.totalPages(),
				elections.totalElements()
		);
	}
}
