package sample.domain;

public class Student{
    private String id;//
    private String name;//姓名
    private String sno;//学号
    private String sex;//性别
    private String apname;//公寓名称
    private String apno ;//公寓号
    private String aproom;//房间号
    private String collage;//学院
    private String password;//密码
    private String age;
    private String pno;//电话号码
    private  String email;

    public Student() {
    }

    public Student(String id, String name, String sno, String sex, String apname, String apno, String aproom, String collage, String password, String age, String pno, String email) {
        this.id = id;
        this.name = name;
        this.sno = sno;
        this.sex = sex;
        this.apname = apname;
        this.apno = apno;
        this.aproom = aproom;
        this.collage = collage;
        this.password = password;
        this.age = age;
        this.pno = pno;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getCollage() {
        return collage;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApname() {
        return apname;
    }

    public void setApname(String apname) {
        this.apname = apname;
    }

    public String getApno() {
        return apno;
    }

    public void setApno(String apno) {
        this.apno = apno;
    }

    public String getAproom() {
        return aproom;
    }

    public void setAproom(String aproom) {
        this.aproom = aproom;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sno='" + sno + '\'' +
                ", sex='" + sex + '\'' +
                ", apname='" + apname + '\'' +
                ", apno='" + apno + '\'' +
                ", aproom='" + aproom + '\'' +
                ", collage='" + collage + '\'' +
                ", password='" + password + '\'' +
                ", age='" + age + '\'' +
                ", pno='" + pno + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}