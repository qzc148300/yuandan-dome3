package com.jk.service;

import com.jk.entity.Order;
import com.jk.entity.Tree;
import com.jk.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface TicketService {
    List<Tree> findTree(HttpSession session);

    HashMap<String, Object> findtickettable(Integer page, Integer rows);

    void buypiao(Integer id, Integer numprice,Integer aa);

    HashMap<String, Object> findordertable(Integer page, Integer rows, Order order);

    void daochu(HttpServletResponse response, HttpServletRequest request) throws IOException;

    User findUser();
}
