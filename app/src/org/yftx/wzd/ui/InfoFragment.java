package org.yftx.wzd.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import org.yftx.common.task.GenericTask;
import org.yftx.common.task.TaskAdapter;
import org.yftx.common.task.TaskFeedback;
import org.yftx.common.task.TaskResult;
import org.yftx.wzd.R;
import org.yftx.wzd.ui.adapter.InfoAdapter;
import org.yftx.wzd.ui.base.Refreshable;
import org.yftx.wzd.ui.custom.PullToRefreshListView;


/**
 * 表信息显示ui
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-11
 */
public class InfoFragment extends SherlockFragment {
    private static final String KEY_CONTENT = "InfoFragment:Content";
    private int mCurrentPos;
    private PullToRefreshListView plv;

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
        plv.setAdapter(new InfoAdapter(getActivity()));
        plv.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((Refreshable) getActivity()).doRetrieve(mFragListener);
            }
        });
//        setListAdapter(new InfoAdapter(getActivity()));
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
            plv.onRefreshComplete();
            TaskFeedback.getInstance(TaskFeedback.DIALOG_MODE, getActivity()).start("刷新标信息");
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
                plv.onRefreshComplete();
                TaskFeedback.getInstance(TaskFeedback.DIALOG_MODE, getActivity()).success("刷新投标信息完成");
            } else if (result == TaskResult.IO_ERROR) {

            }
        }
    };

}
