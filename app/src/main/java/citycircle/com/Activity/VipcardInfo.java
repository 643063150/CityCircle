package citycircle.com.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import citycircle.com.JsonMordel.VipInfo;
import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.ImageUtils;
import citycircle.com.Utils.PreferencesUtils;
import okhttp3.Call;

/**
 * Created by admins on 2016/5/31.
 */
public class VipcardInfo extends Activity implements View.OnClickListener {
    CardView cardView;
    TextView shopinfo, cardtype, titile, callphone, adress,shengyu,info,shiy;
    ImageView back, logo;
    VipInfo vipInfo;
    String url,username,id,shopid;
   List<VipInfo.DataBean.InfoBean> list=new ArrayList<VipInfo.DataBean.InfoBean>();
    com.nostra13.universalimageloader.core.ImageLoader ImageLoader;
    DisplayImageOptions options;
    citycircle.com.Utils.ImageUtils ImageUtils;
    ImageLoadingListener animateFirstListener;
    LinearLayout shengyulay;
    int a;
    Button btn_lq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vipcardinfo);
        a = PreferencesUtils.getInt(this, "land");
        id=getIntent().getStringExtra("id");
        if (a==0){
            url= GlobalVariables.urlstr+"Hyk.getArticle&id="+id;
        }else {
            username=PreferencesUtils.getString(this,"username");
            url= GlobalVariables.urlstr+"Hyk.getArticle&id="+id+"&username="+username;
        }
        intview();
        getjson();
    }

    private void intview() {
        btn_lq=(Button)findViewById(R.id.btn_lq);
        info=(TextView)findViewById(R.id.info);
        shiy=(TextView)findViewById(R.id.shiy);
        shengyulay=(LinearLayout)findViewById(R.id.shengyulay);
        shengyu=(TextView)findViewById(R.id.shengyu);
        cardView = (CardView) findViewById(R.id.cardview);
        shopinfo = (TextView) findViewById(R.id.shopinfo);
        cardtype = (TextView) findViewById(R.id.cardtype);
        titile = (TextView) findViewById(R.id.titile);
        callphone = (TextView) findViewById(R.id.callphone);
        adress = (TextView) findViewById(R.id.adress);
        back = (ImageView) findViewById(R.id.back);
        logo = (ImageView) findViewById(R.id.logo);
        shopinfo.setOnClickListener(this);
        btn_lq.setOnClickListener(this);
        back.setOnClickListener(this);
        ImageUtils = new ImageUtils();
        ImageLoader = ImageLoader.getInstance();
        ImageLoader.init(ImageLoaderConfiguration.createDefault(this));
        animateFirstListener = new ImageUtils.AnimateFirstDisplayListener();
    }

    private void getjson() {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(VipcardInfo.this, R.string.intent_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                vipInfo= JSON.parseObject(response,VipInfo.class);
                if (vipInfo.getData().getCode()==0){
                    list.addAll(vipInfo.getData().getInfo());
                    for (int i=0;i<list.size();i++){
                        shopid=list.get(i).getShopid();
                        cardtype.setText(list.get(i).getType());
                        titile.setText(list.get(i).getShopname());
                        callphone.setText("电话:"+list.get(i).getTel());
                        adress.setText("地址:"+list.get(i).getAddress());
                        cardView.setCardBackgroundColor(Color.parseColor(list.get(i).getColor()));
                        options=ImageUtils.setCirclelmageOptions();
                        ImageLoader.displayImage(list.get(i).getLogo(),logo,options,animateFirstListener);
                        if (list.get(i).getOrlq()==0){
                            shengyu.setText("未领取");
                            shiy.setVisibility(View.GONE);
                            info.setText("用户须知");
                            shengyulay.setVisibility(View.GONE);
                        }else {
                            btn_lq.setVisibility(View.GONE);
                            shengyulay.setVisibility(View.VISIBLE);
                            SpannableStringBuilder stringBuilder;
                            if (cardtype.getText().equals("打折卡")){
                                String str="打折额度:"+list.get(i).getValues();
                                stringBuilder=new SpannableStringBuilder(str);
                                stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#21ADFD")),4,str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                shengyu.setText(stringBuilder);
                            }else if (cardtype.getText().equals("计次卡")){
                                String str="剩余次数:"+list.get(i).getValues();
                                stringBuilder=new SpannableStringBuilder(str);
                                stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#21ADFD")),4,str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                shengyu.setText(stringBuilder);
                            }else if (cardtype.getText().equals("充值卡")){
                                String str="剩余金额:"+list.get(i).getValues();
                                stringBuilder=new SpannableStringBuilder(str);
                                stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#21ADFD")),4,str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                shengyu.setText(stringBuilder);
                            }else {
                                String str="剩余积分:"+list.get(i).getValues();
                                stringBuilder=new SpannableStringBuilder(str);
                                stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#21ADFD")),4,str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                shengyu.setText(stringBuilder);
                            }
                        }
                    }
                }else {
                    Toast.makeText(VipcardInfo.this,R.string.nomore,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shopinfo:
                Intent intent = new Intent();
                intent.putExtra("id",shopid);
                intent.putExtra("shopname",titile.getText());
                intent.setClass(VipcardInfo.this, ShopInfo.class);
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.btn_lq:
                Toast.makeText(this,"领取",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
