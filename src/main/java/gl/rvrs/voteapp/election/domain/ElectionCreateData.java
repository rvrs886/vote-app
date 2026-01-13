package gl.rvrs.voteapp.election.domain;

import java.util.Set;

public record ElectionCreateData(String title, String description, Set<String> candidates) {
}
