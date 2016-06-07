package citycircle.com.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import citycircle.com.R;

/**
 * Created by admins on 2016/6/3.
 */
public class Mymessage extends Activity implements View.OnClickListener{
    ListView mylist;
    String[] item = new String[]{"系统消息", "小区消息", "会员卡消息"};
    String[] items = new String[]{"点击查看系统消息", "点击查看小区消息", "点击查看会员卡消息"};
    int[] drable = new int[]{R.mipmap.my_system3x, R.mipmap.my_msgx, R.mipmap.my_vipx};
    ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    HashMap<String, Object> map;
    SimpleAdapter adapter;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymessage);
        intview();
        setMyListView();
    }
    private void  intview(){
        mylist=(ListView)findViewById(R.id.mylist);
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);
    }
    public void setMyListView() {
        for (int i = 0; i < drable.length; i++) {
            map = new HashMap<String, Object>();
            map.put("drable", drable[i]);
            map.put("item", item[i]);
            map.put("items", items[i]);
            list.add(map);
        }
        adapter = new SimpleAdapter(Mymessage.this, list, R.layout.mymessagelist, new String[]{"drable", "item","items"}, new int[]{R.id.drable, R.id.item,R.id.look});
        mylist.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
