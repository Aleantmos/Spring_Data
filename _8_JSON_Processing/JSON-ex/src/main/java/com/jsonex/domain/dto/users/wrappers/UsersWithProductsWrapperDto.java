package com.jsonex.domain.dto.users.wrappers;

import com.jsonex.domain.dto.users.UserWithProductsDto;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersWithProductsWrapperDto {
    @XmlAttribute
    private Integer count;
    @XmlElement(name = "user")
    private List<UserWithProductsDto> users;

    public UsersWithProductsWrapperDto(List<UserWithProductsDto> users) {
        this.users = users;
        this.count = users.size();
    }
}
