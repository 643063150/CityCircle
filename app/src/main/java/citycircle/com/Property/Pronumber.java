package citycircle.com.Property;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.Toast;

import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.HttpRequest;
import citycircle.com.Utils.PreferencesUtils;

/**
 * Created by 飞侠 on 2016/3/18.
 */
public class Pronumber extends Activity {
    ListView numberlist;
    String url, urlstr, uid, username,houseid;
    SwipeRefreshLayout Refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pronumber);
        uid = PreferencesUtils.getString(Pronumber.this, "userid");
        username = PreferencesUtils.getString(Pronumber.this, "username");
        houseid = PreferencesUtils.getString(Pronumber.this, "houseids");
        url = GlobalVariables.urlstr + "Wuye.getUserNewsList&uid=" + uid + "&username=" + username+"&houseid="+houseid;
        intview();
    }
    private void intview() {
        numberlist = (ListView) findViewById(R.id.numberlists);
        Refresh=(SwipeRefreshLayout)findViewById(R.id.Refresh);
    }
    private void getmessagelist() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                HttpRequest httpRequest = new HttpRequest();
                urlstr = httpRequest.doGet(url);
                if (urlstr.equals("网络超时")) {
                    handler.sendEmptyMessage(2);
                } else {
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    break;
                case 2:
                    Toast.makeText(Pronumber.this, R.string.intent_error, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(Pronumber.this, R.string.nomore, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
