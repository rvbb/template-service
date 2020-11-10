package com.smartosc.fintech.lms.dto;

import com.smartosc.fintech.lms.validator.EmailConstraint;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Getter
@Setter
//@MappedSuperclass
public class PersonalInformationDto {

    private Integer id;
    private String fullName;
    private String phoneNumber;
    private Date dateOfBirth;

    @EmailConstraint
    private String emailAddress;

    @Size(max = 100)
    @NotBlank
    private String address;
}
