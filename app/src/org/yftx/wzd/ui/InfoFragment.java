package org.yftx.wzd.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import org.yftx.wzd.R;
import org.yftx.wzd.ui.adapter.InfoAdapter;
import org.yftx.wzd.ui.custom.PullToRefreshListView;


/**
 * 表信息显示ui
 * <p/>
 * User: yftx
 * Mail: yftx.net@gmail.com
 * Date: 12-8-11
 */
public class InfoFragment extends SherlockFragment {
    private static final String KEY_CONTENT = "InfoFragment:Content";
    private int mCurrentPos;
    private PullToRefreshListView plv;

    public static InfoFragment newInstance(int currentPos ) {
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


//        setListAdapter(new InfoAdapter(getActivity()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putString(KEY_CONTENT, mContent);
    }


}
