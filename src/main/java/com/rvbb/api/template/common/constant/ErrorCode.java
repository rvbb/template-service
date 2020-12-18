package com.rvbb.api.template.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCode {
    // Define business exception code from 5000
    public static final int NOT_ALLOWED = 5001;
    public static final int EXPECTONE_GETTOOMANY = 5002;
    public static final int EMPTY_RESULT = 5003;
}
