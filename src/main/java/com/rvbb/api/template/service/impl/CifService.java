package com.rvbb.api.template.service.impl;

import com.rvbb.api.template.common.util.LogIt;
import com.rvbb.api.template.dto.adapter.Cif;
import com.rvbb.api.template.service.adapter.CifClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CifService {

    private final CifClient cifClient;

    @LogIt
    public Cif getCif(String base) {
        return cifClient.getCif(base);
    }
}