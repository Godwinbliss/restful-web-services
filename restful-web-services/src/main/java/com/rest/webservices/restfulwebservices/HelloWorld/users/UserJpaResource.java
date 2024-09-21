package com.rest.webservices.restfulwebservices.HelloWorld.users;

import com.rest.webservices.restfulwebservices.repository.PostRepository;
import com.rest.webservices.restfulwebservices.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public UserJpaResource(UserRepository userRepository) {
    }

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("id:" + id);
        EntityModel<User> model = EntityModel.of(user.get());  // HATEOAS- aLLows website to see data and perform actions
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        model.add(link.withRel("all-users"));
        return model;
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser (@Valid @RequestBody User user ){
        User saved = userRepository.save(user);
        // To return the url of a created resource
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/jpa/users/{id}/post")
    public List<Post> retrievePostForUser (@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new UserNotFoundException("id:" + id);
        return user.get().getPosts();
    }

    @PostMapping("/jpa/users/{id}/post")
    public ResponseEntity<Objects> createPostForUser (@PathVariable int id, @Valid @RequestBody Post post ){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new UserNotFoundException("id:" + id);
        post.setUser(user.get());
        Post savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }


}
