package gl.rvrs.voteapp.users.repo;

import gl.rvrs.voteapp.users.domain.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterRepository extends JpaRepository<Voter, Long> {
}
