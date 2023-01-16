package pl.kul.onlinestore.entity.user;

import lombok.Data;

@Data
public class PasswordModel {
    private String email;
    private String oldPassword;
    private String newPassword;
    private String matchingPassword;
}
