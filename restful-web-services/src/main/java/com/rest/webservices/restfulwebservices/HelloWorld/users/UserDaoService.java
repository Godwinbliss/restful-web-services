package com.rest.webservices.restfulwebservices.HelloWorld.users;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {
    // Method to retrieve all users
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 1;

    static {
        users.add(new User(usersCount++,"Adam", LocalDate.now().minusYears(30)));
        users.add(new User(usersCount++,"Eve", LocalDate.now().minusYears(29)));
        users.add(new User(usersCount++,"John", LocalDate.now().minusYears(20)));
    }

    public List<User> findAll() {
        return users;
    }

    public User findOne(@PathVariable int id){

        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().get();

    }

    public void deleteById(@PathVariable int id){
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        users.removeIf(predicate);

    }

    // Save details of a specific user

    public User save(User user){
        user.setId(++usersCount);
        users.add(user);
        return user;
    }

    // Retrieve details of a specific user
}
