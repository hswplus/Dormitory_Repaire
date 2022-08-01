package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sample.domain.Maintenance_record;
import sample.domain.Student;
import sample.util.Tool;

import java.util.List;

/**
 * 1、学生可查看本宿舍的报修情况,根据公寓名称、公寓号、房间号查找
 * 表格中字段有学号、姓名、楼栋名称、A/B栋、房间号、联系方式以及报修日期
 *2、管理员查询全部报修情况页面
 */
public class BudingInformationFrame implements EventHandler<ActionEvent> {


    private String identity;
    private Maintenance_record maintenance_record;
    private Student student;
    private ObservableList<Maintenance_record> list;

    public BudingInformationFrame(){}

    public BudingInformationFrame(Student student, String identity) {
        this.student = student;
        this.identity = identity;
    }


    @Override
    public void handle(ActionEvent event) {

        ObservableList<Maintenance_record> list = FXCollections.observableArrayList(); //表格对象
        TableView<Maintenance_record> tableView = new TableView<Maintenance_record>();//视图对象

        //新建列对象
        TableColumn<Maintenance_record, String> idColumn = new TableColumn<>("序号");
        TableColumn<Maintenance_record, String> snoColumn = new TableColumn<>("学号");
        TableColumn<Maintenance_record, String> snameColumn = new TableColumn<>("姓名");
        TableColumn<Maintenance_record, String> apnameColumn = new TableColumn<>("楼栋名称");
        TableColumn<Maintenance_record, String> apnoColumn = new TableColumn<>("楼栋号");
        TableColumn<Maintenance_record, String> aproomColumn = new TableColumn<>("房间号");
        TableColumn<Maintenance_record, String> repair_contentColumn = new TableColumn<>("报修内容");
        TableColumn<Maintenance_record, String> pnoColumn = new TableColumn<>("联系方式");
        TableColumn<Maintenance_record, String> re_apdateColumn = new TableColumn<>("报修日期");

        //数值值的列    其中最后的()内的字段为数据库Maintenance_record表列名
        snoColumn.setCellValueFactory(new PropertyValueFactory<Maintenance_record, String>("sno"));
        snameColumn.setCellValueFactory(new PropertyValueFactory<Maintenance_record, String>("name"));
        apnameColumn.setCellValueFactory(new PropertyValueFactory<Maintenance_record, String>("ap_name"));
        apnoColumn.setCellValueFactory(new PropertyValueFactory<Maintenance_record, String>("ap_no"));
        aproomColumn.setCellValueFactory(new PropertyValueFactory<Maintenance_record, String>("ap_room"));
        repair_contentColumn.setCellValueFactory(new PropertyValueFactory<Maintenance_record, String>("repair_content"));
        pnoColumn.setCellValueFactory(new PropertyValueFactory<Maintenance_record, String>("pno"));
        re_apdateColumn.setCellValueFactory(new PropertyValueFactory<Maintenance_record, String>("repair_data"));


        //自己定义的borrowTools的居中操作setCenter方法，实现数据在表格中居中
       /* borrowTools.setCenter(idColumn);
        borrowTools.setCenter(snoColumn);
        borrowTools.setCenter(snameColumn);
        borrowTools.setCenter(apnameColumn);
        borrowTools.setCenter(apnoColumn);
        borrowTools.setCenter(aproomColumn);
        borrowTools.setCenter(repair_contentColumn);
        borrowTools.setCenter(re_apdateColumn);*/

        //设置每列的表格宽度
       // idColumn.setPrefWidth(40);
        snoColumn.setPrefWidth(100);
        snameColumn.setPrefWidth(100);
        apnameColumn.setPrefWidth(100);
        apnoColumn.setPrefWidth(50);
        aproomColumn.setPrefWidth(90);
        repair_contentColumn.setPrefWidth(240);
        re_apdateColumn.setPrefWidth(100);
        pnoColumn.setPrefWidth(100);

        //非数据值的列
        //id自动补充
        idColumn.setPrefWidth(60);
        idColumn.setCellFactory((col) -> {
            TableCell<Maintenance_record, String> cell = new TableCell<Maintenance_record, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);

                    if(!empty){
                        int rowIndex = this.getIndex() + 1;
                        this.setText("  " + String.valueOf(rowIndex));
                    }
                }
            };
            return cell;
        });

        // Top顶部：查询功能控件的引入与设置
        Button bt1 = new Button("查询");
        Button bt2 = new Button("还原");
        Label sno = new Label("学号");
        Label sname = new Label("姓名");
        Label apname = new Label("楼栋名称");
        Label apno = new Label("楼栋号");
        Label repair_date = new Label("报修日期");
        Label pno= new Label("联系方式");
        TextField snof = new TextField();
        TextField snamef = new TextField();
        TextField apnamef = new TextField();
        TextField apnof = new TextField();//楼栋号
        TextField repair_datef = new TextField();
        TextField pnof = new TextField();//联系方式
        snof.setPrefColumnCount(6);
        snamef.setPrefColumnCount(4);
        apnamef.setPrefColumnCount(4);//楼栋名称
        apnof.setPrefColumnCount(4);//楼栋号
        repair_datef.setPrefColumnCount(6);//报修时间
        pnof.setPrefColumnCount(7);//联系电话
        FlowPane flowPane = new FlowPane(7, 0);
        flowPane.setPadding(new Insets(15, 0, 15, 0));
        flowPane.getChildren().addAll(sno, snof , sname, snamef, apname, apnamef, apno, apnof,pno,pnof, repair_date, repair_datef);
        HBox hBox_bt=new HBox(50);
        hBox_bt.setAlignment(Pos.CENTER);
        hBox_bt.getChildren().addAll(bt1, bt2);
        BorderPane bp = new BorderPane();
        bp.setTop(flowPane);
        bp.setCenter(hBox_bt);

        // Center中间：将表格放入视图中展示
        tableView.setItems(list);
        HBox hBox = new HBox();
        hBox.getChildren().add(tableView);
        hBox.setHgrow(tableView, Priority.ALWAYS);
        VBox vBox = new VBox();
        vBox.getChildren().add(hBox);
        bp.setBottom(vBox);
        bp.setStyle("-fx-background-color:rgba(144,248,255,0.35)");
        //将表头添加在表格中 根据身份不同则功能（表头）不同
        tableView.getColumns().addAll(idColumn, snoColumn, snameColumn, apnameColumn, apnoColumn, aproomColumn,repair_contentColumn,re_apdateColumn ,pnoColumn);

        //添加数据到表格
        if(identity=="学生"){
            //添加数据到表格
            List<Maintenance_record> all = Tool.findM_record(student.getApname(),student.getApno(),student.getAproom());
            /**
             * 学生可查看本宿舍的报修情况,根据公寓名称、公寓号、房间号查找*/
            for (Maintenance_record maintenance_record : all) {
                list.add(new Maintenance_record(maintenance_record.getId(),maintenance_record.getSno()
                        , maintenance_record.getName(),  maintenance_record.getAp_name(), maintenance_record.getAp_no()
                        , maintenance_record.getAp_room(),maintenance_record.getRepair_content()
                        ,maintenance_record.getRepair_data(),maintenance_record.getPno()));
            }
            //定义查询维修情况事件对象，并将学号、姓名、楼栋名称、楼栋号、报修日期、联系方式的查询文本框传入事件中操作
            Maintenance_record_FindFrame maintenance_record_findFrame = new Maintenance_record_FindFrame(list, snof, snamef, apnamef, apnof,repair_datef,pnof,student,identity, student.getApname(),student.getApno(),student.getAproom());
            bt1.setOnAction(maintenance_record_findFrame);

            //还原操作，恢复查询数据库borrow表中的所有数据，实现过程：
            //1.表面清空文本框内容以及tableView表格内数据
            //2.将数据库borrow表中所有数据重新添加到tableView表格中
            bt2.setOnAction(e -> {
                snof.setText(null);
                snamef.setText(null);
                apnamef.setText(null);
                apnof.setText(null);
                repair_datef.setText(null);
                pnof.setText(null);
                list.clear();
               /* List<Maintenance_record> all1 = Tool.findM_record();*/
                for (Maintenance_record maintenance_record : all) {
                    list.add(new Maintenance_record(maintenance_record.getId(), maintenance_record.getSno(),
                            maintenance_record.getName(), maintenance_record.getAp_name(),
                            maintenance_record.getAp_no(),maintenance_record.getAp_room(),maintenance_record.getRepair_content(), maintenance_record.getRepair_data(),
                            maintenance_record.getPno()));
                }
            });
        }
        else {
            /**
             * 管理员的查看报修情况*/
            List<Maintenance_record> all = Tool.findAll();
            for (Maintenance_record maintenance_record1 : all) {
                list.add(new Maintenance_record(maintenance_record1.getId(), maintenance_record1.getSno()
                        , maintenance_record1.getName(), maintenance_record1.getAp_name(), maintenance_record1.getAp_no()
                        , maintenance_record1.getAp_room(), maintenance_record1.getRepair_content()
                        , maintenance_record1.getRepair_data(), maintenance_record1.getPno()));
            }
            //定义查询维修情况事件对象，并将学号、姓名、楼栋名称、楼栋号、报修日期、联系方式的查询文本框传入事件中操作
            Maintenance_record_FindFrame maintenance_record_findFrame = new Maintenance_record_FindFrame(list, snof, snamef, apnamef, apnof,repair_datef,pnof);
            bt1.setOnAction(maintenance_record_findFrame);

            //还原操作，恢复查询数据库borrow表中的所有数据，实现过程：
            //1.表面清空文本框内容以及tableView表格内数据
            //2.将数据库borrow表中所有数据重新添加到tableView表格中
            bt2.setOnAction(e -> {

                snof.setText(null);
                snamef.setText(null);
                apnamef.setText(null);
                apnof.setText(null);
                repair_datef.setText(null);
                pnof.setText(null);
                list.clear();

                List<Maintenance_record> all1 = Tool.findAll();
                for (Maintenance_record maintenance_record : all1) {
                    list.add(new Maintenance_record(maintenance_record.getId(), maintenance_record.getSno(),
                            maintenance_record.getName(), maintenance_record.getAp_name(),
                            maintenance_record.getAp_no(),maintenance_record.getAp_room(),maintenance_record.getRepair_content(), maintenance_record.getRepair_data(),
                            maintenance_record.getPno()));
                }
            });
        }




        Scene scene = new Scene(bp, 1000, 500);
        Stage stage = new Stage();
        stage.setScene(scene);
        scene.getStylesheets().add("style.css");
        scene.setCursor(Cursor.CLOSED_HAND);
        stage.setTitle("查看报修情况");
        stage.getIcons().add(new Image("/sample/image/information.png"));
        stage.show();

    }

}