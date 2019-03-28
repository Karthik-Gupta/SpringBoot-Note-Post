
package com.karthik.springrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karthik.springrest.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
