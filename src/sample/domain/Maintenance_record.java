package sample.domain;
/*维修记录*/
public class Maintenance_record {
    private String id;
    private String sno;
    private String name;
    private String ap_name;
    private String ap_no;
    private String ap_room;
    private String repair_content;//报修内容
    private String repair_data;//报修时间
    private String pno;//联系方式

    public Maintenance_record(){}

    public Maintenance_record(String id, String sno, String ap_name, String ap_no, String ap_room, String repair_content, String repair_data) {
        this.id = id;
        this.sno = sno;
        this.ap_name = ap_name;
        this.ap_no = ap_no;
        this.ap_room = ap_room;
        this.repair_content = repair_content;
        this.repair_data = repair_data;
    }

    public Maintenance_record(String id, String sno, String name, String ap_name, String ap_no, String ap_room, String repair__content, String repair_data,String pno) {
        this.id = id;
        this.sno = sno;
        this.name = name;
        this.ap_name = ap_name;
        this.ap_no = ap_no;
        this.ap_room = ap_room;
        this.repair_content = repair__content;
        this.repair_data = repair_data;
        this.pno = pno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAp_name() {
        return ap_name;
    }

    public void setAp_name(String ap_name) {
        this.ap_name = ap_name;
    }

    public String getAp_no() {
        return ap_no;
    }

    public void setAp_no(String ap_no) {
        this.ap_no = ap_no;
    }

    public String getAp_room() {
        return ap_room;
    }

    public void setAp_room(String ap_room) {
        this.ap_room = ap_room;
    }

    public String getRepair_content() {
        return repair_content;
    }

    public void setRepair_content(String repair_content) {
        this.repair_content = repair_content;
    }

    public String getRepair_data() {
        return repair_data;
    }

    public void setRepair_data(String repair_data) {
        this.repair_data = repair_data;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    @Override
    public String toString() {
        return "Maintenance_record{" +
                "id='" + id + '\'' +
                ", sno='" + sno + '\'' +
                ", name='" + name + '\'' +
                ", ap_name='" + ap_name + '\'' +
                ", ap_no='" + ap_no + '\'' +
                ", ap_room='" + ap_room + '\'' +
                ", repair_content='" + repair_content + '\'' +
                ", repair_data='" + repair_data + '\'' +
                ", pno='" + pno + '\'' +
                '}';
    }
}


