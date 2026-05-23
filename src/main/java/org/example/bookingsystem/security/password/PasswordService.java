package org.example.bookingsystem.security.password;

import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.*;

@Service
public class PasswordService {
    public String hash(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public boolean matches(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
