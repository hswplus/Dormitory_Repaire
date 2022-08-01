package sample.util;

import javafx.collections.ObservableList;
import sample.domain.Administrator;
import sample.domain.Dormitory_ad;
import sample.domain.Student;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 1.find:根据学号返回对应的行集合
 * 2.findCondition:根据查找文本框内容返回符合查找条件的集合
 * 3.findAll:返回数据库用户表中所有数据的集合
 * 4.delete:根据学号删除对应的行
 * 5.insert:由于设置学号为主键，则判断是否存在有同学号行，否则insert返回boolean
 * 6.update:由于设置用户为主键，则判断是否存在有同学号行，否则update返回boolean
 * 7.login:根据身份identity、sno、password在数据库中查找是否有该用户，有则返回1
 */

public class JdbcUserTools {
    private ObservableList<Student> list;
    private int index;
    public JdbcUserTools(ObservableList<Student> list, int index){
        this.list = list;
        this.index = index;
    }

    static JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());

    /**1.find:根据学号返回对应的行集合*/
/*    public static List<Administrator> find_Ad(String sno){
        String sql = "select * from 管理员 where sno = ?";
        List<Administrator> findAdmin = template.query(sql, new BeanPropertyRowMapper<Administrator>(Administrator.class), sno);
        return findAdmin;
    }*/
    public static List<Student> find_S(String sno){
        String sql = "select * from 学生 where sno = ?";
        List<Student> findStu = template.query(sql, new BeanPropertyRowMapper<Student>(Student.class), sno);
        return findStu;
    }
/*
    public static List<Dormitory_ad> find_A(String d_id){
        String sql = "select * from 宿管 where d_id = ?";
        List<Dormitory_ad> findDor_d = template.query(sql, new BeanPropertyRowMapper<Dormitory_ad>(Dormitory_ad.class), d_id);
        return findDor_d;
    }*/

/**
 * 2.findCondition:根据查找文本框内容返回符合查找条件的集合
 */
    public static List<Student> findCondition(Map<String, String> condition){
        String sql = "select * from 学生 where 1 = 1 ";
        StringBuffer sb = new StringBuffer(sql);
        Set<String> keySet = condition.keySet();
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {
            String value = condition.get(key);
            if(value != null && !"".equals(value)){
                sb.append(" and " + key + " like ? ");
                params.add("%" + value + "%");
            }
        }
        return template.query(sb.toString(), new BeanPropertyRowMapper<Student>(Student.class), params.toArray());
    }

    /**
     *  3.findAll:返回数据库用户表中所有数据的集合
    */
    public static List<Student> findAll(){
        String sql = "select * from 学生";
        List<Student> stu = template.query(sql, new BeanPropertyRowMapper<Student>(Student.class));
       // System.out.println(stu+"\n");
        return stu;
    }
 /**
  * 4.delete:根据学号删除对应的行
     */
    public static void delete(String sno){
            String sql = "delete from 学生 where sno = ?";
            template.update(sql, sno);

    }

    /**
     * 5.insert:由于设置学号为主键，则判断是否存在有同学号行，否则insert返回boolean
     */
    public static boolean insert( String name, String sno, String sex, String apname, String apno, String aproom
            , String collage, String password, String age, String pno, String email) {

        List<Student> list = JdbcUserTools.find_S(sno);
        if(list != null && !list.isEmpty()) {
            return false;
        }
        String sql = " insert into 学生 values(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, name, sno,sex,apname,apno,aproom,collage,password,age,pno,email);
        return true;
    }

    /**
     * 6.update:由于设置用户为主键，则判断是否存在有同学号行，否则update返回boolean
    */
    public static boolean update(String name, String sno, String sex, String apname, String apno, String aproom
            , String collage, String password, String age, String pno, String email, Student clickStu, String usedSno){
        if(clickStu == null) usedSno = sno;
        List<Student> student = JdbcUserTools.find_S(sno);
        if(student == null || student.isEmpty() || clickStu.getSno().equals(student.get(0).getSno())){
            String sql = "update 学生 set name = ?,sno = ?, sex = ? ,apname = ? ," +
                    "apno = ?,aproom = ?,collage = ?, password = ?,age = ?,"+
                    " pno = ?, email = ?  where sno = ?";
            template.update(sql, name, sno, sex, apname, apno, aproom,collage, password, age, pno, email,usedSno);
            return true;
        }
        else
            return false;
    }


    /**
    *7.login:根据身份identity、sno、password在数据库中查找是否有该用户，有则返回1
     * */
    public static int login(String identity, String sno, String password) {
        String sql="select count(*) from \" + identity + \" where sno = ? and password = ?";
        if(identity =="学生")
        {
        sql = "select count(*) from " + identity + " where sno = ? and password = ?";
        }
        if(identity =="管理员")
        {
            sql = "select count(*) from " + identity + " where sno = ? and password = ?";
        }
       /* else if(identity =="宿管")
        {
            sql = "select count(*) from " + identity + " where d_id = ? and password = ?";
        }*/

        int count = template.queryForObject(sql, int.class, sno, password);
        return count;
    }
}
