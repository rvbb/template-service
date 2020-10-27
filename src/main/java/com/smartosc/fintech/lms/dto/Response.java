package com.smartosc.fintech.lms.dto;

import com.smartosc.fintech.lms.constant.ErrorCode;
import com.smartosc.fintech.lms.constant.MessageCode;
import com.smartosc.fintech.lms.util.JsonUtils;
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
		return Response.<T>builder().success(true).code(ErrorCode.OK).message(MessageCode.RESPONSE_OK)
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
		return Response.<T>builder().success(true).code(ErrorCode.UNKNOWN_REASON)
				.message(MessageCode.RESPONSE_UNKNOWN).data(data).build();
	}
}
