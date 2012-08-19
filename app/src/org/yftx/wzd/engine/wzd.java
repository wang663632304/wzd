package org.yftx.wzd.engine;

import android.content.Context;
import android.util.Xml;
import com.github.kevinsawicki.http.HttpRequest;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.yftx.wzd.R;
import org.yftx.wzd.domain.Bid;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理网络相关请求
 * <p/>
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-12
 */
public class WZD {
    private Context context;

    public WZD(Context context) {
        this.context = context;
    }

    public List<Bid> retrieveData() throws IOException, XmlPullParserException {
        String response = "";
        String path = context.getResources().getString(R.string.apiurl);
        HttpRequest request = HttpRequest.get(path);
        if (request.code() == 200) {
            response = request.body();
            return handleResult(response);
        }
        //TODO 后期改为抛自定义异常
        return null;
    }

    private List<Bid> handleResult(String response) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        int eventType = parser.getEventType();
        List<Bid> bids = null;
        Bid currentBid = null;
        parser.setInput(new StringReader(response));
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT://文档开始事件,可以进行数据初始化处理
                    bids = new ArrayList<Bid>();
                    break;
                case XmlPullParser.START_TAG://开始元素事件
                    String name = parser.getName();
                    if (name.equalsIgnoreCase("item")) {
                        currentBid = new Bid();
                        currentBid.setId(new Integer(parser.getAttributeValue(null, "id")));
                    } else if (currentBid != null) {
                        if (name.equalsIgnoreCase("account_format")) {
                            currentBid.setAccount_format(parser.nextText());
                        } else if (name.equalsIgnoreCase("scale")) {
                            currentBid.setScale(parser.nextText());
                        } else if (name.equalsIgnoreCase("name")) {
                            currentBid.setName(parser.nextText());
                        } else if (name.equalsIgnoreCase("time_limit")) {
                            currentBid.setTime_limit(parser.nextText());
                        } else if (name.equalsIgnoreCase("apr")) {
                            currentBid.setApr(parser.nextText());
                        } else if (name.equalsIgnoreCase("funds")) {
                            currentBid.setFunds(parser.nextText());
                        } else if (name.equalsIgnoreCase("addtime")) {
                            currentBid.setAddtime(parser.nextText());
                        } else if (name.equalsIgnoreCase("username")) {
                            currentBid.setUsername(parser.nextText());
                        } else if (name.equalsIgnoreCase("borrow_type")) {
                            currentBid.setBorrow_type(parser.nextText());
                        } else if (name.equalsIgnoreCase("link_url")) {
                            currentBid.setLink_url(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG://结束元素事件
                    if (parser.getName().equalsIgnoreCase("item")
                            && currentBid != null
                            && bids != null) {
                        bids.add(currentBid);
                        currentBid = null;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return bids;
    }
}
