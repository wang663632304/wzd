package org.yftx.wzd.domain;

/**
 * 温州贷中标的信息
 * <p/>
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-7-22
 */
public class Bid {
    //item id(当前xml中的第几位从0开始)
    private int id;
    //借款金额
    private String account_format;
    //借款进度 (%)
    private String scale;
    //标的名字
    private String name;
    //借款期限(月)目前均标识为月标，只能从标的标题来区分是否为天标
    private String time_limit;
    //年利率 (%)
    private String apr;
    //投标奖励 可有可无
    private String funds;
    //借款人姓名
    private String username;
    //标类型 (给力标、净值标、秒表、担保标、信用标)
    private String borrow_type;
    //标的连接地址
    private String link_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getFunds() {
        return funds;
    }

    public void setFunds(String funds) {
        this.funds = funds;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(String time_limit) {
        this.time_limit = time_limit;
    }

    public String getBorrow_type() {
        return borrow_type;
    }

    public void setBorrow_type(String borrow_type) {
        this.borrow_type = borrow_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount_format() {
        return account_format;
    }

    public void setAccount_format(String account_format) {
        this.account_format = account_format;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }
}
