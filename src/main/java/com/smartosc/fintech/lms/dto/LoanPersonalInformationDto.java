package com.smartosc.fintech.lms.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
//@MappedSuperclass
public class LoanPersonalInformationDto {

    private Integer id;
    private String fullName;
    private String phoneNumber;
    private Date dateOfBirth;

    @NotBlank
    private String emailAddress;
    @NotBlank
    private String address;
}
