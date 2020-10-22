package com.smartosc.lending.lms.service.dto.request;

import lombok.*;

@Getter @Setter
@Builder
public class BankListDemo {
    private String bankname;    
    private int type;
    private Long id;
}
