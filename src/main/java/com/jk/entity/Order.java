package com.jk.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Document(collection = "t_order")
public class Order {
    @Id
    private String id;

    private String start;

    private String stop;

    private String seat;

    private Date time;


    private Integer count;

    private Double price;

    private String tname;

    private Integer numprice;

    private String username;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ordertime;

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startdate;
    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date enddate;
    @Transient
    private Integer flag;
}
