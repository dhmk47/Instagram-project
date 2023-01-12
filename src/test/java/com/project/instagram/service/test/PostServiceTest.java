package com.project.instagram.service.test;

import com.project.instagram.domain.test.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    void savePostTest() {
        // given
        Post post = Post.builder()
                .content("게시글4")
                .build();

        // when
        postService.savePost(post);
        // then

    }
}