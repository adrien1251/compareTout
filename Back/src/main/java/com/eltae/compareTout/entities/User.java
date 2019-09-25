package com.eltae.compareTout.entities;

import com.eltae.compareTout.constants.Tables;
import lombok.*;

import javax.persistence.*;
import java.util.Random;


@Entity
@Table(name = Tables.USERS, uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String resetToken;

    public String getRandomPassword() {
        char[] chars = new char[26 + 26 + 10]; //
        int i = 0;
        for (char c = 'a'; c <= 'z'; c++, i++) { // on remplit avec les minuscules
            chars[i] = c;
        }
        for (char c = 'A'; c <= 'Z'; c++, i++) { // on remplit avec les majuscules
            chars[i] = c;
        }
        for (char c = '0'; c <= '9'; c++, i++) { // on remplit avec les chiffres
            chars[i] = c;
        }

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < 10 + random.nextInt(20 - 10); j++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }

        return sb.toString();
    }

    public User clone() throws CloneNotSupportedException {
        return (User) super.clone();
    }
}