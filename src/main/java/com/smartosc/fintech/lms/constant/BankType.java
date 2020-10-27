package com.smartosc.fintech.lms.constant;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BankType {
  public static Map<Integer, String> TYPES = Stream
      .of(new Object[][]{{1, "private"}, {2, "public"},})
      .collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));
}
