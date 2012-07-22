package org.yftx.wzd.net;

import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.yftx.wzd.domain.Bid;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-7-22
 */
public class WzdApi {
    private static String path = "http://www.wzdai.com/openapi/invest-list.php";

    /**
     * 获取最新标信息
     *
     * @return
     * @throws Exception
     */
    public static List<Bid> getLastBids() throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        InputStream inStream = conn.getInputStream();
        if (conn.getResponseCode() == 200) {
            return parseXML(inStream);
        }
        return null;
    }

    /**
     * 解析服务器返回的xml数据
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    private static List<Bid> parseXML(InputStream inStream) throws Exception {
        List<Bid> bids = null;
        Bid bid = null;

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inStream, "UTF-8");
        int event = parser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    bids = new ArrayList<Bid>();
                    break;
                case XmlPullParser.START_TAG:
                    if ("item".equals(parser.getName())) {
                        bid = new Bid();
                        bid.setId(new Integer(parser.getAttributeValue(0)));
                    }
                    if (bid != null) {
                        if ("account_format".equals(parser.getName())) {
                            bid.setAccount_format(parser.nextText());
                        } else if ("scale".equals(parser.getName())) {
                            bid.setScale(parser.nextText());
                        } else if ("name".equals(parser.getName())) {
                            bid.setName(parser.nextText());
                        } else if ("time_limit".equals(parser.getName())) {
                            bid.setTime_limit(parser.nextText());
                        } else if ("apr".equals(parser.getName())) {
                            bid.setApr(parser.nextText());
                        } else if ("funds".equals(parser.getName())) {
                            bid.setFunds(parser.nextText());
                        } else if ("username".equals(parser.getName())) {
                            bid.setUsername(parser.nextText());
                        } else if ("borrow_type".equals(parser.getName())) {
                            bid.setBorrow_type(parser.nextText());
                        } else if ("link_url".equals(parser.getName())) {
                            bid.setLink_url(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("item".equals(parser.getName())) {
                        bids.add(bid);
                        bid = null;
                    }
                    break;
            }
            event = parser.next();
        }
        return bids;
    }

    private static String parseInputStreamToString(InputStream inStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
           outputStream.write(buffer,0,len);
        }
        inStream.close();

        return outputStream.toString();  //To change body of created methods use File | Settings | File Templates.
    }





    /**
     * 获取json格式的投标信息
     * @return
     * @throws Exception
     */
//   	public static List<Bid> getJSONLastVideos() throws Exception{
//   		URL url = new URL(url);
//   		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//   		conn.setConnectTimeout(5 * 1000);
//   		conn.setRequestMethod("GET");
//   		InputStream inStream = conn.getInputStream();
//   		if(conn.getResponseCode() == 200){
//   			return parseJSON(inStream);
//   		}
//   		return null;
//   	}
    /**
     * 解析JSON数据
     * @param inStream
     * @return
     * @throws Exception
     */
//   	private static List<Bid> parseJSON(InputStream inStream) throws Exception{
//   		List<Bid> videos = new ArrayList<Bid>();
//   		byte[] data = StreamTool.readInputStream(inStream);
//   		String json = new String(data);
//   		JSONArray array = new JSONArray(json);
//   		for(int i = 0 ; i < array.length() ; i++){
//   			JSONObject item = array.getJSONObject(i);
//   			int id = item.getInt("id");
//   			String title = item.getString("title");
//   			int timelength = item.getInt("timelength");
//   			videos.add(new Bid(id, title, timelength));
//   		}
//   		return videos;
//   	}
}
