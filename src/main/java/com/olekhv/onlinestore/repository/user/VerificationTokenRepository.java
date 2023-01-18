package com.olekhv.onlinestore.repository.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.olekhv.onlinestore.entity.user.VerificationToken;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}
