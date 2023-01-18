package com.olekhv.onlinestore.repository.user;

import com.olekhv.onlinestore.entity.user.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}
