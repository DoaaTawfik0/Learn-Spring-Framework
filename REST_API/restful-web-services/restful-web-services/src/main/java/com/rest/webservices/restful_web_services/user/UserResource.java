package com.rest.webservices.restful_web_services.user;


import com.rest.webservices.restful_web_services.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserResource {

    @Autowired
    private UserDaoService userDaoService;


    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

//    @GetMapping("/users/{id}")
//    public User retrieveUser(@PathVariable int id) throws UserNotFoundException {
//        User userById = userDaoService.findUserById(id);
//
//        if (userById == null) {
//            throw new UserNotFoundException("id: " + id + " not found");
//        }
//        return userById;
//    }


    //using HATEOS
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) throws UserNotFoundException {
        User user = userDaoService.findUserById(id);

        if (user == null) {
            throw new UserNotFoundException("id: " + id + " not found");
        }

        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userDaoService.addUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userDaoService.deleteUserById(id);
    }


}
