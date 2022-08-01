package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sample.domain.Apartemt;
import sample.util.JdbcApartmentTools;

import java.util.List;

public class BudingListFrame implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        ObservableList<Apartemt> list = FXCollections.observableArrayList();// 表格对象
        TableView<Apartemt> tableView = new TableView<Apartemt>(); //视图对象

        //新建列对象
        TableColumn<Apartemt, String> xh_Column = new TableColumn<Apartemt, String>("序号");
        TableColumn<Apartemt, String> idColumn = new TableColumn<Apartemt, String>("公寓ID");
        TableColumn<Apartemt, String> apnameColumn = new TableColumn<Apartemt, String>("公寓名称");
        TableColumn<Apartemt, String> apnoColumn = new TableColumn<Apartemt, String>("公寓号");
        TableColumn<Apartemt, String> build_yearColumn = new TableColumn<Apartemt, String>("建立时间");
        TableColumn<Apartemt, String> repair_amountColumn = new TableColumn<Apartemt, String>("维修次数");
        TableColumn<Apartemt, String> updata_Column = new TableColumn<>("修改");
        TableColumn<Apartemt, String> del_Column = new TableColumn<>("删除");

        //数值值的列    其中最后的()内的字段为数据库Book表列名
        idColumn.setCellValueFactory(new PropertyValueFactory<Apartemt, String>("id"));
        apnameColumn.setCellValueFactory(new PropertyValueFactory<Apartemt, String>("apname"));
        apnoColumn.setCellValueFactory(new PropertyValueFactory<Apartemt, String>("apno"));
        build_yearColumn.setCellValueFactory(new PropertyValueFactory<Apartemt, String>("build_year"));
        repair_amountColumn.setCellValueFactory(new PropertyValueFactory<Apartemt, String>("repair_amount"));
      //设置每列的表格宽度
        apnameColumn.setPrefWidth(90);
        apnoColumn.setPrefWidth(60);
        build_yearColumn.setPrefWidth(90);
        repair_amountColumn.setPrefWidth(90);
        updata_Column.setPrefWidth(60);
        del_Column.setPrefWidth(60);
