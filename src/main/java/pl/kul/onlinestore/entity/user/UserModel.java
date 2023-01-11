package pl.kul.onlinestore.entity.user;

import lombok.*;
import pl.kul.onlinestore.entity.user.Gender;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserModel {
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotBlank(message = "This field can't be blank")
    private String email;
    @NotBlank(message = "This field can't be blank")
    private String password;
    private String matchingPassword;
}
