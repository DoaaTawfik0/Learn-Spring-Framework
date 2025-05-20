package com.rest.webservices.restful_web_services.jpa;


import com.rest.webservices.restful_web_services.exception.UserNotFoundException;
import com.rest.webservices.restful_web_services.user.Post;
import com.rest.webservices.restful_web_services.user.User;
import com.rest.webservices.restful_web_services.user.UserDaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PostRepository postRepository;


    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers() {
        return repository.findAll();
    }

//    @GetMapping("/jpa/users/{id}")
//    public User retrieveUser(@PathVariable int id) throws UserNotFoundException {
//        User userById = userDaoService.findUserById(id);
//
//        if (userById == null) {
//            throw new UserNotFoundException("id: " + id + " not found");
//        }
//        return userById;
//    }


    //using HATEOS
    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) throws UserNotFoundException {
        Optional<User> user = repository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("id: " + id + " not found");
        }

        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = repository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }


    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostsForAUser(@PathVariable int id) {

        Optional<User> user = repository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("id: " + id + " not found");
        }

        return user.get().getPosts();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Post> CreatePostForAUser(@PathVariable int id, @Valid @RequestBody Post post) {
        Optional<User> user = repository.findById(id);

        if (user.isEmpty())
            throw new UserNotFoundException("id: " + id + " not found");

        post.setUser(user.get());
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{postId}") // append the new post's ID
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build(); // no body, only Location header
    }



//    @GetMapping("/jpa/users/{id}/posts/{}")
//    public Post retrievePostForAUser(@PathVariable int userId,@PathVariable int postId) {
//
//        Optional<User> user = repository.findById(id);
//
//        if (user.isEmpty()) {
//            throw new UserNotFoundException("id: " + id + " not found");
//        }
//
//        return user.get().getPosts();
//    }


}
