package com.javarush.khmelov.lesson14.entity;

import com.javarush.khmelov.lesson14.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;

    private String login;

    private String password;

    private Role role;

    public String getImage() { //TODO move to DTO
        return "image-" + id;
    }

}
