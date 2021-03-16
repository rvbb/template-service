package com.rvbb.api.template.service.adapter;

import com.rvbb.api.template.dto.adapter.Cif;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "cif", url = "${cif.apis}")
public interface CifClient {

    @GetMapping
    Cif getCif(@Param("base") String base);

    @PostMapping(produces = "application/json")
    Cif createNewCif(@RequestBody Cif request);

}
