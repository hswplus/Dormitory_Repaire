package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import sample.domain.Maintenance_record;
import sample.domain.Student;
import sample.util.Tool;

import java.util.HashMap;
import java.util.List;

/**
 * 改事件仅管理员可操作
 * 查找搜索功能 实现根据输入到文本框的内容查询相应行
 * 文本框包括维修情况的学号、姓名、楼栋名称、楼栋号、报修日期、联系方式
 * 可单个查询，也可多个交集查询
 * 提交即查询，还原即初始化
 */
public class Maintenance_record_FindFrame implements EventHandler<ActionEvent> {
    // 将llist, snof, snamef, apnamef, apnof, repair_datef,pnof 传入到该类中 （均为查询条文本框）
    private ObservableList<Maintenance_record> list;
    private TextField snof;
    private TextField snamef;
    private TextField apnamef;
    private TextField apnof;
    private TextField repair_datef;
    private TextField pnof;
    Student stu;
    private String identity;
    private String apname;
    private String apno;
    private String aproom;
    //student.getApname(),student.getApno(),student.getAproom()
    public Maintenance_record_FindFrame(ObservableList<Maintenance_record> list, TextField snof, TextField snamef, TextField apnamef,
                                        TextField apnof, TextField repair_datef, TextField pnof ) {
        this.list = list;
        this.snof = snof;
        this.snamef = snamef;
        this.apnamef = apnamef;
        this.apnof = apnof;
        this.repair_datef = repair_datef;
        this.pnof = pnof;
    }


    public Maintenance_record_FindFrame(ObservableList<Maintenance_record>
                                        list, TextField snof, TextField snamef, TextField apnamef,
                                        TextField apnof, TextField repair_datef, TextField pnof ,
                                        Student stu,String identity, String apname, String apno, String aproom) {
        this.list = list;
        this.snof = snof;
        this.snamef = snamef;
        this.apnamef = apnamef;
        this.apnof = apnof;
        this.repair_datef = repair_datef;
        this.pnof = pnof;
        this.stu =stu;
        this.identity =identity;
        this.apname=apname;
        this.apno=apno;
        this.aproom= aproom;

    }

    @Override
    public void handle(ActionEvent event) {

        //创建一个map集合condition key和value分别为查询字段名与查询值
        HashMap<String, String> condition = new HashMap<String, String>(){
            {put("sno", snof.getText());}
            {put("name", snamef.getText());}
            {put("ap_name", apnamef.getText());}
            {put("ap_no", apnof.getText());}
            {put("repair_data", repair_datef.getText());}
            {put("pno", pnof.getText());}
        };



            if (identity=="学生"){
                //根据查询条件找到符合条件的List集合
                List<Maintenance_record> find = Tool.findCondition(condition);
                List<Maintenance_record> all= Tool.findM_record_S(find,this.apname,this.apno,this.aproom);
         /**
                 * 学生可查看本宿舍的报修情况,根据公寓名称、公寓号、房间号查找*/
                for (Maintenance_record maintenance_record : all) {
                    list.clear();
                    list.add(new Maintenance_record(maintenance_record.getId(),maintenance_record.getSno()
                            , maintenance_record.getName(),  maintenance_record.getAp_name(), maintenance_record.getAp_no()
                            , maintenance_record.getAp_room(),maintenance_record.getRepair_content()
                            ,maintenance_record.getRepair_data(),maintenance_record.getPno()));
                }
                System.out.println(list);
            }
            else {
                //根据查询条件找到符合条件的List集合
                List<Maintenance_record> find = Tool.findCondition(condition);
                //先清空list再添加符合要求的行到list中，实现条件查询
                list.clear();
                for (Maintenance_record maintenance_record : find) {
                    list.add(new Maintenance_record(maintenance_record.getId(), maintenance_record.getSno(),
                            maintenance_record.getName(), maintenance_record.getAp_name(),
                            maintenance_record.getAp_no(), maintenance_record.getAp_room(), maintenance_record.getRepair_content(), maintenance_record.getRepair_data(),
                            maintenance_record.getPno()));
                }
            }
    }
}