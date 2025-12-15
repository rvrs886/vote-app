package gl.rvrs.voteapp.election.repository;

import gl.rvrs.voteapp.election.domain.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<Election, Long> {
}
