package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import sample.domain.Administrator;
import sample.domain.Dormitory_ad;
import sample.domain.Student;
import sample.util.JdbcUserTools;

import java.util.List;

/**
 * 该页面为登录页面，利用combobox选择身份，并将identify变量传入到后续页面
 * 用户和管理员信息分别存在数据库各自的表中
 * 登录时获取combobox的值来获取身份到表查看是否有该账号密码后弹出提示框
 */

public class LoginFrame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //文字标签与文本框
        final Label lab1 = new Label("用户名: ");
        final Label lab2 = new Label("密  码: ");
        lab1.setPrefWidth(60);
        lab1.setPrefHeight(30);
        lab2.setPrefWidth(60);
        lab2.setPrefHeight(30);
        final PasswordField pf = new PasswordField();
        pf.setPrefWidth(60);
        pf.setPrefHeight(30);
        final TextField tf = new TextField();
        tf.setPrefHeight(30);

        //身份选择combobox
        String[] mode = {"学生", "管理员",};//组合框选择身份的类型
        ComboBox<String> cbo = new ComboBox<String>();//下拉框选择
        //ObservableList可以监听到这些改变,本质上是提供了一个监听器接口
        ObservableList<String> items = FXCollections.observableArrayList(mode);//下拉框可供的选择身份
        cbo.getItems().addAll(items);//将身份添加到组合框里
        cbo.setPrefWidth(90);//设置组合框的宽度
        cbo.setValue("学生");//设置组合框的默认值
        //使用网格布局
        GridPane rootGP = new GridPane();
        rootGP.setPadding(new Insets(10, 8, 10, 8));
        rootGP.setHgap(15);//列间距？
        rootGP.setVgap(10);//行间距？

        //排版gridPane网格面板
        tf.setPromptText("输入用户名");
        rootGP.add(lab1, 0, 0);
        rootGP.add(tf, 1, 0);
        pf.setPromptText("输入密码");
        rootGP.add(lab2, 0, 1);
        rootGP.add(pf, 1, 1);
        Button bt1 = new Button("登录");
        Button bt2 = new Button("退出");
        bt1.setPadding(new Insets(8,16,8,16));
        bt2.setPadding(new Insets(8,16,8,16));
        bt1.setStyle("-fx-border-radius: 25;" + "-fx-background-radius: 25;");
        bt2.setStyle("-fx-border-radius: 25;" + "-fx-background-radius: 25;");
        rootGP.add(bt1, 1, 2);
        rootGP.add(bt2, 2, 2);
        rootGP.add(cbo, 2, 1);

    //登录事件事件
        bt1.setOnAction(e -> {
            String sno = tf.getText();
            String password = pf.getText();
            String identity = cbo.getValue();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("登录提醒");
            alert.setHeaderText(null);
            int count = JdbcUserTools.login(identity, sno, password);//若查找成功则返回1
/*
            System.out.println(count);
            System.out.println(identity);*/

         if (count == 1) {// 登录成功
                alert.setContentText("登录成功！");
                primaryStage.close();
                Button bt = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);//获取登录成功按钮

                //根据sno值在数据库中找到对应的行，获取对象并与身份一起传入到menuFrame中
             //List<Administrator> administrators = JdbcUserTools.find_Ad(sno);
                List<Student> students = JdbcUserTools.find_S(sno);
               // List<Dormitory_ad> dormitory_ads = JdbcUserTools.find_A(sno);

                if ("学生".equals(identity)) {
                    MenuFrame menuFrame = new MenuFrame(identity, students.get(0));
                    bt.setOnAction(menuFrame);
                }
                /*if("宿管".equals(identity))
                {
                    MenuFrame menuFrame1 = new MenuFrame(identity, dormitory_ads.get(0));
                    bt.setOnAction(menuFrame1);
                }*/
                else bt.setOnAction(new MenuFrame(identity,null));
            } else {  // 登录失败
                alert.setContentText("登录失败,请重新输入帐号密码！");
            }
            alert.showAndWait();
        });

        //退出事件
        bt2.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("退出成功!");
            alert.showAndWait();
            primaryStage.close();
        });

        //在顶部设置图片
        Image im = new Image("sample/image/logintop.png");
        ImageView imView = new ImageView();
        imView.setImage(im);
        imView.setFitWidth(400);
        imView.setFitHeight(130);

        //利用边界面板的set位置功能实现顶部放图片，中心放其他面板
        BorderPane rootPane = new BorderPane();
        rootPane.setTop(imView);
        rootPane.setCenter(rootGP);

        Scene scene = new Scene(rootPane, 400, 280);
            /*Application.setUserAgentStylesheet(getClass().getResource("style.css")
                .toExternalForm());*/
        scene.getStylesheets().add("style.css");
        rootPane.setStyle("-fx-background-color: rgba(144,248,255,0.24)");
        primaryStage.setTitle("登录页面");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/sample/image/ls.png"));
        primaryStage.show();
    }
}
