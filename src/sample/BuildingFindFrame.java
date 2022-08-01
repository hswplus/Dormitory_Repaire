package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import sample.domain.Apartemt;
import sample.util.JdbcApartmentTools;
import java.util.HashMap;
import java.util.List;


    /**
     * 查找搜索功能 实现根据输入到文本框的内容查询相应行
     * 文本框包括公寓名称、公寓号、建立时间、维修次数
     * 可单个查询，也可多个交集查询
     * 提交即查询，还原即初始化
     */

    public class BuildingFindFrame implements EventHandler<ActionEvent> {
        //将list, apnamef, apnof, build_yearf, repair_amountf 穿入该类中（公寓名称、公寓号、建立时间、维修次数）
        public ObservableList<Apartemt> list;
        private TextField idf;
        private TextField apnamef;
        private TextField apnof;
        private TextField build_yearf;
        private TextField repair_amountf;
        public BuildingFindFrame(ObservableList<Apartemt> list,TextField idf, TextField apnamef,
                                 TextField apnof, TextField build_yearf, TextField repair_amountf){
            this.list = list;
            this.idf = idf;
            this.apnamef = apnamef;
            this.apnof = apnof;
            this.build_yearf = build_yearf;
            this.repair_amountf = repair_amountf;
        }

        @Override
        public void handle(ActionEvent event) {
            //创建一个map集合condition key和value分别为查询字段名与查询值
            HashMap<String, String> condition = new HashMap<String, String>(){
                {put("id", idf.getText());}
                {put("apname", apnamef.getText());}
                {put("apno", apnof.getText());}
                {put("build_year", build_yearf.getText());}
                {put("repair_amount", repair_amountf.getText());}
            };

            //根据查询条件找到符合条件的List集合
            List<Apartemt> findApartment = JdbcApartmentTools.findCondition(condition);

            //先清空list再添加符合要求的行到list中，实现条件查询
            list.clear();
            for (Apartemt apartemt : findApartment) {
                list.add(new Apartemt(null,apartemt.getId(),apartemt.getApname(),apartemt.getApno(),apartemt.getBuild_year()
                        ,apartemt.getRepair_amount()));
            }
        }
    }


