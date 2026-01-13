package gl.rvrs.voteapp.common.web.paging;

import java.util.List;

public record PagedData<T>(List<T> content, int pageNumber, int pageSize, int totalPages, long totalElements) {
}
