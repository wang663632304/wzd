package org.yftx.wzd.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.gfan.sdk.statitistics.GFAgent;
import com.github.eddieringle.android.libs.undergarment.widgets.DrawerGarment;
import com.viewpagerindicator.TitlePageIndicator;
import org.xmlpull.v1.XmlPullParserException;
import org.yftx.wzd.R;
import org.yftx.wzd.WZDApplication;
import org.yftx.wzd.domain.Bid;
import org.yftx.wzd.ui.adapter.ContentAdapter;
import org.yftx.wzd.ui.base.Refreshable;
import org.yftx.wzd.utils.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-9
 */
public class MainActivity extends SherlockFragmentActivity implements Refreshable, ActionBar.OnNavigationListener {
    private ContentAdapter mAdapter;
    private ViewPager mPager;
    public List<Bid> bids = new ArrayList<Bid>();
    private WZDApplication app;
    ActionBar ab;
    TextView tv_bidCount;
    DrawerGarment mDrawerGarment;
    Boolean isOpenDashBoard = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initDashboard();
        app = (WZDApplication) getApplication();
        configureActionBar();
        initView();
    }

    /**
     * 初始化设置面板
     */
    private void initDashboard() {
        mDrawerGarment = new DrawerGarment(this, R.layout.dashboard);
        mDrawerGarment.setDrawerCallbacks(new DrawerGarment.IDrawerCallbacks() {
            @Override
            public void onDrawerOpened() {
                isOpenDashBoard = true;
            }

            @Override
            public void onDrawerClosed() {
                isOpenDashBoard = false;
            }
        });

    }

    private void configureActionBar() {
        ab = getSupportActionBar();
        if (ab == null)
            return;
        ab.setCustomView(R.layout.title_count);
        ab.setDisplayShowCustomEnabled(true);
        ab.setLogo(R.drawable.app_ico);
        /**
         Context context = getSupportActionBar().getThemedContext();
         ArrayAdapter<CharSequence> listAdapter = ArrayAdapter.createFromResource(context, R.array.classify, R.layout.sherlock_spinner_item);
         listAdapter.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
         ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
         ab.setListNavigationCallbacks(listAdapter, null);
         */
        ab.setHomeButtonEnabled(true);
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
    private void getData() throws IOException, XmlPullParserException {
        bids = app.wzd.retrieveData();
    }

    /**
     * 向fragment中填充数据
     */
    public void freshUI() {
        Logger.d("当前标的数量" + bids.size());
        tv_bidCount.setText(bids.size() + "");
//        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void doRetrieve() throws IOException, XmlPullParserException {
        getData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!isOpenDashBoard) {
                    mDrawerGarment.openDrawer();
                } else {
                    mDrawerGarment.closeDrawer();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        Logger.d("MainActivity  itemPosition  -->  " + itemPosition);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerGarment.isDrawerMoving())
            return;
        if (!isOpenDashBoard)
            mDrawerGarment.openDrawer();
        else
            super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GFAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GFAgent.onPause(this);
    }

}
