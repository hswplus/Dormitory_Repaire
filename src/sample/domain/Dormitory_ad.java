package sample.domain;

public class Dormitory_ad {
    private String d_id;
    private String d_name;
    private String d_password;
    private String d_sex;
    private String d_age;
    private String d_tel;

    public Dormitory_ad(){}

    public Dormitory_ad(String d_id, String d_name, String d_password, String d_sex, String d_age, String d_tel) {
        this.d_id = d_id;
        this.d_name = d_name;
        this.d_password = d_password;
        this.d_sex = d_sex;
        this.d_age = d_age;
        this.d_tel = d_tel;
    }

    public String getD_id() {
        return d_id;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public String getD_password() {
        return d_password;
    }

    public void setD_password(String d_password) {
        this.d_password = d_password;
    }

    public String getD_sex() {
        return d_sex;
    }

    public void setD_sex(String d_sex) {
        this.d_sex = d_sex;
    }

    public String getD_age() {
        return d_age;
    }

    public void setD_age(String d_age) {
        this.d_age = d_age;
    }

    public String getD_tel() {
        return d_tel;
    }

    public void setD_tel(String d_tel) {
        this.d_tel = d_tel;
    }

    @Override
    public String toString() {
        return "Dormitory_ad{" +
                "d_id='" + d_id + '\'' +
                ", d_name='" + d_name + '\'' +
                ", d_password='" + d_password + '\'' +
                ", d_sex='" + d_sex + '\'' +
                ", d_age='" + d_age + '\'' +
                ", d_tel='" + d_tel + '\'' +
                '}';
    }
}
