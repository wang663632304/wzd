package org.yftx.wzd.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import org.yftx.wzd.R;
import org.yftx.wzd.domain.Bid;
import org.yftx.wzd.net.WzdApi;

import java.util.List;

public class MainActivity extends Activity
{
    private static String TAG = "MainActivity";


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btn =(Button)findViewById(R.id.getXml);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    List<Bid> bids = WzdApi.getLastBids();
                    Log.d(TAG,"bids长度"+bids.size());
                } catch (Exception e) {
                    Log.d(TAG,e.toString());
                }
                int a;
            }
        });


    }
}
