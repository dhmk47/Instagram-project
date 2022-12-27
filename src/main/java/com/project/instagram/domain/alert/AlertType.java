package com.project.instagram.domain.alert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
@Data
public class AlertType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertTypeCode;

    @NotNull
    private String alertContent;
}