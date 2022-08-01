package sample.util;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sample.domain.Feedback;
import sample.util.JDBCUtils;

import java.util.List;

public class JdbcFeedbackTools {
    static JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());


    public static boolean insert( String s_name,  String content, String email, String s_tel , String f_time)
    {
        String sql = " insert into feedback values(null,?, ?, ?, ?, ?)";
        template.update(sql, s_name, content,email,s_tel,f_time);
        return true;
    }

    public static List<Feedback> findAll() {
        String sql = "select * from feedback ";
        List<Feedback> list = template.query(sql, new BeanPropertyRowMapper<Feedback>(Feedback.class));
        return list;
    }

    public static List<Feedback> find(String name) {
        String sql = "select * from feedback where s_name = ? ";
        List<Feedback> list = template.query(sql, new BeanPropertyRowMapper<Feedback>(Feedback.class), name);
        return list;
    }
   /**
            * 4.delete:根据姓名和内容删除对应的行
     */
    public static void delete(String s_name,String content){
        String sql = "delete from feedback where s_name = ? and content = ?  ";
        template.update(sql,s_name, content);

    }
}
