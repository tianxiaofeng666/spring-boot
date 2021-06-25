package cn.chinaunicom.person.request;

import lombok.Data;

@Data
public class GuestReq {
    private Long id;
    private String guestName;
    private String idCard;
    private int pageNum;
    private int pageSize;

    public GuestReq() {
    }

    public GuestReq(Long id, String guestName, String idCard, int pageNum, int pageSize) {
        this.id = id;
        this.guestName = guestName;
        this.idCard = idCard;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @Override
    public String toString(){
        return "id:" + id
                + ",访客姓名：" + guestName
                + ",身份证号：" + idCard;
    }
}
