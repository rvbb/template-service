package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDto> getUser(Pageable pageable);
    UserDto getUser(long id);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(long id, UserDto userDto);
    void deleteUser(long id);
}
