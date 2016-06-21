package citycircle.com.JsonMordel;

import java.util.List;

/**
 * Created by admins on 2016/6/21.
 */
public class WallJsonMo {

    /**
     * ret : 200
     * data : {"code":0,"msg":"","info":[{"money":"100","value":"100","create_time":"1466415609","shopname":"怀府网","type":"1"},{"money":"0","value":"10","create_time":"1463737248","shopname":"怀府网","type":"2"}]}
     * msg :
     */

    private int ret;
    /**
     * code : 0
     * msg :
     * info : [{"money":"100","value":"100","create_time":"1466415609","shopname":"怀府网","type":"1"},{"money":"0","value":"10","create_time":"1463737248","shopname":"怀府网","type":"2"}]
     */

    private DataBean data;
    private String msg;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private int code;
        private String msg;
        /**
         * money : 100
         * value : 100
         * create_time : 1466415609
         * shopname : 怀府网
         * type : 1
         */

        private List<InfoBean> info;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean {
            private String money;
            private String value;
            private long create_time;
            private String shopname;
            private int type;

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public String getShopname() {
                return shopname;
            }

            public void setShopname(String shopname) {
                this.shopname = shopname;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
