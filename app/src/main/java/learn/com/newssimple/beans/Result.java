package learn.com.newssimple.beans;

import java.util.Date;
import java.util.List;

/**
 * Created by zyx on 2017/7/13.
 */

public class Result {
    private String company;
    private String com;
    private String no;
    private int status;
    private List<ExpressDataList> list;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ExpressDataList> getList() {
        return list;
    }

    public void setList(List<ExpressDataList> list) {
        this.list = list;
    }


}
