package sample.domain;

public class Administrator {
    private String sno;//管理员ID admin
    private String pwd;//默认密码admin
    private String name;

    public Administrator(){}
    public Administrator(String sno, String pwd, String name) {
        this.sno = sno;
        this.pwd = pwd;
        this.name = name;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "sno='" + sno + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
