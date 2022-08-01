package sample.util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 1.限制文本框字数
 * 2.日期转为字符串类型 YY-mm-dd
 */

public class Tools {
    //限制文本框字数
    public static void limitNOF(TextField tf, int num){
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(tf.getText().length() > num)
                    tf.setText(oldValue);
            }
        });
    }

    //日期转为字符串类型 YY-mm-dd
    public static String dateToString(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }


}
