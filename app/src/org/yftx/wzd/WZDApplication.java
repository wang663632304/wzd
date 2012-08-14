package org.yftx.wzd;

import android.app.Application;
import org.yftx.wzd.engine.WZD;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-12
 */
public class WZDApplication extends Application{
    public WZD wzd;
    public WZDApplication() {
        this.wzd = new WZD(this);
    }
}
