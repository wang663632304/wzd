package org.yftx.wzd.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.gfan.sdk.statitistics.GFAgent;
import org.yftx.wzd.R;
import org.yftx.wzd.domain.Bid;
import org.yftx.wzd.ui.adapter.InfoAdapter;
import org.yftx.wzd.ui.base.Refreshable;
import org.yftx.wzd.utils.DateTimeHelper;
import org.yftx.wzd.utils.Logger;
import yaochangwei.pulltorefreshlistview.widget.RefreshableListView;
import yaochangwei.pulltorefreshlistview.widget.extend.PullToRefreshListView;


/**
 * 表信息显示ui
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-11
 */
public class InfoFragment extends SherlockFragment implements ActionBar.OnNavigationListener {
    static final String KEY_CONTENT = "InfoFragment:Content";
    static final String TAG = "InfoFragment";
    private PullToRefreshListView plv;
    Refreshable refreshable;
    InfoAdapter infoAdapter;

    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            //mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info, container, false);
        plv = (PullToRefreshListView) v.findViewById(R.id.lv_info);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshable = (Refreshable) getActivity();
        infoAdapter = new InfoAdapter(getActivity());
        plv.setAdapter(infoAdapter);
        GFAgent.onEvent(getActivity(), "open app|-->" + DateTimeHelper.getNowTime());
        plv.setOnUpdateTask(new RefreshableListView.OnUpdateTask() {
            public void updateBackground() {
                GFAgent.onEvent(getActivity(), "refresh|-->" + DateTimeHelper.getNowTime());
                refreshData();
            }

            public void updateUI() {
                refreshable.freshUI();
                infoAdapter.notifyDataSetChanged();
            }

            public void onUpdateStart() {
            }
        });


        plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bid bid = (Bid) view.getTag();
                Logger.d("当前点击的item的url为" + bid.getLink_url());
                openUrl(bid.getLink_url());
            }
        });
        plv.startUpdateImmediate();
    }


    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //.putString(KEY_CONTENT, mContent);
    }

    protected void refreshData() {
        try {
            refreshable.doRetrieve();
        } catch (Exception e) {
            Logger.d("请求数据失败  失败原因" + e.toString());
        }
    }


    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        Logger.d("InfoFragment  itemPosition  -->  " + itemPosition);
        return true;
    }
}
