package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.domain.Apartemt;
import sample.domain.Student;
import sample.util.JdbcApartmentTools;
import sample.util.JdbcUserTools;
import sample.util.Tool;

import java.util.List;

public class ApartemtUpdateFrame  implements EventHandler<ActionEvent> {

    private ObservableList<Apartemt> list;
    private int index;
    private Apartemt apartemt;

    public ApartemtUpdateFrame(ObservableList<Apartemt> list, int index, Apartemt apartemt) {
        this.list = list;
        this.index = index;
        this.apartemt = apartemt;
    }

    @Override
    public void handle(ActionEvent event) {
        Button bt1 = new Button("修改");
        Button bt2 = new Button("关闭");
        Label id = new Label("公寓ID:");
        String Ap_id = list.get(index).getId();
        TextField idf=new TextField(list.get(index).getId());
        idf.setPrefSize(160,30);
        Label apname = new Label("公寓名称:");
        TextField apnamef = new TextField(list.get(index).getApname());
        apnamef.setPrefSize(160,30);
        Label apno = new Label("   公寓号:");
        TextField apnof = new TextField(list.get(index).getApno());
        apnof.setPromptText("请输入公寓号");
        apnof.setPrefSize(160,30);
        Label build_year = new Label("建立时间:");
        TextField build_yearf = new TextField(list.get(index).getBuild_year());
        build_yearf.setPromptText("请输入建立时间");
        build_yearf.setPrefSize(160,30);
        Label repair_amount = new Label("维修次数:");
        TextField repair_amountf = new TextField(list.get(index).getRepair_amount());
        repair_amountf.setPromptText("请输入维修次数");
        repair_amountf.setPrefSize(160,30);

        idf.setPrefColumnCount(15);
        apnamef.setPrefColumnCount(15);
        apnof.setPrefColumnCount(15);
        build_yearf.setPrefColumnCount(15);
        repair_amountf.setPrefColumnCount(15);


        //布局
        HBox hBox0_apID = new HBox(30);
        hBox0_apID.getChildren().addAll(id,idf);
        HBox hBox1_apname = new HBox(30);
        hBox1_apname.getChildren().addAll(apname,apnamef);
        HBox hBox2_apno = new HBox(30);
        hBox2_apno.getChildren().addAll(apno,apnof);
        HBox hBox3_build_year = new HBox(30);
        hBox3_build_year.getChildren().addAll(build_year,build_yearf);
        HBox hBox4_repair_amount = new HBox(30);
        hBox4_repair_amount.getChildren().addAll(repair_amount,repair_amountf);

        HBox hBox5_btn = new HBox(50);
        hBox5_btn.setAlignment(Pos.CENTER);
        hBox5_btn.getChildren().addAll(bt1,bt2);

        VBox vBox1= new VBox(10);
        vBox1.getChildren().addAll(hBox0_apID, hBox1_apname,hBox2_apno,hBox3_build_year,hBox4_repair_amount,hBox5_btn);
        GridPane gridPane= new GridPane();
        gridPane.add(vBox1,1,1);

        //利用边界面板的set位置功能实现中心放其他面板
        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(gridPane);

        Scene scene = new Scene(rootPane, 360, 250);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setResizable(false);//不可修改界面大小

        //bt1按钮 添加修改事件
        bt1.setOnAction(e -> {
            Alert alert1= new Alert(Alert.AlertType.ERROR);
           /* String string =sexf.getText();
            String[] sex= {"男","女"};

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
                }*/
                //公寓报修次数要为整数。
                String num=repair_amountf.getText();
                 int num_int = Integer.parseInt(num);

                if (num_int<0||num_int>=10e6){
                    alert1.setContentText("公寓报修次数要为正整数，请检输入是否正确！！");
                    alert1.setHeaderText(null);
                    alert1.showAndWait();
                    return;
                }
           boolean flag;
                flag = JdbcApartmentTools.update(idf.getText(),apnamef.getText(), apnof.getText(),build_yearf.getText()
                                                , repair_amountf.getText(), apartemt, Ap_id);

                //System.out.println(flag);
            //判断是否修改成功，设置学号为主键，即判断是否存在已存在的学号，若存在则返回false，否则返回true进行添加操作
            if (flag) {
                //以下语句为表面修改数据，实际修改数据为 ↑  数据库sql语句
                list.set(index, new Apartemt(null,idf.getText(), apnamef.getText(), apnof.getText(),build_yearf.getText(),repair_amountf.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("修改成功");
                alert.setHeaderText(null);
                alert.showAndWait();

                //若修改成功，则关闭当前页面重新进入BudingListFrame刷新数据，即可显示刚刚修改的数据
                stage.close();
                new BudingListFrame();
            } else {
                //修改失败，弹出提示框
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("该公寓ID已存在，请重新修改!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        });
        scene.getStylesheets().add("style.css");
       // scene.setCursor(Cursor.CLOSED_HAND);
        //取消按钮关闭添加页面
        bt2.setOnAction(e -> stage.close());
        stage.setTitle("修改公寓信息");
       // stage.getIcons().add(new Image("/sample/image/user.png"));
        stage.show();

    }
}
