package com.jsonex.domain.dto.users.wrappers;

import com.jsonex.domain.dto.users.UserWithSoldProductsDto;
import com.jsonex.domain.dto.users.UserWithSoldProductsXmlDto;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersWithSoldProductsWrapperDto {
    @XmlElement(name = "user")
    private List<UserWithSoldProductsXmlDto> users;


    public UsersWithSoldProductsWrapperDto ofListOfUsersWithSoldProductsDto(List<UserWithSoldProductsDto> input) {
        users = UserWithSoldProductsDto.toUsersWithSoldProductsDto(input);

        return this;
    }
}


