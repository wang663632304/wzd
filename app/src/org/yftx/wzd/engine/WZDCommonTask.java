package org.yftx.wzd.engine;

import android.content.Context;
import android.util.Log;
import org.yftx.common.task.GenericTask;
import org.yftx.common.task.TaskParams;
import org.yftx.common.task.TaskResult;
import org.yftx.wzd.WZDApplication;
import org.yftx.wzd.domain.Bid;

import java.util.List;

public class WZDCommonTask {
    /**
     * 此种方式不能返回所需要的返回值，所以放弃该总写法,需要返回值的地方task放在Activity中实现
     public static class RetrieveTask extends GenericTask {
     public static final String TAG = "RetrieveTask";
     private WZDApplication app;

     public RetrieveTask(WZDApplication app) {
     this.app = app;
     }

     @Override protected TaskResult _doInBackground(TaskParams... params) {
     TaskParams param = params[0];
     List<Bid> bids = param.get("result") == null ? null : (List<Bid>) param.get("result");
     try {
     bids = app.wzd.retrieveData();
     } catch (Exception e) {
     Log.e(TAG, e.getMessage(), e);
     return TaskResult.IO_ERROR;
     }
     return TaskResult.OK;
     }
     }
     **/
}

