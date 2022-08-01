package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.domain.Apartemt;
import sample.domain.Maintenance_record;
import sample.domain.Student;
import sample.util.JdbcApartmentTools;
import sample.util.JdbcUserTools;
import sample.util.Tool;
import sample.util.Tools;

import java.io.StringReader;
import java.util.Date;
import java.util.List;

/**
 * 学生报修页面
 * 表提交数据、
 *      要求实现：
 *          1、通过组合框选择对应的写入数据库
 *          2、需获取本机的时间作为报修时间
 */
public class RepairFrame implements EventHandler<ActionEvent> {

    // 将身份identity和学生对象student传到该类中
    private String identity;
    private Maintenance_record maintenance_record;
    private Student loginStu;
    private ObservableList<Maintenance_record> list;
    public RepairFrame(Student loginStu, String identity) {
        this.loginStu = loginStu;
        this.identity = identity;
    }
    public RepairFrame(Maintenance_record maintenance_record, String identity) {
        this.maintenance_record = maintenance_record;
        this.identity = identity;
    }

    @Override
    public void handle(ActionEvent event) {
        //用网格面板来排版 较为整齐且简易
        GridPane gp = new GridPane();
        BorderPane bp = new BorderPane();

        Label name = new Label("宿舍名:");

        //身份选择combobox

        /**
         * 该操作是给下拉框赋值
         * 如语句 String[] apname = {"明德苑", "博雅苑", "至道苑", "新民苑", "昌明苑"};//组合框选择身份的类型
        */
        List<Apartemt> all = Tool.findApname();
        int amount=all.size();
        String[] apname= new String[amount];
        for (int i=0;i<all.size();i++) {
            apname[i] =all.get(i).getApname();
        }
        ComboBox<String> cbo_name = new ComboBox<String>();//下拉框选择
        //ObservableList可以监听到这些改变,本质上是提供了一个监听器接口
        ObservableList<String> items_name = FXCollections.observableArrayList(apname);//下拉框可供的选择身份
        cbo_name.getItems().addAll(items_name);//将身份添加到组合框里
        cbo_name.setPrefWidth(120);//设置组合框的宽度
        cbo_name.setValue(loginStu.getApname());//设置组合框的默认值

        Label no = new Label("楼 栋 :");
        String[] apno = {"A", "B", "C", "D", "E"};//组合框选择身份的类型
        ComboBox<String> cbo_no = new ComboBox<String>();//下拉框选择
        //ObservableList可以监听到这些改变,本质上是提供了一个监听器接口
        ObservableList<String> items_no = FXCollections.observableArrayList(apno);//下拉框可供的选择身份
        cbo_no.getItems().addAll(items_no);//将身份添加到组合框里
        cbo_no.setPrefWidth(90);//设置组合框的宽度
        cbo_no.setValue(loginStu.getApno());//设置组合框的默认值

        Label room = new Label("房间号:");
        String[] aproom = {"101", "102", "103", "104", "105", "106", "107", "108", "109", "110" ,
                "201", "202", "203", "204", "205", "206", "207", "208", "209", "210",
                "301", "302", "303", "304", "305", "306", "307", "308", "309", "310",
                "401", "402", "403", "404", "405", "406", "407", "408", "409", "410",
                "501", "502", "503", "504", "505", "506", "507", "508", "509", "510",
                "601", "602", "603", "604", "605", "606", "607", "608", "609", "610",
                "701", "702", "703", "704", "705", "706", "707", "708", "709", "710",
                "801", "802", "803", "804", "805", "806", "807", "808", "809", "810"
        };//组合框选择身份的类型
        ComboBox<String> cbo_room = new ComboBox<String>();//下拉框选择
        //ObservableList可以监听到这些改变,本质上是提供了一个监听器接口
        ObservableList<String> items_room = FXCollections.observableArrayList(aproom);//下拉框可供的选择身份
        cbo_room.getItems().addAll(items_room);//将身份添加到组合框里
        cbo_room.setPrefWidth(90);//设置组合框的宽度
        cbo_room.setValue(loginStu.getAproom());//设置组合框的默认值
        Label pno =new Label("联系方式：");
        TextField pnof = new TextField();
        pnof.setPrefWidth(100);
        Label rep_content = new Label("请 输 入 报 修 内 容 ：");
        TextArea content = new TextArea();
        //content.setMaxHeight(185); // 设置多行输入框的最大高度
        //area.setMaxWidth(300); // 设置多行输入框的最大宽度
        content.setPrefSize(250, 250); // 设置多行输入框的推荐宽高
        content.setEditable(true); // 设置多行输入框能否编辑
        content.setPromptText("请输入报修内容"); // 设置多行输入框的提示语
        content.setWrapText(true); // 设置多行输入框是否支持自动换行。true表示支持，false表示不支持。
        //content.setPrefColumnCount(11); // 设置多行输入框的推荐列数
        //content.setPrefRowCount(3); // 设置多行输入框的推荐行数

        Button bt1 = new Button("提交");
        Button bt2 = new Button("取消");


       // 将有关联的label和ComboBox放在同一行
        HBox hBox1_name= new HBox(20);
        hBox1_name.getChildren().addAll(name,cbo_name);

        HBox hBox2_apno= new HBox(25);
        hBox2_apno.getChildren().addAll(no,cbo_no);

        HBox hBox3_aproom= new HBox(20);
        hBox3_aproom.getChildren().addAll(room,cbo_room);

        HBox hBox4_pno= new HBox();
        hBox4_pno.getChildren().addAll(pno,pnof);

        HBox hBox5_content= new HBox();
        hBox5_content.getChildren().addAll(rep_content,content);

        HBox hBox6_btn = new HBox(50);
        hBox6_btn.setAlignment(Pos.CENTER);
        hBox6_btn.getChildren().addAll(bt1,bt2);

        //将label放入网格面板排放

        gp.add(new Label("报修信息:"), 0, 0);
        gp.add(hBox1_name,0,1);
        gp.add(hBox2_apno,0,2);
        gp.add(hBox3_aproom,0,3);
        gp.add(hBox4_pno,0,4);
        gp.add(hBox5_content,0,5);
        gp.add(hBox6_btn,0,6);
        //gp.add(new Label("报修详情:"), 0, 5);
        gp.setHgap(10);//设置网格间的行间距
        gp.setVgap(5);//设置网格间的列间距
        gp.setPadding(new Insets(20, 10, 10, 10));//设置整个网格面板与边界的距离
        bp.setTop(gp);
        bp.setStyle("-fx-background-color: rgba(144,248,255,0.22)");
        String contents = content.getText();
        String dormitory_identity = cbo_name.getValue();

        //通过传来的身份，找出该学生的学号和姓名。
        String sno = loginStu.getSno();
        String sname = loginStu.getName();

        //System.out.println(loginStu.getSno());//输出一行学生信息
        // List<Student> students = JdbcUserTools.find_S(loginStu);
        //获取本机时间做为提交时间
        String repair_data= Tools.dateToString(new Date(), "yyyy-MM-dd");
        Scene scene = new Scene(bp, 470, 500);
        scene.getStylesheets().add("style.css");
        Stage stage = new Stage();
        stage.setScene(scene);
//提交事件事件
        bt1.setOnAction(e -> {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            String str1 =cbo_name.getValue() ;
            String str2 = loginStu.getApname();
           //核对公寓名称
            if (!str1.equals(str2) ){
                alert.setContentText("请正确选择您的公寓名称！");
                alert.setHeaderText(null);
                alert.showAndWait();
                return;
            }

            //核对公寓号
            str1 =cbo_no.getValue();
            str2 =loginStu.getApno();
            if (!str1.equals(str2)){
                alert.setContentText("请正确选择您的公寓号！");
                alert.setHeaderText(null);
                alert.showAndWait();
                return;
            }

            //核对房间号
            str1 =cbo_room.getValue();
            str2 =loginStu.getAproom();
            if (!str1.equals(str2)){
                alert.setContentText("请正确选择您的房间！！");
                alert.setHeaderText(null);
                alert.showAndWait();
                return;
            }

            //核对电话号码
            str1 =pnof.getText();
            str2 =loginStu.getPno();
            if (!str1.equals(str2)){
                alert.setContentText("请正确输入你的电话！！");
                alert.setHeaderText(null);
                alert.showAndWait();
                return;
            }

            //判断是否添加成功，内容不为空即可
            if(content.getText().isEmpty())
            {// 提交失败
                alert.setContentText("内容不能为空！请重新输入！");
                alert.setHeaderText(null);
                alert.showAndWait();
                return;

            }
            if(JdbcApartmentTools.insert(sno, sname,cbo_name.getValue(), cbo_no.getValue()
                    ,cbo_room.getValue(), content.getText(),repair_data,pnof.getText()))
            {
                //以下语句为表面添加数据，实际添加数据为 ↑  数据库sql语句
//                list.add(new Maintenance_record(null, sno, sname, cbo_name.getValue(),
//                        cbo_no.getValue(), cbo_room.getValue(),content.getText(), repair_data, pnof.getText()));
                alert.setTitle("报修提醒");
                alert.setHeaderText(null);
                alert.setContentText("提交成功！");
                alert.showAndWait();
            }
            //若添加成功，则关闭当前页面
            stage.close();

        });

        bt2.setOnAction(e -> stage.close());
    //用户点击按钮事件设置为应用程序模式，即关闭前不可操作界面外内容
        if ("学生".equals(identity)) stage.initModality(Modality.APPLICATION_MODAL);

        stage.setTitle("我要报修");
        stage.getIcons().add(new Image("/sample/image/information.png"));
        stage.show();
}
    }
