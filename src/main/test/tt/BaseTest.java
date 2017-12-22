package tt;

import org.hhs.record.dao.pojo.Code;
import org.hhs.record.dao.pojo.Jiu;
import org.hhs.record.dao.pojo.Record;
import org.hhs.record.dao.pojo.User;
import org.hhs.record.utils.SqlOperation;
import org.hhs.record.utils.StringUtils;

public class BaseTest {
    public static void main(String...args) throws Exception {
        String sql1 = "INSERT INTO [order]([id],[u_id],[j_id],[c_id],[j_time]) VALUES('0csc98d4de54483ca1d48ae93a6ea63c','asdf','42e6611383e3400c97f391da0075740f','743f3ea715074a1fa191cffe911d412c','2017-12-17 18:50:25')";

        SqlOperation resultToMap = new SqlOperation();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("id", "123456");
//        Map<String, Object> map1 = new HashMap<String, Object>();
//        map1.put("id", "123");
//        resultToMap.UPDATEoperation(map, map1, Order.class);
        //增加
//        try {
//            for (int i = 0; i < 100; i++) {
//                Order order = new Order();
//                order.setC_id(StringUtils.getUUID());
//                order.setId(StringUtils.getUUID());
//                order.setJ_id(StringUtils.getUUID());
//                order.setJ_time(StringUtils.dateFormatToStr());
//                resultToMap.INSERToperation(ObjectToMap.objectToMap(order), Order.class);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        //查询
//        String select = "select * from [order] where id='13b5a86f6de94f77bcc5abc081d4f293'";
//        List<Object> list = resultToMap.GETObjectLists(select);
//        System.out.println(list.toString());
//        String select = "select * from [order] limit 5";
//        List<Object> list = resultToMap.GETObjectLists(select);
//        System.out.println(list.size());
//        Connection connection = DatabaseSource.generateConnection();
//        connection.setAutoCommit(true);
//        Statement statement = connection.createStatement();
//        String sql = "update [order] set id='123' where id='101759a74c134d1fbd58ae885e8a4e64'";
//        statement.executeUpdate(sql);
//        connection.close();
        Jiu jiu = new Jiu();
        jiu.setId(StringUtils.getUUID());
        Code code = new Code();
        code.setCode(StringUtils.getUUID());
        code.setId(StringUtils.getUUID());
//        User user = new User();
//        user.setAddress("hubbb");
//        user.setId(StringUtils.getUUID());
//        user.setTel("126677");
//        user.setUsername("mike");

//        resultToMap.INSERToperation(ObjectToMap.objectToMap(user), User.class);
//        String str = "select * from [order] o inner join [user] u on o.u_id=u.id where o.c_id='7a9fa1a2b7744489b428e2bf0235ea70'";
//        String str = "select * from ([order] inner join [user] on order.u_id=user.id ) inner join [jiu] on o.j_id=jiu.id where order.c_id='7a9fa1a2b7744489b428e2bf0235ea70'";
//        String str = "select * from [user]";
//        List<Object> list = resultToMap.GETObjectLists(str);
//        System.out.println(list);
        resultToMap.DELETEoperation(null, Record.class);
        resultToMap.DELETEoperation(null, Code.class);
        resultToMap.DELETEoperation(null, User.class);
        resultToMap.DELETEoperation(null, Jiu.class);
    }
}
