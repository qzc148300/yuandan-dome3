package com.jk.controller;

import com.jk.entity.Order;
import com.jk.entity.Tree;
import com.jk.entity.User;
import com.jk.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @RequestMapping("show")
    public String show(){
        return "show";
    }
    @RequestMapping("look")
    public String look(){
        return "look";
    }

    @RequestMapping("findTree")
    @ResponseBody
    public List<Tree> findTree(HttpSession session){
        return ticketService.findTree(session);
    }


    @RequestMapping("findtickettable")
    @ResponseBody
    public HashMap<String, Object> findtickettable(Integer page, Integer rows){
        return ticketService.findtickettable( page, rows);
    }

    @RequestMapping("findordertable")
    @ResponseBody
    public HashMap<String, Object> findordertable(Integer page, Integer rows, Order order){
        return ticketService.findordertable( page, rows,order);
    }

    @RequestMapping("buypiao")
    @ResponseBody
    public void buypiao(Integer id, Integer numprice,Integer aa){
         ticketService.buypiao( id, numprice,aa);
    }

    @RequestMapping("daochu")
    @ResponseBody
    public void daochu(HttpServletResponse response, HttpServletRequest request) throws IOException {
        ticketService.daochu( response, request);
    }

    @RequestMapping("findUser")
    @ResponseBody
    public User findUser(){
       return ticketService.findUser( );
    }

    @RequestMapping("ff")
    @ResponseBody
    public void ff(){
        System.out.println("1111");
    }

}
