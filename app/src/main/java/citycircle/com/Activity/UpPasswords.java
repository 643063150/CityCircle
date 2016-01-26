package citycircle.com.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;
import citycircle.com.Utils.HttpRequest;

/**
 * Created by admins on 2015/12/4.
 */
public class UpPasswords extends Activity implements View.OnClickListener {
    EditText password;
    String url, urlstr,number,code;
    Button submit;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uppass);
        url = GlobalVariables.urlstr + "User.updatePass";
        number=getIntent().getStringExtra("number");
        code=getIntent().getStringExtra("code");
        intview();
    }

    public void intview() {
        back = (ImageView) findViewById(R.id.back);
        password = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        back.setOnClickListener(this);
    }
    public void getstr(){
       new Thread(){
           @Override
           public void run() {
               super.run();
               HttpRequest httpRequest=new HttpRequest();
               urlstr=httpRequest.doGet(url);
               if (urlstr.equals("网络超时")){
                   handler.sendEmptyMessage(2);
               }else {
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
                    JSONObject jsonObject = JSON.parseObject(urlstr);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    if (jsonObject1.getIntValue("code") == 0) {
                        Toast.makeText(UpPasswords.this, "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(UpPasswords.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    Toast.makeText(UpPasswords.this, "网络似乎有问题了", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                if (password.getText().toString().trim().length() == 0) {
                    Toast.makeText(UpPasswords.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                } else {
                    url = GlobalVariables.urlstr + "User.updatePass&mobile=" + number + "&password=" + password.getText().toString()+"&code="+code;
                    getstr();
                }
                break;
        }
    }
}
