package sample.domain;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

//公寓
public class Apartemt {
    private String xh;
    private String id;//公寓ID
    private String apname;//公寓名称
    private String apno ;//公寓号
    private String build_year;//建立时间 YY-mm-dd
    private String repair_amount;//维修次数

    public Apartemt(){}

    public Apartemt(String xh,String id,String apname, String apno, String build_year, String repair_amount) {
        this.xh=xh;
        this.id =id;
        this.apname = apname;
        this.apno = apno;
        this.build_year = build_year;
        this.repair_amount = repair_amount;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public String getBuild_year() {
        return build_year;
    }

    public void setBuild_year(String build_year) {
        this.build_year = build_year;
    }

    public String getRepair_amount() {
        return repair_amount;
    }

    public void setRepair_amount(String repair_amount) {
        this.repair_amount = repair_amount;
    }

    @Override
    public String toString() {
        return "Apartemt{" +
                "xh='" + xh + '\'' +
                ", id='" + id + '\'' +
                ", apname='" + apname + '\'' +
                ", apno='" + apno + '\'' +
                ", build_year='" + build_year + '\'' +
                ", repair_amount='" + repair_amount + '\'' +
                '}';
    }
}
