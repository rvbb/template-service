package com.rvbb.api.template.dto.adapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankListDemo {
    private long id;
    private String bankName;
    private int type;
    private Date created;
}
