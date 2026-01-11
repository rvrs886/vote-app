package gl.rvrs.voteapp.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageRequestMapper {

	public Pageable toPageable(PageRequest pageRequest) {
		Sort.Direction direction = switch (pageRequest.sort().direction()) {
			case ASC -> Sort.Direction.ASC;
			case DESC -> Sort.Direction.DESC;
		};
		return org.springframework.data.domain.PageRequest.of(
				pageRequest.pageNumber(),
				pageRequest.pageSize(),
				direction,
				pageRequest.sort().properties()
		);
	}
}
