package com.project.instagram.domain.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class TagType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagTypeCode;

    @NotNull
    private String tagTypeName;
}