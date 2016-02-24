package citycircle.com.Property;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import citycircle.com.R;
import citycircle.com.Utils.DateUtils;
import citycircle.com.Utils.GlobalVariables;

/**
 * Created by admins on 2016/2/24.
 */
public class PayMoney extends Activity implements View.OnClickListener{
    ImageView loginback,typeimg;
    TextView danwei,beizhu,money,time;
    Button submit;
    String type,moneys,id,bak,create_time,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymoney);
        type=getIntent().getStringExtra("type");
        moneys=getIntent().getStringExtra("money");
        id=getIntent().getStringExtra("id");
        bak=getIntent().getStringExtra("bak");
        create_time=getIntent().getStringExtra("create_time");
        status=getIntent().getStringExtra("status");
        intview();
    }
    private void intview(){
        loginback=(ImageView)findViewById(R.id.loginback);
        typeimg=(ImageView)findViewById(R.id.typeimg);
        money=(TextView)findViewById(R.id.money);
        danwei=(TextView)findViewById(R.id.danwei);
        time=(TextView)findViewById(R.id.time);
        beizhu=(TextView)findViewById(R.id.beizhu);
        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(this);
        loginback.setOnClickListener(this);
        if (type.equals("1")){
            typeimg.setImageResource(R.mipmap.iconfontwuyezujin);
        }
        money.setText(moneys);
        beizhu.setText(bak);
        danwei.setText(GlobalVariables.housename);
        time.setText(DateUtils.getDateToStrings(Long.parseLong(create_time)));
        if (status.equals("1")) {
            submit.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginback:
                break;
            case R.id.submit:
                break;
        }
    }
}
