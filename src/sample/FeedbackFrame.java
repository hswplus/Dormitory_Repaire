package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sample.domain.Feedback;
import sample.domain.Student;
import sample.util.JdbcFeedbackTools;
import sample.util.JdbcUserTools;
import sample.util.Tools;
import java.util.List;

import java.util.Date;

public class FeedbackFrame implements EventHandler<ActionEvent> {

    private String identity;
    private Feedback feedback;
    private Student student;
    private ObservableList<FeedbackFrame> list;

    public FeedbackFrame(){}

    public FeedbackFrame(Student student, String identity) {
        this.student = student;
        this.identity = identity;
    }
    public FeedbackFrame(Feedback feedback, String identity) {
        this.feedback = feedback;
        this.identity = identity;
    }

    @Override
    public void handle(ActionEvent event) {
        ObservableList<Feedback> list = FXCollections.observableArrayList(); //表格对象
        TableView<Feedback> tableView = new TableView<Feedback>();//视图对象

        //新建列对象
        TableColumn<Feedback, String> idColumn = new TableColumn<>("序号");
        TableColumn<Feedback, String> snameColumn = new TableColumn<>("姓名");
        TableColumn<Feedback, String> contentColumn = new TableColumn<>("报修内容");
        TableColumn<Feedback, String> emailColumn = new TableColumn<>("邮箱");
        TableColumn<Feedback, String> s_telColumn = new TableColumn<>("联系方式");
        TableColumn<Feedback, String> f_timeColumn = new TableColumn<>("报修时间");
        TableColumn<Feedback, String> del_Column = new TableColumn<>("删除");
        //数值值的列    其中最后的()内的字段为数据库Feedback表列名
        idColumn.setCellValueFactory(new PropertyValueFactory<Feedback, String>("id"));
        snameColumn.setCellValueFactory(new PropertyValueFactory<Feedback, String>("s_name"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<Feedback, String>("content"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Feedback, String>("email"));
        s_telColumn.setCellValueFactory(new PropertyValueFactory<Feedback, String>("s_tel"));
        f_timeColumn.setCellValueFactory(new PropertyValueFactory<Feedback, String>("f_time"));


        //设置每列的表格宽度
       // idColumn.setPrefWidth(30);
        snameColumn.setPrefWidth(60);
        contentColumn.setPrefWidth(200);
        emailColumn.setPrefWidth(100);
        s_telColumn.setPrefWidth(100);
        f_timeColumn.setPrefWidth(100);
        tableView.setItems(list);
        del_Column.setPrefWidth(60);

        tableView.getColumns().addAll(idColumn, snameColumn, contentColumn, emailColumn, s_telColumn, f_timeColumn,del_Column);


        //非数据值的列
        //id自动补充

        idColumn.setPrefWidth(60);
        idColumn.setCellFactory((col) -> {
            TableCell<Feedback, String> cell = new TableCell<Feedback, String>(){
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


        //将表头添加在表格中 根据身份不同则功能（表头）不同
        //学生的界面
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        BorderPane rootbp = new BorderPane();//边界面板
        if(identity == "学生"){
            //添加数据到表格
            List<Feedback> feedbackList = JdbcFeedbackTools.find(student.getName());
            for (Feedback feedback : feedbackList) {
                list.add(new Feedback(feedback.getId(),feedback.getS_name(), feedback.getContent(),feedback.getEmail(),feedback.getS_tel(),feedback.getF_time()));
            }

            final Button bt1 = new Button("重置");
            final Button bt2 = new Button("确定");
            final TextArea contents = new TextArea();
            contents.setEditable(true);
            contents.setWrapText(true);// 设置多行输入框是否支持自动换行。true表示支持，false表示不支持。
            contents.setPrefColumnCount(11); // 设置多行输入框的推荐列数
            contents.setPrefRowCount(3); // 设置多行输入框的推荐行数
            contents.setPromptText("请输入报修内容"); // 设置多行输入框的提示语
            contents.setStyle("-fx-background-color: rgba(144,248,255,0.22)");
            contents.setMinHeight(100);
            contents.setMinWidth(470);

            HBox hBox = new HBox();//单行面板
            hBox.getChildren().add(tableView);
            hBox.setHgrow(tableView, Priority.ALWAYS);//?
            HBox text= new HBox(20);//单行面板
            VBox vBox = new VBox(30);//单列面板
            vBox.getChildren().addAll(bt1,bt2);
            text.getChildren().addAll (contents,vBox);
            text.setAlignment(Pos.CENTER);
            rootbp.setCenter(text);
            rootbp.setBottom(hBox);
            rootbp.setStyle("-fx-background-color: rgba(144,248,255,0.22)");
            String data = Tools.dateToString(new Date(), "yyyy-MM-dd");
            //重置按钮
            bt1.setOnAction(event1 -> {
                contents.clear();
                System.out.println(contents.getText());
            });
            //添加按钮
            bt2.setOnAction(event1 -> {

                if(contents.getText().isEmpty())
                {
                    alert.setContentText("内容不能为空！请重新输入！");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                    return;
                }
                if(JdbcFeedbackTools.insert(student.getName(), contents.getText(),student.getEmail(),student.getPno(),data))
                {
                    //以下语句为表面添加数据，实际添加数据为 ↑  数据库sql语句
                    list.add(new Feedback(null, student.getName(), contents.getText(),student.getEmail(),student.getPno(),data));
                    alert.setTitle("反馈提醒");
                    alert.setHeaderText(null);
                    alert.setContentText("提交成功！");
                    alert.showAndWait();
                    contents.clear();
                }
                //若添加成功，则关闭当前页面
                //stage.close();
            });
            del_Column.setCellFactory((col) -> {
                TableCell<Feedback, String> cell = new TableCell<Feedback, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        this.setText(null);
                        this.setGraphic(null);

                        if (!empty) {//数据非空操作，防止空值仍赋值导致乱码
                            ImageView delICON = new ImageView("file:E:\\LibrarySystem\\src\\sample\\image\\delete.png".toString());
                            Button delBtn = new Button("删除", delICON);
                            delBtn.setMaxWidth(10);
                            this.setGraphic(delBtn);
                            delBtn.setOnAction((me) -> {
                                Feedback clickedStu = this.getTableView().getItems().get(this.getIndex());//获取选中的行的student对象clicked
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);//警告对象
                                alert.setContentText("你确定要删除吗?");
                                alert.setHeaderText(null);
                                Button bt = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);//获取确定删除按钮
                                bt.setOnAction(e -> {
                                    JdbcFeedbackTools.delete(clickedStu.getS_name(),clickedStu.getContent());//数据库实现删除操作
                                    System.out.println(clickedStu.getS_name()+clickedStu.getContent());
                                    list.remove(this.getIndex());//表格里表面删除操作
                                });
                                alert.showAndWait();
                            });
                        }
                    }
                };
                return cell;
            });
        }
        else {

            //添加数据到表格
            List<Feedback> feedbackList = JdbcFeedbackTools.findAll();
            for (Feedback feedback : feedbackList) {
                list.add(new Feedback(feedback.getId(),feedback.getS_name(), feedback.getContent(),feedback.getEmail(),feedback.getS_tel(),feedback.getF_time()));
            }
            tableView.setItems(list);

            HBox hBox = new HBox();//单行面板
            hBox.getChildren().add(tableView);
            hBox.setHgrow(tableView, Priority.ALWAYS);//?
            VBox vBox = new VBox(30);//单列面板
            rootbp.setCenter(hBox);
            del_Column.setCellFactory((col) -> {
                TableCell<Feedback, String> cell = new TableCell<Feedback, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        this.setText(null);
                        this.setGraphic(null);
                        if (!empty) {//数据非空操作，防止空值仍赋值导致乱码
                            ImageView delICON = new ImageView("file:E:\\LibrarySystem\\src\\sample\\image\\delete.png".toString());
                            Button delBtn = new Button("删除", delICON);
                            delBtn.setMaxWidth(10);
                            this.setGraphic(delBtn);
                            delBtn.setOnAction((me) -> {
                                Feedback clickedStu = this.getTableView().getItems().get(this.getIndex());//获取选中的行的student对象clicked
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);//警告对象
                                alert.setContentText("你确定要删除吗?");
                                alert.setHeaderText(null);
                                Button bt = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);//获取确定删除按钮
                                bt.setOnAction(e -> {
                                    JdbcFeedbackTools.delete(clickedStu.getS_name(),clickedStu.getContent());//数据库实现删除操作
                                   // System.out.println(clickedStu.getS_name()+clickedStu.getContent());
                                    list.remove(this.getIndex());//表格里表面删除操作
                                });
                                alert.showAndWait();
                            });
                        }
                    }
                };
                return cell;
            });
        }

        //管理员的界面
      // Center中间：将表格放入视图中展示
        Scene scene = new Scene(rootbp, 680, 500);
        scene.getStylesheets().add("style.css");
        Stage stage = new Stage();
        stage.setScene(scene);

        if (identity=="学生")
        stage.setTitle("我要反馈");
        else
            stage.setTitle("查看反馈");
        stage.getIcons().add(new Image("/sample/image/information.png"));
        stage.show();
           }
}
