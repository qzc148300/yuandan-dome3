package com.jk.service.impl;

import com.jk.dao.TicketMapper;
import com.jk.entity.Order;
import com.jk.entity.Ticket;
import com.jk.entity.Tree;
import com.jk.entity.User;
import com.jk.service.TicketService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Tree> findTree(HttpSession session) {
        User user=ticketMapper.finduser();
        session.setAttribute("user",user);
        int pid =0;
        List<Tree> list  = tree(pid);
        return list;
    }

    @Override
    public HashMap<String, Object> findtickettable(Integer page, Integer rows) {
        Integer start =(page-1)*rows;
        int total = ticketMapper.findticketcount();
        List<Ticket> list = ticketMapper.findtickettable(start,rows);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("total", total);
        map.put("rows", list);
        return map;
    }

    @Override
    public void buypiao(Integer id, Integer numprice,Integer aa) {
        ticketMapper.updateuser(numprice);
        ticketMapper.updateticket(id,aa);
        Ticket ticket = ticketMapper.findticket(id);

        Order order = new Order();
        order.setPrice(ticket.getPrice());
        order.setSeat(ticket.getSeat());
        order.setStart(ticket.getStart());
        order.setStop(ticket.getStop());
        order.setNumprice(numprice);
        order.setCount(aa);
        order.setTname(ticket.getTname());
        order.setUsername("张三");
        order.setTime(ticket.getTime());
        order.setOrdertime(new Date());
        mongoTemplate.save(order);
    }

    @Override
    public HashMap<String, Object> findordertable(Integer page, Integer rows, Order order) {
        Integer start =(page-1)*rows;
        Query query = new Query();
        long count = mongoTemplate.count(query, Order.class);

        //排序
        if (order.getFlag()!=null) {
            if(order.getFlag()==1){//升序
                Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC,"numprice"),new Sort.Order(Sort.Direction.ASC,"ordertime"));
                query.with(sort);
            }else{//降序
                Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC,"numprice"),new Sort.Order(Sort.Direction.DESC,"ordertime"));
                query.with(sort);
            }
        }


        Criteria ordertime = Criteria.where("ordertime");
        if (order.getStartdate()!=null){
            ordertime.gte(order.getStartdate());
        }
        if (order.getEnddate()!=null){
            ordertime.lte(order.getEnddate());
        }
        if (order.getStartdate()!=null||order.getEnddate()!=null){
            query.addCriteria(ordertime);
        }

        query.skip(start).limit(rows);
        List<Order> list = mongoTemplate.find(query,Order.class);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("total", count);
        map.put("rows", list);
        return map;
    }

    @Override
    public void daochu(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Query query = new Query();
        List<Order> list = mongoTemplate.find(query,Order.class);
        Integer count = 0;
        Integer sumprice = 0;

        //1、创建excel工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        //2、创建sheet页
        XSSFSheet sheet = workbook.createSheet("用户信息");
        //3、创建行：标题、下标从0开始
        String[] title = {"出发站","到达站","席别","车次类型","付款","购买票数","下单时间"};
        XSSFRow row = sheet.createRow(0);
        //给第一行的第一个单元格赋值
        for (int i = 0; i < title.length; i++) {
            row.createCell(i).setCellValue(title[i]);
        }

        for (int i = 0; i < list.size(); i++) {
            //获取对象
            Order order = list.get(i);
            //创建行
            XSSFRow row1 = sheet.createRow(i+1);//1、2、3...
            //创建列、创建单元格
            row1.createCell(0).setCellValue(order.getStart());
            row1.createCell(1).setCellValue(order.getStop());

            row1.createCell(2).setCellValue(order.getSeat());
            row1.createCell(3).setCellValue(order.getTname());
            row1.createCell(4).setCellValue(order.getNumprice());
            row1.createCell(5).setCellValue(order.getCount());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sdf.format(order.getOrdertime());
            row1.createCell(6).setCellValue(format);
							//计算总价
							count+=order.getCount();
                            sumprice+=order.getNumprice();
        }
						XSSFRow row2 = sheet.createRow(list.size()+1);
						row2.createCell(0).setCellValue("总票数");
						row2.createCell(5).setCellValue(count);
                        XSSFRow row3 = sheet.createRow(list.size()+2);
                        row3.createCell(0).setCellValue("总价");
                        row3.createCell(5).setCellValue(sumprice);

        //下载
        //设置Content-Disposition :attchment
        response.setHeader("Content-Disposition", "attchment;filename="+ URLEncoder.encode("用户信息表格.xlsx", "utf-8"));
        ServletOutputStream outputStream = response.getOutputStream();//获取输出流
        workbook.write(outputStream);
        outputStream.close();//关闭资源
    }

    @Override
    public User findUser() {
        return ticketMapper.finduser();
    }


    private List<Tree> tree(int pid) {
        List<Tree> list = ticketMapper.findTree(pid);
        for (Tree tree : list) {
            Integer id = tree.getId();
            List<Tree> list2 = tree(id);
            tree.setChildren(list2);
        }
        return list;
    }
}
