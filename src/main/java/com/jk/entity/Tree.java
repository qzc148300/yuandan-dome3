package com.jk.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Tree implements Serializable {
    private static final long serialVersionUID = 4238007327333910631L;

	private Integer id;

    private String text;

    private Integer pid;

    private String url;

    private String prower;
    
    private List<Tree> children;
    
    private boolean checked;
    
    
    

}