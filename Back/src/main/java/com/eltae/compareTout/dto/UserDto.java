package com.eltae.compareTout.dto;

import lombok.*;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDto implements Cloneable {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String resetToken;

    public UserDto clone() throws CloneNotSupportedException {
        return (UserDto) super.clone();
    }

}