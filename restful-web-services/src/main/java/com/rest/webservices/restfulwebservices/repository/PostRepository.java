package com.rest.webservices.restfulwebservices.repository;

import com.rest.webservices.restfulwebservices.HelloWorld.users.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
