package gl.rvrs.voteapp.election.application;

import gl.rvrs.voteapp.election.domain.ElectionCreateData;
import gl.rvrs.voteapp.common.web.paging.PageRequest;
import gl.rvrs.voteapp.common.web.paging.PagedData;
import gl.rvrs.voteapp.election.domain.Election;
import gl.rvrs.voteapp.election.domain.VoteCreateData;
import gl.rvrs.voteapp.election.web.dto.VoteDto;

public interface ElectionService {

	PagedData<Election> getElectionsPaged(PageRequest pageRequest);

	Election getElectionById(Long electionId);

	Election createElection(ElectionCreateData electionCreateData);

	void submitVote(Long electionId, VoteCreateData voteCreateData);

	void deleteElection(Long electionId);
}
