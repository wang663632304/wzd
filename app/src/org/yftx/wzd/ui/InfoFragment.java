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
import org.yftx.common.task.GenericTask;
import org.yftx.common.task.TaskAdapter;
import org.yftx.common.task.TaskFeedback;
import org.yftx.common.task.TaskResult;
import org.yftx.wzd.R;
import org.yftx.wzd.domain.Bid;
import org.yftx.wzd.ui.adapter.InfoAdapter;
import org.yftx.wzd.ui.base.Refreshable;
import org.yftx.wzd.ui.custom.PullToRefreshListView;
import org.yftx.wzd.utils.Logger;


/**
 * 表信息显示ui
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-11
 */
public class InfoFragment extends SherlockFragment implements ActionBar.OnNavigationListener {
    static final String KEY_CONTENT = "InfoFragment:Content";
    static final String TAG = "InfoFragment";
    private int mCurrentPos;
    private PullToRefreshListView plv;
    Refreshable refreshable;

    public static InfoFragment newInstance(int currentPos) {
        InfoFragment fragment = new InfoFragment();
        fragment.mCurrentPos = currentPos;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {

//            mContent = savedInstanceState.getString(KEY_CONTENT);
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
        plv.setAdapter(new InfoAdapter(getActivity()));
        plv.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bid bid = (Bid) view.getTag();
                Logger.d("当前点击的item的url为"+ bid.getLink_url());
                openUrl(bid.getLink_url());
            }
        });
        refreshData();
    }


    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putString(KEY_CONTENT, mContent);
    }


    TaskAdapter mFragListener = new TaskAdapter() {
        @Override
        public String getName() {
            return "mFragListener";
        }

        @Override
        public void onPreExecute(GenericTask task) {
            Logger.d("开始请求服务器");
            plv.onRefreshComplete();
            TaskFeedback.getInstance(TaskFeedback.DIALOG_MODE, getActivity()).start("刷新投标信息");
        }

        @Override
        public void onCancelled(GenericTask task) {
            plv.onRefreshComplete();
            TaskFeedback.getInstance(TaskFeedback.DIALOG_MODE, getActivity()).cancel();
        }

        @Override
        public void onPostExecute(GenericTask task, TaskResult result) {
            if (result == TaskResult.AUTH_ERROR) {
            } else if (result == TaskResult.OK) {
                Logger.d("刷新投标信息完成");
                plv.onRefreshComplete();
                refreshable.freshUI();
                TaskFeedback.getInstance(TaskFeedback.DIALOG_MODE, getActivity()).success("刷新投标信息完成");
                goTop();
            } else if (result == TaskResult.IO_ERROR) {
                Logger.d("刷新投标信息失败");
                plv.onRefreshComplete();
                TaskFeedback.getInstance(TaskFeedback.DIALOG_MODE, getActivity()).success("刷新投标信息失败");
            }
        }
    };

    public void goTop() {
        plv.setSelection(1);
    }

    protected void refreshData() {
        refreshable.doRetrieve(mFragListener);
    }


    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        Logger.d("InfoFragment  itemPosition  -->  " + itemPosition);
        return true;
    }
}
