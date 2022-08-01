package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sample.domain.Student;
import sample.util.JdbcUserTools;

import java.util.List;
/**
 * 仅限管理员查询用户 功能：
 * 添加用户功能，查找用户功能，修改用户功能，删除用户功能
 */
public class UserListFrame implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        ObservableList<Student> list = FXCollections.observableArrayList();// 表格对象
        TableView<Student> tableView = new TableView<Student>(); //视图对象

        //新建列对象
        TableColumn<Student, String> idColumn = new TableColumn<Student, String>("序号");
        TableColumn<Student, String> snoColumn = new TableColumn<Student, String>("学号");
        TableColumn<Student, String> nameColumn = new TableColumn<Student, String>("姓名");
        TableColumn<Student, String> sexColumn = new TableColumn<Student, String>("性别");
        TableColumn<Student, String> apnameColumn = new TableColumn<Student, String>("公寓名称");
        TableColumn<Student, String> apnoColumn = new TableColumn<Student, String>("公寓号");
        TableColumn<Student, String> ap_roomColumn = new TableColumn<Student, String>("房间号");
        TableColumn<Student, String> collageColumn = new TableColumn<Student, String>("学院");
        TableColumn<Student, String> passwordColumn = new TableColumn<Student, String>("密码");
        TableColumn<Student, String> ageColumn = new TableColumn<Student, String>("年龄");
        TableColumn<Student, String> pnoColumn = new TableColumn<Student, String>("电话号码");
        TableColumn<Student, String> emailColumn = new TableColumn<Student, String>("邮箱");
        TableColumn<Student, String> delColumn = new TableColumn<Student, String>("删除");
        TableColumn<Student, String> updateColumn = new TableColumn<Student, String>("修改");


        //数值值的列    其中最后的()内的字段为数据库Book(student)表列名
        snoColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("sno"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("sex"));
        apnameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("apname"));
        apnoColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("apno"));
        ap_roomColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("aproom"));
        collageColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("collage"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("password"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("age"));
        pnoColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("pno"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));

     /*   //自己定义的bookTools的居中操作setCenter方法，实现数据在表格中居中
        userTools.setCenter(nameColumn);
        userTools.setCenter(sexColumn);
        userTools.setCenter(pnoColumn);
        userTools.setCenter(snoColumn);
        userTools.setCenter(collegeColumn);
        userTools.setCenter(cnameColumn);
        userTools.setCenter(passwordColumn);*/

        //设置每列的表格宽度
        nameColumn.setPrefWidth(60);
        snoColumn.setPrefWidth(70);
        sexColumn.setPrefWidth(60);
        apnameColumn.setPrefWidth(90);
        apnoColumn.setPrefWidth(50);
        ap_roomColumn.setPrefWidth(50);
        collageColumn.setPrefWidth(115);
        passwordColumn.setPrefWidth(60);
        ageColumn.setPrefWidth(60);
        pnoColumn.setPrefWidth(90);
        emailColumn.setPrefWidth(120);
        updateColumn.setPrefWidth(60);
        delColumn.setPrefWidth(60);

        //非数据值的列
        //ID列 自动填补
        idColumn.setPrefWidth(60);
        idColumn.setCellFactory((col) -> {
            TableCell<Student, String> cell = new TableCell<Student, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);

                    if (!empty) {
                        int rowIndex = this.getIndex() + 1;
                        this.setText("  " + String.valueOf(rowIndex));
                    }
                }
            };
            return cell;
        });

        //删除列
        delColumn.setCellFactory((col) -> {
            TableCell<Student, String> cell = new TableCell<Student, String>() {
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
                            Student clickedStu = this.getTableView().getItems().get(this.getIndex());//获取选中的行的student对象clicked
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);//警告对象
                            alert.setContentText("你确定要删除吗?");
                            alert.setHeaderText(null);
                            Button bt = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);//获取确定删除按钮
                            bt.setOnAction(e -> {
                                JdbcUserTools.delete(clickedStu.getSno());//数据库实现删除操作
                                list.remove(this.getIndex());//表格里表面删除操作
                            });
                            alert.showAndWait();
                        });
                    }
                }
            };
            return cell;
        });

   //修改列
           updateColumn.setCellFactory((col) -> {
            TableCell<Student, String> cell = new TableCell<Student, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);

                    if (!empty) {
                        int index = this.getIndex();
                        Student student = this.getTableView().getItems().get(this.getIndex());//获取选中的行的student对象clicked
                        ImageView updateICON = new ImageView("file:E:\\LibrarySystem\\src\\sample\\image\\update.png".toString());
                        Button updateBtn = new Button("修改", updateICON);
                        updateBtn.setPrefWidth(10);
                        this.setGraphic(updateBtn);

                        //将list、index、student参数传入到UserUpdateFrame事件中，点击按钮实现修改操作
                        UserUpdateFrame userUpdateFrame = new UserUpdateFrame(list, index, student);
                        updateBtn.setOnAction(userUpdateFrame);
                    }
                }
            };
            return cell;
        });

        //将表头添加在表格中
        tableView.getColumns().addAll(idColumn, snoColumn, nameColumn,sexColumn, apnameColumn,apnoColumn,ap_roomColumn,
                collageColumn,passwordColumn, ageColumn,pnoColumn,emailColumn,updateColumn,delColumn);

        //添加数据到表格
        List<Student> studentList = JdbcUserTools.findAll();
        for (Student student : studentList) {
            list.add(new Student(student.getId(),student.getName(),  student.getSno(),student.getSex(),student.getApname(),student.getApno(),
                    student.getAproom(),student.getCollage(),student.getPassword(),student.getAge(), student.getPno(),student.getEmail()));
        }

        // Top顶部：查询功能控件的引入与设置
        Button bt = new Button("添加");
        Button bt1 = new Button("查询");
        Button bt2 = new Button("还原");
        Label name = new Label("姓名:");
        TextField nf = new TextField();
        Label sno = new Label("学号:");
        TextField sf = new TextField();
        Label college = new Label("学院:");
        TextField cof = new TextField();
        Label sex = new Label("性别:");
        TextField sexf = new TextField();
        Label apname = new Label("公寓名称:");
        TextField apnamef = new TextField();

        nf.setPrefColumnCount(5);
        sf.setPrefColumnCount(10);
        cof.setPrefColumnCount(8);
        sexf.setPrefColumnCount(3);
        apnamef.setPrefColumnCount(5);

        FlowPane flowPane = new FlowPane(10, 0);
        flowPane.setPadding(new Insets(15, 0, 15, 0));
        flowPane.getChildren().addAll(bt, name, nf, sno, sf, college, cof, sex, sexf,apname,apnamef, bt1, bt2);
        BorderPane root = new BorderPane();
        root.setTop(flowPane);

        //定义用户添加事件对象，并将list变量传入
        UserAddFrame userAddFrame = new UserAddFrame(list);
        bt.setOnAction(userAddFrame);//添加事件

        // Center中间：将表格放入视图中展示
        tableView.setItems(list);
        HBox hBox = new HBox();
        hBox.getChildren().add(tableView);
        HBox.setHgrow(tableView, Priority.ALWAYS);//水平拉满
        VBox vBox = new VBox();
        vBox.getChildren().add(hBox);
        root.setCenter(vBox);
        root.setStyle("-fx-background-color: rgba(144,248,255,0.22)");

        //定义查询用户事件对象，并将学号、姓名、学院、的查询文本框传入事件中操作
        UserFindFrame userFindFrame = new UserFindFrame(list, nf, sf, cof, sexf,apnamef);
        bt1.setOnAction(userFindFrame);


        //还原操作，恢复查询数据库student表中的所有数据，实现过程：
        //1.表面清空文本框内容以及tableView表格内数据
        //2.将数据库student表中所有数据重新添加到tableView表格中
        bt2.setOnAction(e -> {
            nf.setText(null);
            sf.setText(null);
            cof.setText(null);
            sexf.setText(null);
            apnamef.setText(null);

            list.clear();
            List<Student> studentsList = JdbcUserTools.findAll();
            for (Student student : studentsList) {
                list.add(new Student(student.getId(),student.getName(), student.getSno(), student.getSex(),student.getApname(),student.getApno(),
                        student.getAproom(),student.getCollage(),student.getPassword(),student.getAge(), student.getPno(),student.getEmail()));
            }
        });

        Scene scene = new Scene(root, 1050, 450);
        Stage stage = new Stage();
        stage.setScene(scene);
        scene.getStylesheets().add("style.css");
        stage.setTitle("用户列表");
        stage.getIcons().add(new Image("/sample/image/user.png"));
        stage.show();
    }
}

