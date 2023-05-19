package com.example.funE.Controllers;

import com.example.funE.Dtos.AddressDto;
import com.example.funE.Dtos.UserDto;
import com.example.funE.Services.FileService;
import com.example.funE.Services.UserService;
import com.example.funE.Utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/v1/")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @PostMapping("user/login")
    public ResponseEntity authenticateUser(@RequestBody UserDto userDto) {
        List<String> userEmail = this.userService.checkUserEmail(userDto.getEmail());

        if(userEmail.isEmpty() || userEmail == null) {
            return new ResponseEntity("Email does not exist!", HttpStatus.NOT_FOUND);
        }

        String hashed_password = this.userService.checkPasswordByEmail(userDto.getEmail());

        if(!BCrypt.checkpw(userDto.getPassword(), hashed_password)) {
            return new ResponseEntity("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(this.userService.getUserDetailByEmail(userDto.getEmail()), HttpStatus.OK);
    }

    @PostMapping("/user/register")
    public ResponseEntity<String> registerNewUser(@RequestParam("email") String email,
                                                  @RequestParam("password") String password) {
        List<String> userEmail = this.userService.checkUserEmail(email);

        if(userEmail.contains(email)){
            return new ResponseEntity("Email already exist!", HttpStatus.BAD_REQUEST);
        }

        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        String nameInit = new RandomUtil().randomString();
        int result = this.userService.registerNewUser(email, hashPassword, nameInit);

        if(result != 1) {
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/user/seller/register/{userId}")
    public ResponseEntity<UserDto> registerSeller(@Valid @RequestPart UserDto userDto,
                                                  @Valid @RequestPart AddressDto addressDto,
                                                  @PathVariable(name = "userId") Integer userId) {
        UserDto savedUser = this.userService.registerSeller(userDto, addressDto, userId);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // Update Profile
    @PutMapping("/user/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestParam("name") String name,
                                              @RequestParam("avatar") MultipartFile avatar,
                                              @RequestParam(value = "coverAvatar", required = false) MultipartFile coverAvatar,
                                              @PathVariable(name = "userId") Integer userId) throws IOException {
        String fileNameAvatar = this.fileService.uploadImage(path, avatar);
        String fileNameCoverAvatar = "";
        if(coverAvatar != null) {
            fileNameCoverAvatar = this.fileService.uploadImage(path, coverAvatar);
        }

        UserDto updatedUser = this.userService.updateUser(name, fileNameAvatar, fileNameCoverAvatar, userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping(value = "/user/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

    @GetMapping("/user/search")
    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam("key") String key) {
        List<UserDto> searchResult = this.userService.searchUsers("%"+key+"%");
        return ResponseEntity.ok(searchResult);
    }
}
