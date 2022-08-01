package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.domain.Administrator;
import sample.domain.Dormitory_ad;
import sample.domain.Student;

/**
 * 登录后主页面：
 * 管理员：查看楼栋信息、查看（维修）情况、查看反馈、退出登录
 *学生：我要报修、查看报修情况、反馈、退出登录
 */

public class MenuFrame implements EventHandler<ActionEvent> {

    //将登录的用户loginStu、身份identity传入到该类中
    private Student loginStu;
    private RepairFrame repairFrame;
    private Dormitory_ad loginD_ad;
    private Administrator liginAdmin;
    private String identity;
/*    public MenuFrame(String identity, Administrator liginAdmin ){
        this.identity = identity;
        this.liginAdmin = liginAdmin;
    }*/
    public MenuFrame(String identity, Student loginStu) {
        this.identity = identity;
        this.loginStu = loginStu;
    }
/*    public MenuFrame(String identity, Dormitory_ad loginD_ad ){
        this.identity = identity;
        this.loginD_ad = loginD_ad;
    }*/

    @Override
    public void handle(ActionEvent event) {
        Stage stage = new Stage();

        //窗口布局
        Button button= new Button("查看学生信息");
        Button bt1=new Button("我要报修");
        Button bt2=new Button("查看报修情况");
        Button bt3=new Button("反馈");
        Button bt4=new Button("退出登录");
        HBox box_S1 = new HBox(25);
        HBox box_S2 = new HBox(25);
        HBox box_M1 = new HBox(100);
        HBox box_M2 = new HBox();
        HBox box_M3 = new HBox(100);
        box_M1.setAlignment(Pos.CENTER);
        box_M2.setAlignment(Pos.CENTER);
        box_M3.setAlignment(Pos.CENTER);
        box_S1.setAlignment(Pos.CENTER);
        box_S2.setAlignment(Pos.CENTER);
        StackPane sPane = new StackPane();
        VBox vbox = new VBox(20);
        BorderPane rootBP =new BorderPane();
        Button Top_btn=new Button("欢迎使用本系统！");
        Top_btn.setStyle("-fx-background-color: rgba(144,248,255,0.22)");
       /* sPane.setRotate(45);
        box.setRotate(-45);
        box1.setRotate(-45);*/

        //根据身份不同进入到不同的信息查看页面
        if("学生".equals(identity)){
            bt1.setPrefSize(115,135);
            bt1.setStyle("-fx-background-color: rgba(255,245,198,0.58)");
            bt2.setPrefSize(115,135);
            bt2.setStyle("-fx-background-color: rgba(222,217,255,0.58)");
            bt3.setPrefSize(115,135);
            bt3.setStyle("-fx-background-color: rgba(255,194,168,0.58)");
            bt4.setPrefSize(115,135);
            bt4.setStyle("-fx-background-color: rgba(202,255,198,0.58)");
            //bt2, bt3, bt4, bt5对应
            //我要报修、查看楼栋报修情况、反馈、退出登录

            Top_btn.setPrefSize(440,0);
            box_S1.getChildren().addAll(bt1,bt2);
            box_S2.getChildren().addAll(bt3, bt4);
            vbox.getChildren().addAll(Top_btn,box_S1,box_S2);
            rootBP.setCenter(vbox);
        }
        else {
            bt1.setText("查看楼栋信息");
            bt3.setText("查看反馈");
           // sPane.setStyle("-fx-border-color:red");
            button.setPrefSize(120,120);
            button.setStyle("-fx-background-color: rgba(255,192,203,0.58)");
            bt1.setPrefSize(120,120);
            bt1.setStyle("-fx-background-color: rgba(255,245,198,0.58)");
            bt2.setPrefSize(120,120);
            bt2.setStyle("-fx-background-color: rgba(222,217,255,0.58)");
            bt3.setPrefSize(120,120);
            bt3.setStyle("-fx-background-color: rgba(255,194,168,0.58)");
            bt4.setPrefSize(110,120);
            bt4.setStyle("-fx-background-color: rgba(202,255,198,0.58)");
            box_M1.getChildren().addAll(button,bt1);
            box_M2.getChildren().addAll(bt2);
            box_M3.getChildren().addAll(bt3,bt4);
            Top_btn.setPrefSize(500,0);
            vbox.getChildren().addAll(Top_btn,box_M1,box_M2,box_M3);
            rootBP.setCenter(vbox);
            //sPane.getChildren().addAll(box1);
        }


        //学生：查询个人信息以及个人报修
        //进入报修页面。
        if("学生".equals(identity)) {
            RepairFrame repairFrame = new RepairFrame(loginStu, identity);
            bt1.setOnAction(repairFrame);
        }
        else{ //管理员：查询所有楼栋情况
            BudingInformationFrame budingInformationFrame = new BudingInformationFrame();
            bt2.setOnAction(budingInformationFrame);
        }


        //管理员：查询报修情况
        if("学生".equals(identity)) {
            BudingInformationFrame budingInformationFrame = new BudingInformationFrame(loginStu, identity);
            bt2.setOnAction(budingInformationFrame);
        }

        //仅管理员有查询楼栋信息列表
      if("管理员".equals(identity)) {
            BudingListFrame budingListFrame = new BudingListFrame();
            bt1.setOnAction(budingListFrame);
        }
        //仅管理员有查询用户功能
        if("管理员".equals(identity)) {
            UserListFrame userListFrame = new UserListFrame();
            button.setOnAction(userListFrame);
        }


        //查询反馈情况
        if("学生".equals(identity)) {
            FeedbackFrame feedbackFrame = new FeedbackFrame(loginStu, identity);
            bt3.setOnAction(feedbackFrame);
        }
        else{ //管理员：查询所有反馈情况
            FeedbackFrame feedbackFrame = new FeedbackFrame();
            bt3.setOnAction(feedbackFrame);
        }

        //退出事件
        bt4.setOnAction(e -> {
            Alert alertC = new Alert(Alert.AlertType.CONFIRMATION);
            alertC.setContentText("你确定要退出吗?");
            alertC.setHeaderText(null);
            Button ok = (Button) alertC.getDialogPane().lookupButton(ButtonType.OK);//获取确定按钮
            ok.setOnAction(e1 -> {
                stage.close();
                Alert alertI = new Alert(Alert.AlertType.INFORMATION);
                alertI.setContentText("退出成功");
                alertI.setHeaderText(null);
                alertI.showAndWait();
                Stage stage1 = new Stage();
                try {
                    new LoginFrame().start(stage1);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
            alertC.showAndWait();
        });

        if (identity =="学生"){
            Scene scene=new Scene(rootBP,440,370);//行(宽)，列(高)
            rootBP.setStyle("-fx-background-color: rgba(144,248,255,0.37)");
            scene.getStylesheets().add("style.css");
            stage.setScene(scene);
        }
        else {
        Scene scene=new Scene(rootBP,500,470);//行(宽)，列(高)
            rootBP.setStyle("-fx-background-color: rgba(144,248,255,0.37)");
            scene.getStylesheets().add("style.css");
            stage.setScene(scene);
             }
/*       //背景图片设置失败
       sPane.setId("spane");
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());*/

        //设置场景颜色

            if (identity=="学生")
                  {
                      stage.setTitle("学生主页面");
                  }else stage.setTitle("管理员主页面");
        stage.getIcons().add(new Image("/sample/image/ls.png"));

        stage.show();
    }
}
