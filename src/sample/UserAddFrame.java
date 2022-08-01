package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.domain.Apartemt;
import sample.domain.Student;
import sample.util.JdbcUserTools;
import sample.util.Tool;
import sample.util.Tools;

import java.util.List;

/**
 * 该页面仅限管理员进入，用户进入查询所有用户页面
 * 用户添加界面，可实现用户的添加操作
 */
public class UserAddFrame  implements EventHandler<ActionEvent> {
    //将list传入该类中
    private ObservableList<Student> list;

    public UserAddFrame(ObservableList<Student> list) {
        this.list = list;
    }

    @Override
    public void handle(ActionEvent event) {


        //添加标签与文本框控件来获取输入的内容
        final Label name_lab = new Label("姓名：");
        final TextField namef = new TextField();
        final Label sno_lab = new Label("学号：");
        final TextField snof = new TextField();
        final Label sex_lab = new Label("性别：");
        final TextField sexf = new TextField();
        final Label apname_lab = new Label("公寓名称：");
        final TextField apnamef = new TextField();
        final Label apno_lab = new Label("公寓号：");
        final TextField apnof = new TextField();
        final Label aproom_lab = new Label("房间号：");
        final TextField aproomf = new TextField();
        final Label collage_lab = new Label("学院：");
        final TextField collagef = new TextField();
        final Label pwd_lab = new Label("密码：");
        final TextField pwdf = new TextField();
        final Label age_lab = new Label("年龄：");
        final TextField agef = new TextField();
        final Label pno_lab = new Label("联系方式：");
        final TextField pnof = new TextField();
        final Label email_lab = new Label("邮箱：");
        final TextField emailf = new TextField();


        GridPane rootGP = new GridPane(); //用网格面板来排版 label+TextField 较为整齐且简易
        rootGP.setPadding(new Insets(0, 16, 0, 16)); //设置整个网格面板与边界的距离
        rootGP.setHgap(10);//设置网格间的行间距
        rootGP.setVgap(10);//设置网格间的列间距

        //将label+TextField+button放入网格面板
        namef.setPromptText("请输入姓名");
        rootGP.add(name_lab, 0, 1);
        rootGP.add(namef, 1, 1);
        sexf.setPromptText("请输入性别");
        rootGP.add(sex_lab, 0, 2);
        rootGP.add(sexf, 1, 2);
        agef.setPromptText("请输入年龄");
        rootGP.add(age_lab, 0, 3);
        rootGP.add(agef, 1, 3);
        apnamef.setPromptText("请输入公寓名称");
        rootGP.add(apname_lab, 0, 4);
        rootGP.add(apnamef, 1, 4);
        apnof.setPromptText("请输入公寓号");
        rootGP.add(apno_lab, 0, 5);
        rootGP.add(apnof, 1, 5);
        aproomf.setPromptText("请输入房间号");
        rootGP.add(aproom_lab, 0, 6);
        rootGP.add(aproomf, 1, 6);
        Tools.limitNOF(sexf, 2);
        Tools.limitNOF(pnof, 11);
        snof.setPromptText("请输入学号");
        rootGP.add(sno_lab, 10, 1);
        rootGP.add(snof, 11, 1);
       pwdf.setPromptText("请输入密码");
        rootGP.add(pwd_lab, 10, 2);
        rootGP.add(pwdf, 11, 2);
        collagef.setPromptText("请输入学院名称");
        rootGP.add(collage_lab, 10, 3);
        rootGP.add(collagef, 11, 3);
        pnof.setPromptText("请输入电话");
        rootGP.add(pno_lab, 10, 4);
        rootGP.add(pnof, 11, 4);
        emailf.setPromptText("请输入电话");
        rootGP.add(email_lab, 10, 5);
        rootGP.add(emailf, 11, 5);
        Button bt1 = new Button("确认");
        Button bt2 = new Button("关闭");
        rootGP.add(bt1, 6, 6);
        rootGP.add(bt2, 7, 6);

        //在顶部设置图片
        Image im = new Image("sample/image/addUser.png");
        ImageView imView = new ImageView();
        imView.setImage(im);
        //
        imView.setFitWidth(750);
        imView.setFitHeight(200);

        //利用边界面板的set位置功能实现顶部放图片，中心放其他面板
        BorderPane rootPane = new BorderPane();
        rootPane.setTop(imView);
        rootPane.setCenter(rootGP);

        //新建新舞台
        Stage stage = new Stage();
        Scene scene = new Scene(rootPane, 750, 500);
        scene.getStylesheets().add("style.css");        stage.setTitle("添加用户信息");
        stage.setScene(scene);
        stage.setResizable(false);//不可修改界面大小



        //bt1添加用户事件
        bt1.setOnAction(e -> {
            //判断是否添加成功，设置学号为主键，即判断是否存在已存在的学号，若存在则返回false，否则返回true进行添加操作
            String string =sexf.getText();
            String[] sex= {"男","女"};
            Alert alert1= new Alert(Alert.AlertType.ERROR);
            if (string.equals(sex[0]) || string.equals(sex[1])){

                //核对年龄
                int age_int=Integer.parseInt(agef.getText());
                if(age_int > 25 || age_int < 17)
                {
                    alert1.setContentText("请输入适合的年龄！17~25");
                    alert1.setHeaderText(null);
                    alert1.showAndWait();
                    return;
                }
                //核对公寓名称
                List<Apartemt> all = Tool.findApname();
                String str1 =apnamef.getText() ;
                String[] flag1={"0","1"};
                String ff="0";
                for (int i=0;i<all.size();i++) {
                    if (str1.equals(all.get(i).getApname()))
                    {
                        ff=flag1[1];
                    }
                }
                if (ff.equals(flag1[0])){
                    alert1.setContentText("请正确选择您的公寓名称！");
                    alert1.setHeaderText(null);
                    alert1.showAndWait();
                    return;
                }

                //房间号不能为空，且要为整数
                if(aproomf.getText().isEmpty()){
                    alert1.setContentText("房间号不能为空，且要为整数！");
                    alert1.setHeaderText(null);

                    alert1.showAndWait();
                    return;
                }

                //学号能为空，且要为整数
                if(snof.getText().isEmpty()){
                    alert1.setContentText("学号不能为空！");
                    alert1.setHeaderText(null);
                    alert1.showAndWait();
                    return;
                }
                //学号限制，字符串转字符数组
                char[] s=snof.getText().toCharArray();
                if (s.length>12||s.length<8){
                    alert1.setContentText("请检查学号是否正确！！\n有8个数字或12个数字！");
                    alert1.setHeaderText(null);
                    alert1.showAndWait();
                    return;
                }
                //电话号码的识别。
                char[] tel=pnof.getText().toCharArray();
                if (tel.length!=11){
                    alert1.setContentText("请检查电话号码是否正确！！");
                    alert1.setHeaderText(null);
                    alert1.showAndWait();
                    return;
                }


            if (JdbcUserTools.insert(namef.getText(), snof.getText(), sexf.getText()
                    , apnamef.getText(), apnof.getText(), aproomf.getText(), collagef.getText()
            ,pwdf.getText(),agef.getText(),pnof.getText(),emailf.getText())) {
                //以下语句为表面添加数据，实际添加数据为 ↑  数据库sql语句
                list.add(new Student(null,namef.getText(), snof.getText(), sexf.getText()
                        , apnamef.getText(), apnof.getText(), aproomf.getText(), collagef.getText()
                        ,pwdf.getText(),agef.getText(),pnof.getText(),emailf.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("添加成功");
                alert.setHeaderText(null);
                alert.showAndWait();
                //若添加成功，则关闭当前页面重新进入bookList刷新数据，即可显示刚刚添加的数据
                stage.close();

            } else {
                //添加失败，弹出提示框
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("该学号已存在，请重新添加!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
            }


            else
            {
                alert1.setContentText("请输入正确的性别！");
                alert1.setHeaderText(null);
                alert1.showAndWait();
                return;
            }
        });
        //取消按钮关闭添加页面
        bt2.setOnAction(e -> stage.close());

        stage.setTitle("添加用户");
        stage.getIcons().add(new Image("/sample/image/add.png"));
        stage.show();
    }
}

