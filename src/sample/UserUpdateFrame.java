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
 * 该修改用户页面仅限管理员进入
 * 更改student数据页面，进入后在文本框内回显选中修改的内容
 * 规定sno学号文本框内容不能与数据库其他内容重复，即设置用户为主键，重复则弹出提示框
 */

public class UserUpdateFrame implements EventHandler<ActionEvent> {

    //将list、student、index传入该类中
    private ObservableList<Student> list;
    private int index;
    private Student student;

    public UserUpdateFrame(ObservableList<Student> list, int index, Student student) {
        this.list = list;
        this.index = index;
        this.student = student;
    }

    @Override
    public void handle(ActionEvent event) {

        //添加标签与文本框控件来获取输入的内容，并实现选中修改数据回显到文本框中
        final Label name_lab = new Label("姓名：");
        final TextField namef = new TextField(list.get(index).getName());
        final Label sno_lab = new Label("学号：");
        //获取修改前学号以便sql语句修改操作中运用 （ where usedSno = ? ）
        String usedSno = list.get(index).getSno();
        final TextField snof = new TextField(usedSno);
        final Label sex_lab = new Label("性别：");
        final TextField sexf = new TextField(list.get(index).getSex());
        final Label apname_lab = new Label("公寓名称：");
        final TextField apnamef = new TextField(list.get(index).getApname());
        final Label apno_lab = new Label("公寓号：");
        final TextField apnof = new TextField(list.get(index).getApno());
        final Label aproom_lab = new Label("房间号：");
        final TextField aproomf = new TextField(list.get(index).getAproom());
        final Label collage_lab = new Label("学院：");
        final TextField collagef = new TextField(list.get(index).getCollage());
        final Label pwd_lab = new Label("密码：");
        final TextField pwdf = new TextField(list.get(index).getPassword());
        final Label age_lab = new Label("年龄：");
        final TextField agef = new TextField(list.get(index).getAge());
        final Label pno_lab = new Label("联系方式：");
        final TextField pnof = new TextField(list.get(index).getPno());
        final Label email_lab = new Label("邮箱：");
        final TextField emailf = new TextField(list.get(index).getEmail());




        GridPane rootGP = new GridPane();//用网格面板来排版 label+TextField 较为整齐且简易
        rootGP.setPadding(new Insets(0, 16, 0, 16));//设置整个网格面板与边界的距离
        rootGP.setHgap(10); //设置网格间的行间距
        rootGP.setVgap(10); //设置网格间的列间距

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
        Button bt1 = new Button("修改");
        Button bt2 = new Button("关闭");
        rootGP.add(bt1, 6, 6);
        rootGP.add(bt2, 7, 6);

        //在顶部设置图片
        Image im = new Image("/sample/image/updateUser1.png");
        ImageView imView = new ImageView();
        imView.setImage(im);
        imView.setFitWidth(750);
        imView.setFitHeight(200);

        //利用边界面板的set位置功能实现顶部放图片，中心放其他面板
        BorderPane rootPane = new BorderPane();
        rootPane.setTop(imView);
        rootPane.setCenter(rootGP);

        //新建新舞台
        Stage stage = new Stage();
        Scene scene = new Scene(rootPane, 700, 450);
        stage.setTitle("修改用户信息");
        stage.setScene(scene);
        stage.setResizable(false);//不可修改界面大小

        //bt1按钮 添加修改事件
        bt1.setOnAction(e -> {
            boolean flag;
            String string =sexf.getText();
            String[] sex= {"男","女"};
            Alert alert1= new Alert(Alert.AlertType.ERROR);
            if (string.equals(sex[0]) || string.equals(sex[1])) {

                List<Apartemt> all = Tool.findApname();
                String str1 = apnamef.getText();
                String[] flag1 = {"0", "1"};
                String ff = "0";
                for (int i = 0; i < all.size(); i++) {
                    if (str1.equals(all.get(i).getApname())) {
                        ff = flag1[1];
                    }
                }
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
                if (ff.equals(flag1[0])) {
                    alert1.setContentText("请正确选择您的公寓名称！");
                    alert1.setHeaderText(null);
                    alert1.showAndWait();
                    return;
                }
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

                flag = JdbcUserTools.update(namef.getText(), snof.getText(), sexf.getText()
                        , apnamef.getText(), apnof.getText(), aproomf.getText(), collagef.getText()
                        , pwdf.getText(), agef.getText(), pnof.getText(), emailf.getText(), student, usedSno);
            } else
                {
                    alert1.setContentText("请输入正确的性别！");
                    alert1.setHeaderText(null);
                    alert1.showAndWait();
                    return;
                }
            //判断是否修改成功，设置学号为主键，即判断是否存在已存在的学号，若存在则返回false，否则返回true进行添加操作
            if (flag) {
                //以下语句为表面修改数据，实际修改数据为 ↑  数据库sql语句
                list.set(index, new Student(null, namef.getText(), snof.getText(), sexf.getText()
                        , apnamef.getText(), apnof.getText(), aproomf.getText(), collagef.getText()
                        ,pwdf.getText(),agef.getText(),pnof.getText(),emailf.getText()));
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("修改成功");
                alert.setHeaderText(null);
                alert.showAndWait();

                //若修改成功，则关闭当前页面重新进入userList刷新数据，即可显示刚刚修改的数据
                stage.close();
                new UserListFrame();
            } else {
                //修改失败，弹出提示框
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("该学号已存在，请重新修改!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        });
        //取消按钮关闭添加页面
        bt2.setOnAction(e -> stage.close());
        stage.getIcons().add(new Image("/sample/image/updateIcon.png"));
        stage.show();
    }
}
