package org.yftx.wzd.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.TitlePageIndicator;
import org.yftx.common.task.*;
import org.yftx.wzd.R;
import org.yftx.wzd.WZDApplication;
import org.yftx.wzd.domain.Bid;
import org.yftx.wzd.ui.adapter.ContentAdapter;
import org.yftx.wzd.ui.base.Refreshable;

import java.util.ArrayList;
import java.util.List;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-9
 */
public class MainActivity extends SherlockFragmentActivity implements Refreshable {
    final static String TAG = "MainActivity";
    private ContentAdapter mAdapter;
    private ViewPager mPager;
    protected GenericTask mRetrieveTask;
    public List<Bid> bids = new ArrayList<Bid>();
    private WZDApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_titles);
        app = (WZDApplication) getApplication();
        initData();
    }

    /**
     * 向网络请求数据。
     */
    private void initData() {
        mRetrieveTask = new RetrieveTask();
        mRetrieveTask.setListener(mRetrieveCB);
        mRetrieveTask.execute();
    }

    /**
     * 向fragment中填充数据
     */
    private void fillData() {
        //内容部分
        mAdapter = new ContentAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        //Title 部分
        TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(TitlePageIndicator.IndicatorStyle.Triangle);
    }

    @Override
    public void doRetrieve() {


    }


    class RetrieveTask extends GenericTask {
        public static final String TAG = "RetrieveTask";

        @Override
        protected TaskResult _doInBackground(TaskParams... params) {
            try {
                bids = app.wzd.retrieveData();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                return TaskResult.IO_ERROR;
            }
            return TaskResult.OK;
        }
    }

    TaskListener mRetrieveCB = new TaskAdapter() {
        @Override
        public String getName() {
            return "RetrieveTask";
        }

        @Override
        public void onPostExecute(GenericTask task, TaskResult result) {
            if (result == TaskResult.AUTH_ERROR) {

            } else if (result == TaskResult.OK) {
                Log.d(TAG, "返回的数据长度为" + bids.size() + "");
                fillData();
            } else if (result == TaskResult.IO_ERROR) {

            }
        }
    };

}
