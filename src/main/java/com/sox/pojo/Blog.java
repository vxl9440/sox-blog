package com.sox.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Blog implements Serializable {
    private String blogID;
    private String authorID;
    private Date date;
    private String content;
    private int view;
    private int like;
    private String title;
    private String introduction;
}
