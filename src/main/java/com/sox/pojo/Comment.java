package com.sox.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {
    private String blogID;
    private String commenterID;
    private String commentContent;
    private Date date;
}
