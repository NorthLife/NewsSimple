package learn.com.newssimple.beans;

/**
 * Created by zyx on 2017/7/13.
 */

public class ExpressName {

    private String com;
    private String no;

    public ExpressName(String com, String no) {
        this.com = com;
        this.no = no;
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
}
