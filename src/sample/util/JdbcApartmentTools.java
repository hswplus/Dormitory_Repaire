package sample.util;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sample.domain.Apartemt;
import sample.domain.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * 1.findCondition:根据查找文本框内容返回符合查找条件的集合
 * 2.findAll:返回数据库用户表中所有数据的集合
 */
public class JdbcApartmentTools {

    static JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());


    /**
     * 1.findCondition:根据查找文本框内容返回符合查找条件的集合
     */
    public static List<Apartemt> findCondition(Map<String, String> condition){
        String sql = "select * from apartment where 1 = 1 ";
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
        return template.query(sb.toString(), new BeanPropertyRowMapper<Apartemt>(Apartemt.class), params.toArray());
    }

    /**
     *  2.findAll:返回数据库用户表中所有数据的集合
     */
    public static List<Apartemt> findAll(){
        String sql = "select * from apartment";
        List<Apartemt> list = template.query(sql, new BeanPropertyRowMapper<Apartemt>(Apartemt.class));
        //System.out.println(list);
        return list;
    }

    /**
     * 3.insert:
     */
    public static boolean insert( String sno, String name,  String ap_name, String ap_no, String ap_room
            , String repair_content, String repair_data, String pno) {
        String sql = " insert into maintenance_record values(?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println(sno+name+ap_name+ap_no+ap_room+repair_content+repair_data+pno);
        template.update(sql, sno, name,ap_name,ap_no,ap_room,repair_content,repair_data,pno);
        return true;
    }
    /**
     * 4.insert_Apartment:
     */

    public static boolean insert_Apartment( String id,String apname, String apno,String build_year,String repair_amount) {
        String sql = " insert into apartment values(?,?, ?, ?, ?)";
        template.update(sql,id, apname,apno,build_year,repair_amount);
        return true;
    }
    /**
            * 5.delete:根据姓名和内容删除对应的行
     */
    public static void delete(String id){
        String sql = "delete from apartment where id = ?  ";
        template.update(sql,id);

    }

    public static boolean update(String id,String apname, String apno,  String build_year, String repair_amount,Apartemt click_apartemt, String Ap_id) {
        if(click_apartemt == null) Ap_id = id;
        List<Apartemt> apartemts = JdbcApartmentTools.find(id);
        if(apartemts == null || apartemts.isEmpty() || click_apartemt.getId().equals(apartemts.get(0).getId())){
            String sql = "update apartment set id =?,apname = ?, apno =  ? , build_year = ? , repair_amount = ?  where id = ? ";
            template.update(sql,id, apname, apno,build_year ,repair_amount,Ap_id);
            return true;
        }else
            return false;
    }

    private static List<Apartemt> find(String id) {
        String sql = "select * from apartment where id=? ";
        List<Apartemt> list = template.query(sql, new BeanPropertyRowMapper<Apartemt>(Apartemt.class),id);
        return list;
    }

}

