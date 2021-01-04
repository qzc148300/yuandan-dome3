package com.jk.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;
@Data
public class Ticket  {
    private Integer id;

    private String start;

    private String stop;

    private String seat;

    private Date time;

    private Integer tid;

    private Integer count;

    private Double price;

    private String tname;
}