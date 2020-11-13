package com.smartosc.fintech.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author minhnd3@smartosc.com
 * @since 11-Nov-20
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResultDto implements Serializable {
    private boolean isSuccessful;
    private boolean isFailed;
    private Object data;
}
