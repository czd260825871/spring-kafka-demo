package com.czd.demo.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author admin
 */
@Data
public class User {
    private String name;

    private Integer gender;

    private LocalDateTime birthday;
}
