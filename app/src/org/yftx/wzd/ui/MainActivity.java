package org.yftx.wzd.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
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
    private TaskAdapter taskListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        app = (WZDApplication) getApplication();
        initView();
        getData();

    }

    private void initView() {
        //内容部分
        mAdapter = new ContentAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        //Title 部分
        TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(TitlePageIndicator.IndicatorStyle.Triangle);
    }

    /**
     * 向网络请求数据。
     */
    private void getData() {
        mRetrieveTask = new RetrieveTask();
        if (taskListener == null)
            taskListener = mRetrieveCB;

        mRetrieveTask.setListener(taskListener);
        mRetrieveTask.execute();
    }

    /**
     * 向fragment中填充数据
     */
    private void refreshData() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void doRetrieve(TaskAdapter listener) {
        taskListener = listener;
        getData();
    }


    class RetrieveTask extends GenericTask {
        public static final String TAG = "RetrieveTask";

        @Override
        protected TaskResult _doInBackground(TaskParams... params) {
            try {
                bids = app.wzd.retrieveData();
                refreshData();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                return TaskResult.IO_ERROR;
            }
            return TaskResult.OK;
        }
    }

    TaskAdapter mRetrieveCB = new TaskAdapter() {
        @Override
        public String getName() {
            return "RetrieveTask";
        }

        @Override
        public void onPreExecute(GenericTask task) {
            TaskFeedback.getInstance(TaskFeedback.DIALOG_MODE, MainActivity.this).start("看看有什么新的标");
        }

        @Override
        public void onCancelled(GenericTask task) {
            TaskFeedback.getInstance(TaskFeedback.DIALOG_MODE, MainActivity.this).cancel();
        }

        @Override
        public void onPostExecute(GenericTask task, TaskResult result) {
            if (result == TaskResult.AUTH_ERROR) {
            } else if (result == TaskResult.OK) {
                TaskFeedback.getInstance(TaskFeedback.DIALOG_MODE, MainActivity.this).success("刷新投标信息完成");
            } else if (result == TaskResult.IO_ERROR) {

            }
        }
    };
}
