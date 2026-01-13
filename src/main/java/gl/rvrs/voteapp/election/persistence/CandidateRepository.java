package gl.rvrs.voteapp.election.persistence;

import gl.rvrs.voteapp.election.domain.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
