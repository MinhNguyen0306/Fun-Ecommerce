package com.example.funE.Services;

import com.example.funE.Dtos.AddressDto;
import com.example.funE.Dtos.UserDto;

import java.util.List;

public interface UserService {

    UserDto getUserByID(Integer userId);
    UserDto updateUser(String name, String avatar, String coverAvatar, Integer userId);
    List<String> checkUserEmail(String email);
    String checkPasswordByEmail(String email);
    UserDto getUserDetailByEmail(String email);
    UserDto registerSeller(UserDto userDto, AddressDto addressDto, Integer userId);
    List<UserDto> searchUsers(String key);
    int registerNewUser(String email, String password, String nameInit);
}
