package sample.domain;

public class Feedback {
    private String id;
    private String s_name;
    private String content;
    private String email;
    private String s_tel;
    private String f_time;
    public Feedback(){}

    public Feedback(String s_name, String content, String email, String s_tel, String f_time) {
        this.s_name = s_name;
        this.content = content;
        this.email = email;
        this.s_tel = s_tel;
        this.f_time = f_time;
    }

    public Feedback(String id, String s_name, String content, String email, String s_tel, String f_time) {
        this.id = id;
        this.s_name = s_name;
        this.content = content;
        this.email = email;
        this.s_tel = s_tel;
        this.f_time = f_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getS_tel() {
        return s_tel;
    }

    public void setS_tel(String s_tel) {
        this.s_tel = s_tel;
    }

    public String getF_time() {
        return f_time;
    }

    public void setF_time(String f_time) {
        this.f_time = f_time;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id='" + id + '\'' +
                ", s_name='" + s_name + '\'' +
                ", content='" + content + '\'' +
                ", email='" + email + '\'' +
                ", s_tel='" + s_tel + '\'' +
                ", f_time='" + f_time + '\'' +
                '}';
    }
}
