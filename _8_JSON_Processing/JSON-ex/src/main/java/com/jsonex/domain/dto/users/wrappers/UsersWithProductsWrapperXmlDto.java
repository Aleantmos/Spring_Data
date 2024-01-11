package com.jsonex.domain.dto.users.wrappers;

import com.jsonex.domain.dto.users.UserWithProductsDto;
import com.jsonex.domain.dto.users.UserWithProductsXmlDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class UsersWithProductsWrapperXmlDto {
    private Integer usersCount;
    private List<UserWithProductsDto> users;

//    public UsersWithProductsWrapperJsonDto(List<UserWithProductsDto> users) {
//        this.users = users;
//        this.usersCount = users.size();
//    }
}
