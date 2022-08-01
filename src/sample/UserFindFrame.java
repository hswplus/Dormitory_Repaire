package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import sample.domain.Apartemt;
import sample.domain.Student;
import sample.util.JdbcUserTools;

import java.util.HashMap;
import java.util.List;

/**
 * 查找搜索功能 实现根据输入到文本框的内容查询相应行
 * 文本框包括公寓名称、公寓号、建立时间、维修次数
 * 可单个查询，也可多个交集查询
 * 提交即查询，还原即初始化
 */
public class UserFindFrame implements EventHandler<ActionEvent> {
    //将list, nf, sf, cof, sexf,apnamef,穿入该类中（姓名、学号、学院、性别、公寓名称）
    public ObservableList<Student> list;
    private TextField nf;
    private TextField sf;
    private TextField cof;
    private TextField sexf;
    private TextField apnamef;

    public UserFindFrame(ObservableList<Student> list, TextField nf, TextField sf, TextField cof, TextField sexf, TextField apnamef) {
        this.list = list;
        this.nf = nf;
        this.sf = sf;
        this.cof = cof;
        this.sexf = sexf;
        this.apnamef = apnamef;
    }

    @Override
    public void handle(ActionEvent event) {
//创建一个map集合condition key和value分别为查询字段名与查询值
        HashMap<String, String> condition = new HashMap<String, String>() {
            {
                put("name", nf.getText());
            }

            {
                put("sno", sf.getText());
            }

            {
                put("collage", cof.getText());
            }

            {
                put("sex", sexf.getText());
            }

            {
                put("apname", apnamef.getText());
            }
        };

        //根据查询条件找到符合条件的List集合
        List<Student> findStu = JdbcUserTools.findCondition(condition);

        //先清空list再添加符合要求的行到list中，实现条件查询
        list.clear();
        for (Student student : findStu) {
            list.add(new Student(student.getId(), student.getName(), student.getSno(), student.getSex(), student.getApname(), student.getApno(),
                    student.getAproom(), student.getCollage(), student.getPassword(), student.getAge(), student.getPno(), student.getEmail()));
        }
    }
}