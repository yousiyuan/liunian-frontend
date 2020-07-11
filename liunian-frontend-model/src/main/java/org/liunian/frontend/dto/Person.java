package org.liunian.frontend.dto;

import lombok.Data;

import java.util.Date;
import java.util.StringJoiner;

@Data
public class Person {

    private Integer code;

    private String name;

    private Date birthday;

}
