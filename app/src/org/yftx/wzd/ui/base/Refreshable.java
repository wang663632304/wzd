package org.yftx.wzd.ui.base;

import org.xmlpull.v1.XmlPullParserException;
import org.yftx.common.task.TaskAdapter;

import java.io.IOException;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-12
 */
public interface Refreshable {
    /**
     * 请求数据
     */
    void doRetrieve() throws IOException, XmlPullParserException;

    void freshUI();
}
