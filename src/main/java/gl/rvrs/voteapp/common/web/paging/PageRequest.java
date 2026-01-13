package gl.rvrs.voteapp.common.web.paging;

public record PageRequest(int pageNumber, int pageSize, int offset, Sort sort) {
}