//非数据值的列
        //ID列 自动填补
        xh_Column.setPrefWidth(60);
        xh_Column.setCellFactory((col) -> {
            TableCell<Apartemt, String> cell = new TableCell<Apartemt, String>() {
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

        //将表头添加在表格中
        tableView.getColumns().addAll(xh_Column,idColumn,apnameColumn, apnoColumn,build_yearColumn, repair_amountColumn,updata_Column,del_Column);

        //添加数据到表格
        List<Apartemt> all = JdbcApartmentTools.findAll();
        for (Apartemt apartemt : all) {
            list.add(new Apartemt(null,apartemt.getId(),apartemt.getApname(), apartemt.getApno(),
                    apartemt.getBuild_year(), apartemt.getRepair_amount()));
        }

        //删除
        del_Column.setCellFactory((col) -> {
            TableCell<Apartemt, String> cell = new TableCell<Apartemt, String>() {
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
                            Apartemt clickedStu = this.getTableView().getItems().get(this.getIndex());//获取选中的行的student对象clicked
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);//警告对象
                            alert.setContentText("你确定要删除吗?");
                            alert.setHeaderText(null);
                            Button bt = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);//获取确定删除按钮
                            bt.setOnAction(e -> {
                               JdbcApartmentTools.delete(clickedStu.getId());//数据库实现删除操作
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
        updata_Column.setCellFactory((col) -> {
            TableCell<Apartemt, String> cell = new TableCell<Apartemt, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);

                    if (!empty) {
                        int index = this.getIndex();
                        Apartemt apartemt = this.getTableView().getItems().get(this.getIndex());//获取选中的行的apartment对象clicked
                        ImageView updateICON = new ImageView("file:E:\\LibrarySystem\\src\\sample\\image\\update.png".toString());
                        Button updateBtn = new Button("修改", updateICON);
                        updateBtn.setPrefWidth(10);
                        this.setGraphic(updateBtn);

                        //将list、index、student参数传入到UserUpdateFrame事件中，点击按钮实现修改操作
                        ApartemtUpdateFrame apartemtUpdateFrame = new ApartemtUpdateFrame(list, index, apartemt);
                        updateBtn.setOnAction(apartemtUpdateFrame);
                    }
                }
            };
            return cell;
        });


        // Top顶部：查询功能控件的引入与设置
        Button bt1 = new Button("查询");
        Button bt2 = new Button("还原");
        Button bt3 = new Button("添加");
        Label id = new Label("公寓ID:");
        TextField idf = new TextField();
        Label apname = new Label("公寓名称:");
        TextField apnamef = new TextField();
        Label apno = new Label("   公寓号:");
        TextField apnof = new TextField();
        Label build_year = new Label("建立时间:");
        TextField build_yearf = new TextField();
        Label repair_amount = new Label("维修次数:");
        TextField repair_amountf = new TextField();

        idf.setPrefColumnCount(5);
        apnamef.setPrefColumnCount(5);
        apnof.setPrefColumnCount(5);
        build_yearf.setPrefColumnCount(5);
        repair_amountf.setPrefColumnCount(5);

        HBox hBox0_id = new HBox(30);
        hBox0_id.getChildren().addAll(id,idf);
        HBox hBox1_apname = new HBox(20);
        hBox1_apname.getChildren().addAll(apname,apnamef,apno,apnof);
        HBox hBox2_apno = new HBox(20);
        hBox2_apno.getChildren().addAll(build_year,build_yearf,repair_amount,repair_amountf);


        HBox hBox3_btn = new HBox(50);
        hBox3_btn.setAlignment(Pos.CENTER);
        hBox3_btn.getChildren().addAll(bt3,bt1,bt2);

        VBox vBox1= new VBox(10);
        vBox1.getChildren().addAll(hBox0_id,hBox1_apname,hBox2_apno,hBox3_btn);

        BorderPane root = new BorderPane();
        root.setTop(vBox1);
        root.setStyle("-fx-background-color: rgba(144,248,255,0.22)");
        // Center中间：将表格放入视图中展示
        tableView.setItems(list);
        HBox hBox = new HBox();
        hBox.getChildren().add(tableView);
        HBox.setHgrow(tableView, Priority.ALWAYS);//水平拉满
        VBox vBox = new VBox();
        vBox.getChildren().add(hBox);
        root.setCenter(vBox);

        //定义查询用户事件对象，并将公寓名称、公寓号、建立时间、维修次数的查询文本框传入事件中操作
        BuildingFindFrame buildingFindFrame = new BuildingFindFrame(list,idf, apnamef, apnof, build_yearf, repair_amountf);
        bt1.setOnAction(buildingFindFrame);

        //还原操作，恢复查询数据库student表中的所有数据，实现过程：
        //1.表面清空文本框内容以及tableView表格内数据
        //2.将数据库student表中所有数据重新添加到tableView表格中
        bt2.setOnAction(e -> {
            idf.setText(null);
            apnamef.setText(null);
            apnof.setText(null);
            build_yearf.setText(null);
            repair_amountf.setText(null);
            list.clear();
            List<Apartemt> apartemtList1 = JdbcApartmentTools.findAll();
            for (Apartemt apartemt : apartemtList1) {
                list.add(new Apartemt(null,apartemt.getId(),apartemt.getApname(),apartemt.getApno(),apartemt.getBuild_year(),apartemt.getRepair_amount()));
            }
        });
        //添加，不知道为什么在同一个界面时，当点击了其他按钮时，本按钮不能再添加了
        bt3.setOnAction(e -> {
            Alert alert1= new Alert(Alert.AlertType.ERROR);
            if(idf.getText().isEmpty())
            {
                alert1.setContentText("公寓ID不能为空，请检输入是否正确！！");
                alert1.setHeaderText(null);
                alert1.showAndWait();
                return;
            }
            if(repair_amountf.getText().isEmpty())
            {
                alert1.setContentText("公寓报修次数不能为空，请检输入是否正确！！");
                alert1.setHeaderText(null);
                alert1.showAndWait();
                return;
            }
            String num=repair_amountf.getText();
            int num_int = Integer.parseInt(num);
            if (num_int<0||num_int>=10e6){
                alert1.setContentText("公寓报修次数要为正整数，请检输入是否正确！！");
                alert1.setHeaderText(null);
                alert1.showAndWait();
                return;
            }
            if(JdbcApartmentTools.insert_Apartment(idf.getText(),apnamef.getText(),apnof.getText(),build_yearf.getText(),repair_amountf.getText())) {
                //以下语句为表面添加数据，实际添加数据为 ↑  数据库sql语句
                list.add(new Apartemt(null,idf.getText(), apnamef.getText(),apnof.getText(),build_yearf.getText(),repair_amountf.getText()));
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("添加提醒");
                alert.setHeaderText(null);
                alert.setContentText("添加成功！");
                alert.showAndWait();
            }else {
                //添加失败，弹出提示框
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("该公寓ID已存在，请重新添加!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        });

        Scene scene = new Scene(root, 600, 450);
        Stage stage = new Stage();
        stage.setScene(scene);
        scene.getStylesheets().add("style.css");
        stage.setTitle("楼栋信息列表");
        //stage.getIcons().add(new Image("/sample/image/user.png"));
        stage.show();
    }
}
