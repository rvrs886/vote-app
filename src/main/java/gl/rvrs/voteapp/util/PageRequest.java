package gl.rvrs.voteapp.util;

public record PageRequest(int pageNumber, int pageSize, int offset, Sort sort) {
}
