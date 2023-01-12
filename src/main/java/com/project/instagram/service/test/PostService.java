package com.project.instagram.service.test;

import com.project.instagram.domain.test.Post;
import com.project.instagram.domain.test.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequiredArgsConstructor
@Service
public class PostService {

    private PostRepository postRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void savePost(Post post) {
        entityManager.persist(post);
    }
}
