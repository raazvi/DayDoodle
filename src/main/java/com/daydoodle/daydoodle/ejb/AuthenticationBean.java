package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.UserDto;
import com.daydoodle.daydoodle.entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

@Stateless
public class AuthenticationBean {

    private static final Logger log = Logger.getLogger(AuthenticationBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public User login(String username, String password) {
        // Find the user by username
        User user = entityManager.find(User.class, username);

        // If user is not found, return null
        if (user == null) {
            log.warning("User not found for username: " + username);
            return null;
        }

        // Encrypt the provided password
        String encryptedPassword = encryptPassword(password);

        // Log the encrypted and stored passwords for debugging
        log.info("Provided password: " + password);
        log.info("Encrypted password: " + encryptedPassword);
        log.info("Stored password: " + user.getPassword());

        // Compare the encrypted passwords
        if (user.getPassword().equals(encryptedPassword)) {
            return user;
        } else {
            log.warning("Incorrect password for user: " + username);
            return null;
        }
    }


    public String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            log.warning("SHA-256 algorithm not found.");
            return null;
        }
    }
}
