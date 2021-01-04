package com.jk.dao;

import com.jk.entity.Ticket;
import com.jk.entity.Tree;
import com.jk.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TicketMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Ticket record);

    int insertSelective(Ticket record);

    Ticket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ticket record);

    int updateByPrimaryKey(Ticket record);

    User finduser();

    List<Tree> findTree(int pid);

    int findticketcount();

    List<Ticket> findtickettable(@Param("start") Integer start, @Param("rows")Integer rows);

    void updateuser(Integer numprice);

    Ticket findticket(Integer id);

    void updateticket(@Param("id")Integer id, @Param("aa")Integer aa);
}