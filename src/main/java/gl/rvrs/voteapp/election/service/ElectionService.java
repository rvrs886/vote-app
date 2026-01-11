package gl.rvrs.voteapp.election.service;

import gl.rvrs.voteapp.util.PageRequest;
import gl.rvrs.voteapp.util.PagedData;
import gl.rvrs.voteapp.election.domain.Election;
import gl.rvrs.voteapp.election.dto.ElectionDto;
import gl.rvrs.voteapp.election.dto.VoteDto;

public interface ElectionService {

	PagedData<Election> getElectionsPaged(PageRequest pageRequest);

	Election getElectionById(Object electionId);

	Election createElection(ElectionDto electionDto);

	void submitVote(Object electionId, VoteDto voteDto);

	void deleteElection(Object electionId);
}
