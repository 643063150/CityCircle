package citycircle.com.Property;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import citycircle.com.Property.PropertyAdapter.PayAdapter;
import citycircle.com.R;

/**
 * Created by 飞侠 on 2016/2/23.
 */
public class Payment extends Activity implements View.OnClickListener {
    ImageView back;
    ListView paymentlist;
    ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
    HashMap<String, Object> hashMap;
    PayAdapter payAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        intview();
    }

    private void intview() {
        back = (ImageView) findViewById(R.id.back);
        paymentlist = (ListView) findViewById(R.id.paymentlist);
        back.setOnClickListener(this);
        setArrayList();
        paymentlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setClass(Payment.this,paymentInfo.class);
                Payment.this.startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    private void setArrayList() {
        String title[] = new String[]{"电费", "物业费"};
        int drable[] = new int[]{R.mipmap.iconfontdian, R.mipmap.iconfontwuyejiaofei};
        for (int i=0;i<title.length;i++){
            hashMap=new HashMap<>();
            hashMap.put("title",title[i]);
            hashMap.put("icon",drable[i]);
            arrayList.add(hashMap);
        }
        payAdapter=new PayAdapter(arrayList,Payment.this);
        paymentlist.setAdapter(payAdapter);
    }
}
