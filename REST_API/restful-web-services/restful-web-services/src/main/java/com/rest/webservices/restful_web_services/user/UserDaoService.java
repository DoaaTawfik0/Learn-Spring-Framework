package com.rest.webservices.restful_web_services.user;


import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();
    private static int count = 1;

    static {
        users.add(new User(count++, "Adam", LocalDate.now().minusYears(30)));
        users.add(new User(count++, "Tarek", LocalDate.now().minusYears(25)));
        users.add(new User(count++, "Doaa", LocalDate.now().minusYears(20)));
    }

    public List<User> findAll() {
        return users;
    }

    public User findUserById(int id) {
        Predicate<? super User> predicate = user -> user.getId() == id;
//        return users.stream().filter(predicate).findFirst().get();
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    public User addUser(User user) {
        user.setId(count++);
        users.add(user);

        return user;
    }

    public void deleteUserById(int id) {
        Predicate<? super User> predicate = user -> user.getId() == id;

        users.removeIf(predicate);
    }


}
