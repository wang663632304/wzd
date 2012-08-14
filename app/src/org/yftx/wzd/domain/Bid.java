package org.yftx.wzd.domain;

/**
 * 温州贷中标的信息
 * <p/>
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-7-22
 */

/**
 * <item id="0">
 	<account_format>750,000</account_format>
 	<scale>61.2</scale>
 	<id>11420</id>
 	<user_id>1729</user_id>
 	<name>创新借款系列（1）——14天标</name>
 	<time_limit>1</time_limit>
 	<apr>20.40</apr>
 	<funds></funds>
 	<addtime>2012-08-12 19:56:20</addtime>
 	<username>Dayyz3</username>
 	<repayment_manner>按月还款</repayment_manner>
 	<borrow_type>给力标</borrow_type>
 	<link_url>http://www.wzdai.com/invest/a11420.html</link_url>
 </item>
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
    /**
     * 标添加的时间
     */
    private String addtime;

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }




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
