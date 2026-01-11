package gl.rvrs.voteapp.election.util;

import gl.rvrs.voteapp.util.PagedData;
import gl.rvrs.voteapp.election.domain.Election;
import gl.rvrs.voteapp.election.dto.ElectionResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ElectionMapper {

	public ElectionResponse toElectionResponse(Election election) {
		return new ElectionResponse(
				election.getId(),
				election.getTitle(),
				election.getDescription(),
				election.getCandidates()
		);
	}

	public <T> PagedData<T> toPagedData(Page<T> page) {
		return new PagedData<>(
				page.getContent(),
				page.getNumber(),
				page.getSize(),
				page.getTotalPages(),
				page.getTotalElements()
		);
	}
}
