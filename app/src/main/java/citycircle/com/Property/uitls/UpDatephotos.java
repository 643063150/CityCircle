package citycircle.com.Property.uitls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import util.Bimp;
import util.FileUtils;

/**
 * Created by 飞侠 on 2016/2/21.
 */
public class UpDatephotos {
    public String uploadFile(String Url,String uid,String username,String content,String houseid,String type) {
        StringBuilder sb2 = new StringBuilder();
        String BOUNDARY ="ARCFormBoundarymmd8a874lsor";
        String PREFIX = "--", LINEND = "\r\n";
        // String MULTIPART_FROM_DATA = "multipart/form-data";
        final String endline = "--" + BOUNDARY + "--\r\n";//数据结束标志
        String CHARSET = "UTF-8";
        URL httpurl;
        try {
            httpurl = new URL(Url);
            HttpURLConnection conn = (HttpURLConnection) httpurl
                    .openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "utf-8");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + BOUNDARY);

            DataOutputStream outStream = new DataOutputStream(
                    conn.getOutputStream());
            StringBuilder sb = new StringBuilder();
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            sb.append("Content-Disposition: form-data; name=\"" + "uid"
                    + "\"" + LINEND);
            sb.append(LINEND);
            sb.append(uid);
            sb.append(LINEND);
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);

            sb.append("Content-Disposition: form-data; name=\"" + "username" + "\""
                    + LINEND);
            sb.append(LINEND);
            sb.append(username);
            sb.append(LINEND);
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);

            sb.append("Content-Disposition: form-data; name=\"" + "content"
                    + "\"" + LINEND);
            sb.append(LINEND);
            sb.append(content);
            sb.append(LINEND);
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            sb.append("Content-Disposition: form-data; name=\"" + "type"
                    + "\"" + LINEND);
            sb.append(LINEND);
            sb.append(type);
            sb.append(LINEND);
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);

            sb.append("Content-Disposition: form-data; name=\"" + "houseid"
                    + "\"" + LINEND);
            sb.append(LINEND);
            sb.append(houseid);
            sb.append(LINEND);

            outStream.write(sb.toString().getBytes());

            for (int i=0;i< Bimp.tempSelectBitmap.size();i++){
                String filePath=Bimp.tempSelectBitmap.get(i).getImagePath();
                String fileName = String.valueOf(System.currentTimeMillis());
                Bitmap bm = BitmapFactory.decodeFile(filePath.toString());
                File file = FileUtils.saveBitmap(bm, fileName, filePath.toString());
                filePath=file.toString();
                String name="imgFile"+i;
                StringBuilder sb1 = new StringBuilder();
                sb1.append("--");
                sb1.append(BOUNDARY);
                sb1.append("\r\n");
                sb1.append("Content-Disposition: form-data; name=\""+name+"\"; filename=\""
                        + "123.jpg" + "\"" + LINEND);
                sb1.append("Content-Type: image/jpg; charset="
                        + CHARSET + LINEND);
                sb1.append(LINEND);
                outStream.write(sb1.toString().getBytes());

                InputStream is = new FileInputStream(filePath);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
                outStream.write(LINEND.getBytes());

            }
            outStream.write(endline.getBytes());
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
            outStream.write(end_data);
            outStream.flush();
            int res = conn.getResponseCode();
            InputStream in = null;
            if (res == 201||res==200) {
                in = conn.getInputStream();
                int ch;
                while ((ch = in.read()) != -1) {
                    sb2.append((char) ch);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            String str="失败";
            return str;
        }
        return sb2.toString();
    }
}
