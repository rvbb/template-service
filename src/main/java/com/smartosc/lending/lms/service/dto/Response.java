package com.smartosc.lending.lms.service.dto;

import com.smartosc.lending.lms.service.util.IConst;
import com.smartosc.lending.lms.service.util.JsonUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Response<T> {

	@ApiModelProperty(notes = "result of request: true/false")
	private boolean success;
	@ApiModelProperty(notes = "error code")
	private int code;
	@ApiModelProperty(notes = "data of response")
	private T data;
	@ApiModelProperty(notes = "message")
	private String message;

	public static <T> Response<T> success(T data) {
		return Response.<T>builder().success(true).code(IErrorCode.OK).message(IConst.RESPONSE_OK)
				.data(data).build();
	}

	public static <T> Response<T> success(int code, String message, T data) {
		return Response.<T>builder().success(true).code(code).message(message).data(data).build();
	}

	public static <T> Response<T> successEmptyData(String message, int code) {
		return Response.<T>builder().success(true).message(message).code(code).build();
	}

	public static <T> Response<T> fail(String message, int code) {
		return Response.<T>builder().success(false).message(message).code(code).build();
	}

	@SuppressWarnings("unchecked")
	public static <T> Response<T> fail(String body) {
		return JsonUtils.unJson(body.getBytes(), Response.class);
	}

	public static <T> Response<T> unkownReason(T data) {
		return Response.<T>builder().success(true).code(IErrorCode.UNKNOWN_REASON)
				.message(IConst.RESPONSE_UNKNOWN).data(data).build();
	}
}
