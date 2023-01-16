package com.project.instagram.service.test;

import com.project.instagram.domain.test.Post;
import com.project.instagram.domain.test.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.Transaction;

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

    @Transactional
    public void loadPost(Long postCode) {
        entityManager.find(Post.class, postCode);
    }

    @Transactional
    public void updatePost(Long postCode, String content) {
        Post post = entityManager.find(Post.class, postCode);

        post.setContent(content);
    }

    @Transactional
    public void deletePost(Long postCode) {
        Post post = entityManager.find(Post.class, postCode);
        entityManager.remove(post);
    }
}
