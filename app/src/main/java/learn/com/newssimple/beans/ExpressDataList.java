package learn.com.newssimple.beans;

import java.util.Date;

/**
 * Created by zyx on 2017/7/13.
 */

public class ExpressDataList {
    private Date datetime;
    private String remark;
    private String zone;

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

}
