package com.testing_system.util;

import com.testing_system.entity.User;
import com.testing_system.exception.UserValidateException;
import com.testing_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserUtil {

    private final static Pattern EMAIL_PATTERN = Pattern.compile("^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$");
    private final static Pattern NAME_PATTERN = Pattern.compile("^[A-Z][a-z]{1,20}$");

    @Autowired
    private UserService userService;

    private boolean validateUserInfo(String firstName, String lastName, String email, User user) throws UserValidateException {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new UserValidateException("Wrong email");
        }
        if (!NAME_PATTERN.matcher(firstName).matches() || !NAME_PATTERN.matcher(lastName).matches()) {
            throw new UserValidateException("Wrong name");
        }
        if (user != null && !user.getFirstName().equals(firstName) && !user.getLastName().equals(lastName)) {
            throw new UserValidateException("User with such email already exist");
        }
        return true;
    }

    public User createUser(String firstName, String lastName, String email) throws UserValidateException {
        User user = userService.getByEmail(email);
        validateUserInfo(firstName, lastName, email, user);
        if (user == null) {
            user = new User(firstName, lastName, email);
            userService.save(user);
        }
        return user;
    }

    public void setBestResult(User user, float points) {
        if (points > user.getBestResult()) {
            user.setBestResult(points);
            userService.update(user);
        }
    }
}
