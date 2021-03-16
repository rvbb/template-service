package com.rvbb.api.template.dto.adapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cif {
    private String id;
    private String description;
    private String base;
    private Boolean strict;
    private Boolean idInjection;
    private List<BankListDemo> banklist;
    private Date created;
}
