package citycircle.com.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import citycircle.com.Adapter.MyMessageItem;
import citycircle.com.JsonMordel.MessageList;
import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.PreferencesUtils;
import okhttp3.Call;

/**
 * Created by admins on 2016/6/27.
 */
public class MyMessageList extends Activity {
    TextView title;
    ListView viplist;
    ImageView back;
    MessageList messageList;
    List<MessageList.DataBean.InfoBean> list = new ArrayList<>();
    String url;
    MyMessageItem myMessageItem;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymessage_list);
        type=getIntent().getIntExtra("type",1);
        intview();
        String username = PreferencesUtils.getString(MyMessageList.this, "username");
        String uid = PreferencesUtils.getString(MyMessageList.this, "userid");
        url= GlobalVariables.urlstr+"user.getMessageslist&uid="+uid+"&username="+username+"&type="+type;
        setViplist();
        getJson();
    }

    private void intview() {
        title=(TextView)findViewById(R.id.title) ;
        viplist = (ListView) findViewById(R.id.viplist);
        back = (ImageView) findViewById(R.id.back);
        if (type==1){
            title.setText("系统消息");
        }else if (type==2){
            title.setText("小区消息");
        }else {
            title.setText("会员卡消息");
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viplist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.putExtra("title",list.get(position).getTitle());
                intent.putExtra("times",list.get(position).getCreate_time());
                intent.putExtra("contents",list.get(position).getContent());
                intent.setClass(MyMessageList.this,MessageContent.class);
                MyMessageList.this.startActivity(intent);
            }
        });
    }

    private void getJson() {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(MyMessageList.this, R.string.intent_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                messageList = JSON.parseObject(response, MessageList.class);
                if (messageList.getData().getCode()==0){
                    list.addAll(messageList.getData().getInfo());
                }
                myMessageItem.notifyDataSetChanged();
            }
        });
    }
    private void setViplist(){
        myMessageItem=new MyMessageItem(list,MyMessageList.this);
        viplist.setAdapter(myMessageItem);
    }
}