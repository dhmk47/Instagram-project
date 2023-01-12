package com.project.instagram.domain.test;

import com.project.instagram.domain.time.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@Table(name = "postMst")
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postCode;

    @NotNull
    @Column(name = "postContent")
    private String content;
}