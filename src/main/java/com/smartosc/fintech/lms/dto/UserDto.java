package com.smartosc.fintech.lms.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto extends AuditDto {
    Long id;
    String userName;
    String password;
}
