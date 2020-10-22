package com.smartosc.lending.lms.service.util;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface IConst {

	public static Map<Integer, String> BANKLISTDEMO_TYPES = Stream
			.of(new Object[][] { { 1, "private" }, { 2, "public"}, })
			.collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));

	public static String LOGGER_REQUEST_URL = "url not recognized";
	public static String RESPONSE_OK = "OK";
	public static String RESPONSE_FAIL = "FAIL";
	public static String RESPONSE_SERVER_PROBLEM = "Server problem";
	public static String RESPONSE_EXCEPTION = "Exception in process";
	public static String RESPONSE_UNKNOWN = "Unknown";
	public static String MSG_RESOURCE_NOTFOUND = "Resource not found";
	public static String INVALID_ARGUMENT = "Invalid argument in method";
	public static String INVALID_INPUT_PARAMETER = "Invalid input parameter";

	public static String JSON_TO_BYTE_EXCEPTION	= "Could not convert a JSON to bytes: ";
	public static String BYTE_TO_JSON_EXCEPTION	= "Could not convert a bytes to JSON ";
}
