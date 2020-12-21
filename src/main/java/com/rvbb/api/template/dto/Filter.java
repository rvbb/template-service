package com.rvbb.api.template.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Map;

@Getter
@Setter
public abstract class Filter {

    private Map<String, String> sorts;
    private int pageNum;
    private int pageSize;
    private int totalPage;
}
