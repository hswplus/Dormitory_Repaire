package sample.util;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sample.domain.Apartemt;
import sample.domain.Maintenance_record;

import java.util.*;

public class Tool {
    static JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
    //管理员查看维修情况的权限
    public static List<Maintenance_record> findAll(){
        String sql = "select * from maintenance_record";
        List<Maintenance_record> list = template.query(sql, new BeanPropertyRowMapper<Maintenance_record>(Maintenance_record.class));
        //System.out.println(list);
        return list;
    }
    public static List<Maintenance_record> findCondition(Map<String, String> condition) {
        String sql = "select * from maintenance_record where 1 = 1 ";
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
        return template.query(sb.toString(), new BeanPropertyRowMapper<Maintenance_record>(Maintenance_record.class), params.toArray());
    }


    public static List<Maintenance_record> findM_record(String ap_name,String ap_no,String ap_room){
//        System.out.println(ap_name+ap_no+ap_room);
        String sql = "select * from maintenance_record where ap_name = ? and ap_no = ? and ap_room = ?";
        List<Maintenance_record> list = template.query(sql, new BeanPropertyRowMapper<Maintenance_record>(Maintenance_record.class), ap_name,
                ap_no,ap_room);
    //System.out.println(list);
        return list;
    }

/**
 * 查找出数据表apartment的非重复的公寓名称*/
    public static List<Apartemt> findApname(){
        String sql = "select DISTINCT apname from apartment ";
        List<Apartemt> list = template.query(sql, new BeanPropertyRowMapper<Apartemt>(Apartemt.class));
        return list;
    }

    public static List<Maintenance_record> findM_record_S(List<Maintenance_record> find,String apname,String apno,String aproom){

                for (int i= 0;i<find.size();i++)
                {
                    if (!(find.get(i).getAp_name().equals(apname)) && !(find.get(i).getAp_no().equals(apno))&&!(find.get(i).getAp_room().equals(aproom) ))
                    {
                               find.remove(i);
                                i -=1;
                     }
                System.out.println(find.size());
                }

        return find;
    }
}
