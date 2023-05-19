package com.example.funE.Dtos;

import com.example.funE.Utils.GenderEnum;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String avatar;
    private String cover_avatar;
    private List<AddressDto> addressDtoList;
    @Nullable
    private boolean isSeller;
    private GenderEnum gender;
    private Date birthDate;
    private String bankAccountHolderName;
    @Nullable
    private int bankAccountNumber;
    private String bankIdentifierCode;
    private List<CartItemDto> cartItemDtoList;
}
