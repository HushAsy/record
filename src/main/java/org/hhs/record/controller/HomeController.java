package org.hhs.record.controller;

import lombok.extern.slf4j.Slf4j;
import org.hhs.record.dao.pojo.Code;
import org.hhs.record.dao.pojo.Order;
import org.hhs.record.dao.pojo.User;
import org.hhs.record.service.UserService;
import org.hhs.record.utils.ObjectToMap;
import org.hhs.record.utils.SqlOperation;
import org.hhs.record.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("home")
@Slf4j
public class HomeController {
//    @Autowired
//    private UserService userService;
    @Autowired
    private SqlOperation sqlOperation;

    @RequestMapping("batchInsert.do")
    @ResponseBody
    public String batchInsert(@RequestParam("file") MultipartFile mfile,Order order){//u_id,j_id
        InputStream inputStream = null;
        try {
            inputStream = mfile.getInputStream();
        } catch (IOException e) {
            log.error("文件上传失败:", e);
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        List<String> listCode = new ArrayList<String>();
        try {
            String temp = "";
            while (org.springframework.util.StringUtils.isEmpty(temp = br.readLine())){
                listCode.add(temp);
            }
        } catch (IOException e) {
            log.error("解析code失败",e);
        }

        for (String str : listCode){
            Code code = new Code();
            String codeUUid = StringUtils.getUUID();
            code.setId(codeUUid);
            code.setCode(str);
            try {
                sqlOperation.INSERToperation(ObjectToMap.objectToMap(code), Code.class);
            } catch (Exception e) {
                log.error("插入CODE表失败", e);
            }
            order.setC_id(codeUUid);
            order.setId(StringUtils.getUUID());
            order.setO_time(StringUtils.dateFormatToStr());
            try {
                sqlOperation.INSERToperation(ObjectToMap.objectToMap(order), Order.class);
            } catch (Exception e) {
                log.error("插入ORDER表失败", e);
            }
        }

        return "success";
    }

    @RequestMapping("getUserListApi.do")
    @ResponseBody
    public List<Object> getUserLists() throws Exception {
        String sql = "select * from [user]";
        List<Object> list = sqlOperation.GETObjectLists(sql);
        return list;
    }


    @RequestMapping("getJiuListApi.do")
    @ResponseBody
    public List<Object> getJiuLists() throws Exception {
        String sql = "select * from [jiu]";
        List<Object> list = sqlOperation.GETObjectLists(sql);
        return list;
    }

    public List<Object> queryOrderInfo(String code) throws Exception {
        String sql = "select * from [order] o inner join [user] u on o.u_id=u.id where o.c_id='"+code+"'";
        List<Object> objectList = sqlOperation.GETObjectLists(sql);
        for (Object obj : objectList){
            Map<String, Object> map = (Map<String, Object>) obj;
            String jId = (String) map.get("j_id");
            String jiuSql = "select * from [jiu] where id='"+jId+"'";
            List<Object> objectList1 = sqlOperation.GETObjectLists(jiuSql);
            Map<String, Object> tempMap = (Map<String, Object>) objectList1.get(0);
            map.put("j_name", tempMap.get("j_name"));
        }
        return objectList;
    }

    //order表增加
    @RequestMapping("insertOrder.do")
    @ResponseBody
    public String insertOrder(Order order) throws Exception {
        sqlOperation.INSERToperation(ObjectToMap.objectToMap(order), Order.class);
        return "success";
    }



    //code表增加
    @RequestMapping("insertCode.do")
    @ResponseBody
    public String insertCode(Order order) throws Exception {
        sqlOperation.INSERToperation(ObjectToMap.objectToMap(order), Order.class);
        return "success";
    }
    //jiu表增加
    @RequestMapping("insertJiu.do")
    @ResponseBody
    public String insertJiu(Order order) throws Exception {
        sqlOperation.INSERToperation(ObjectToMap.objectToMap(order), Order.class);
        return "success";
    }
    //用户表增加
    @RequestMapping("insertUser.do")
    @ResponseBody
    public String insertUser(User User) throws Exception {
        sqlOperation.INSERToperation(ObjectToMap.objectToMap(User), User.class);
        return "success";
    }

}
