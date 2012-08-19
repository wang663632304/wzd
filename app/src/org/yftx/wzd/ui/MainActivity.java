package org.yftx.wzd.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.TitlePageIndicator;
import org.yftx.common.task.*;
import org.yftx.wzd.R;
import org.yftx.wzd.WZDApplication;
import org.yftx.wzd.domain.Bid;
import org.yftx.wzd.ui.adapter.ContentAdapter;
import org.yftx.wzd.ui.base.Refreshable;
import org.yftx.wzd.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-9
 */
public class MainActivity extends SherlockFragmentActivity implements Refreshable , ActionBar.OnNavigationListener{
    private ContentAdapter mAdapter;
    private ViewPager mPager;
    protected GenericTask mRetrieveTask;
    public List<Bid> bids = new ArrayList<Bid>();
    private WZDApplication app;
    private TaskAdapter taskListener;
    ActionBar ab;
    TextView tv_bidCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        app = (WZDApplication) getApplication();
        handleABar();
        initView();
    }

    private void handleABar() {
        ab = getSupportActionBar();
        if (ab == null)
            return;
        ab.setCustomView(R.layout.title_count);
        ab.setDisplayShowCustomEnabled(true);
        ab.setLogo(R.drawable.logo);

        Context context = getSupportActionBar().getThemedContext();
        ArrayAdapter<CharSequence> listAdapter = ArrayAdapter.createFromResource(context, R.array.classify, R.layout.sherlock_spinner_item);
        listAdapter.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        ab.setListNavigationCallbacks(listAdapter, null);
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

        tv_bidCount = (TextView) findViewById(R.id.tv_bidCount);
    }

    /**
     * 向网络请求数据。
     */
    private void getData() {
        mRetrieveTask = new RetrieveTask();
        mRetrieveTask.setListener(taskListener);
        mRetrieveTask.execute();
    }

    /**
     * 向fragment中填充数据
     */
    public void freshUI() {
        Logger.d("当前标的数量" + bids.size());
        tv_bidCount.setText(bids.size() + "");
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void doRetrieve(TaskAdapter listener) {
        taskListener = listener;
        getData();
    }


    class RetrieveTask extends GenericTask {

        @Override
        protected TaskResult _doInBackground(TaskParams... params) {
            try {
                bids = app.wzd.retrieveData();
            } catch (Exception e) {
                Logger.e(e.getMessage());
                return TaskResult.IO_ERROR;
            }
            return TaskResult.OK;
        }
    }


    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        Logger.d("MainActivity  itemPosition  -->  " + itemPosition);
        return true;
    }
}
