package com.example.funE.Services.Impl;

import com.example.funE.Dtos.AddressDto;
import com.example.funE.Dtos.UserDto;
import com.example.funE.Entities.Address;
import com.example.funE.Entities.User;
import com.example.funE.Exceptions.ResourceNotFoundException;
import com.example.funE.Repositories.AddressRepo;
import com.example.funE.Repositories.UserRepo;
import com.example.funE.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto getUserByID(Integer userId) {
        return this.userToDto(this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId)));
    }

    @Override
    public UserDto updateUser(String name, String avatar, String coverAvatar, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
        user.setAvatar(avatar);
        user.setName(name);
        if(!coverAvatar.equals("")){
            user.setCover_avatar(coverAvatar);
        }

        this.userRepo.save(user);
        User updatedUser = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
        return this.userToDto(updatedUser);
    }

    @Override
    public List<String> checkUserEmail(String email) {
        return this.userRepo.checkUserEmail(email);
    }

    @Override
    public String checkPasswordByEmail(String email) {
        return this.userRepo.checkUserPasswordByEmail(email);
    }

    @Override
    public UserDto getUserDetailByEmail(String email) {
        User user = this.userRepo.getUserDetailByEmail(email);
        return userToDto(user);
    }

    @Override
    public UserDto registerSeller(UserDto userDto, AddressDto addressDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        if(!user.isSeller()) {
            Address address = dtoToAddress(addressDto);
            Address addressSaved = this.addressRepo.save(address);
            List<Address> addressList = user.getAddress();
            addressList.add(addressSaved);
            user.setAddress(addressList);
            user.setBankAccountHolderName(userDto.getBankAccountHolderName());
            user.setBankAccountNumber(userDto.getBankAccountNumber());
            user.setBankIdentifierCode(userDto.getBankIdentifierCode());
            user.setBirthDate(userDto.getBirthDate());
            user.setGender(userDto.getGender());
            user.setSeller(true);
        }
        User savedUser = this.userRepo.save(user);
        return userToDto(savedUser);
    }

    @Override
    public List<UserDto> searchUsers(String key) {
        List<User> users = this.userRepo.searchUsers(key);
        if(users != null) {
            List<UserDto> userDtoList = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
            return userDtoList;
        }
        return null;
    }

    @Override
    public int registerNewUser(String email, String password, String nameInit) {
        return this.userRepo.registerNewUser(email, password, nameInit);
    }

    private User dtoToUser(UserDto userDto) {
        return this.modelMapper.map(userDto, User.class);
    }

    private UserDto userToDto(User user) {
        return this.modelMapper.map(user,UserDto.class);
    }

    private Address dtoToAddress(AddressDto addressDto) {
        return this.modelMapper.map(addressDto, Address.class);
    }
}
