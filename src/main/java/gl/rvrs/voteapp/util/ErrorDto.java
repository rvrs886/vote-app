package gl.rvrs.voteapp.util;

public record ErrorDto(String errorCode, Object result) {

	public static final String DEFAULT_ERROR_CODE = "OTHER_ERROR";

	public ErrorDto(Object result) {
		this(DEFAULT_ERROR_CODE, result);
	}
}
