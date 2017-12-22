package org.hhs.record.controller;

import lombok.extern.slf4j.Slf4j;
import org.hhs.record.dao.pojo.Code;
import org.hhs.record.dao.pojo.Jiu;
import org.hhs.record.dao.pojo.Record;
import org.hhs.record.dao.pojo.User;
import org.hhs.record.service.CodeService;
import org.hhs.record.service.JiuService;
import org.hhs.record.service.RecordService;
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
    @Autowired
    private UserService userService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private JiuService jiuService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private SqlOperation sqlOperation;

    @RequestMapping("jiu.do")
    public String toJiu(){
        return "/addJiu";
    }

    @RequestMapping("user.do")
    public String toUser(){
        return "/addUser";
    }

    @RequestMapping("batchInsertOrder.do")
    @ResponseBody
    public String batchInsert(@RequestParam("file") MultipartFile mfile,Record order){//u_id,j_id
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
            while (!org.springframework.util.StringUtils.isEmpty(temp = br.readLine())){
                listCode.add(temp);
            }
        } catch (IOException e) {
            log.error("解析code失败",e);
        }
        Code code = null;
        for (String str : listCode){
            code = new Code();
            String codeUUid = StringUtils.getUUID();
            code.setId(codeUUid);
            code.setCode(str);
            try {
                if (codeService.insert(code) != 1){
                    log.error("插入CODE表失败{}", code.getId());
                }
            } catch (Exception e) {
                log.error("插入CODE表失败", e);
            }
            order.setRId(StringUtils.getUUID());
            order.setRCId(codeUUid);
            order.setROTime(StringUtils.dateFormatToStr());
            try {
                if (recordService.insert(order) != 1){
                    log.error("插入ORDER表失败{}", code.getId());
                }
            } catch (Exception e) {
                log.error("插入ORDER表失败", e);
            }
        }

        return "success";
    }

    @RequestMapping("getUserListApi.do")
    @ResponseBody
    public List<User> getUserLists() throws Exception {
        return userService.getAll();
    }


    @RequestMapping("getJiuListApi.do")
    @ResponseBody
    public List<Jiu> getJiuLists() throws Exception {
        return jiuService.getAll();
    }

    @RequestMapping("queryByCode.do")
    @ResponseBody
    public List<Map<String, Object>> queryOrderInfo(String code) throws Exception {

//        List<Object> listCodes = sqlOperation.GETObjectLists("select * from [code] where code="+"'"+code+"'");
//        String id = "";
//        if (listCodes.size() == 0){
//            return Collections.emptyList();
//        }else {
//            Map<String, Object> map = (Map) listCodes.get(0);
//            id = (String) map.get("id");
//        }
//        String sql = "select * from [order] o inner join [user] u on o.u_id=u.id where o.c_id='"+id+"'";
//        List<Object> objectList = sqlOperation.GETObjectLists(sql);
//        for (Object obj : objectList){
//            Map<String, Object> map = (Map<String, Object>) obj;
//            String jId = (String) map.get("j_id");
//            String jiuSql = "select * from [jiu] where id='"+jId+"'";
//            List<Object> objectList1 = sqlOperation.GETObjectLists(jiuSql);
//            Map<String, Object> tempMap = (Map<String, Object>) objectList1.get(0);
//            map.put("j_name", tempMap.get("j_name"));
//        }
        return recordService.getRecordByCodeOrName(code);
    }

    //order表增加
    @RequestMapping("insertOrder.do")
    @ResponseBody
    public String insertOrder(Record order) throws Exception {
        order.setRId(StringUtils.getUUID());
//        sqlOperation.INSERToperation(ObjectToMap.objectToMap(order), Record.class);
        return "success";
    }



    //code表增加
    @RequestMapping("insertCode.do")
    @ResponseBody
    public String insertCode(Code code) throws Exception {
//        sqlOperation.INSERToperation(ObjectToMap.objectToMap(code), Code.class);
        return "success";
    }

    //jiu表增加
    @RequestMapping("insertJiu.do")
    @ResponseBody
    public String insertJiu(Jiu jiu) throws Exception {
        jiu.setId(StringUtils.getUUID());
        if (jiuService.selectByName(jiu.getJName()) == null) {
            if (jiuService.insert(jiu) == 1) {
                return "添加成功";
            } else {
                return "添加出错";
            }
        }else {
            return "酒名已存在";
        }
    }
    //用户表增加
    @RequestMapping("insertUser.do")
    @ResponseBody
    public String insertUser(User user) throws Exception {
        user.setId(StringUtils.getUUID());
        if (userService.selectByName(user.getName()) == null) {
            if (userService.insert(user) == 1) {
                return "添加成功";
            } else {
                return "添加失败";
            }
        }else {
            return "用户已存在";
        }
    }

}
