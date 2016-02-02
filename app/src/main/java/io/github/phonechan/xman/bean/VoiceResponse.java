package io.github.phonechan.xman.bean;

import java.util.List;

/**
 * Created by apple on 16/2/2.
 */
public class VoiceResponse {


    /**
     * sn : 1
     * ls : false
     * bg : 0
     * ed : 0
     * ws : [{"bg":0,"cw":[{"sc":0,"w":"大头"}]},{"bg":0,"cw":[{"sc":0,"w":"死"}]},{"bg":0,"cw":[{"sc":0,"w":"变态"}]}]
     */

    private int sn;
    private boolean ls;
    private int bg;
    private int ed;
    /**
     * bg : 0
     * cw : [{"sc":0,"w":"大头"}]
     */

    private List<WsEntity> ws;

    public void setSn(int sn) {
        this.sn = sn;
    }

    public void setLs(boolean ls) {
        this.ls = ls;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public void setEd(int ed) {
        this.ed = ed;
    }

    public void setWs(List<WsEntity> ws) {
        this.ws = ws;
    }

    public int getSn() {
        return sn;
    }

    public boolean isLs() {
        return ls;
    }

    public int getBg() {
        return bg;
    }

    public int getEd() {
        return ed;
    }

    public List<WsEntity> getWs() {
        return ws;
    }

    public static class WsEntity {
        private int bg;
        /**
         * sc : 0.0
         * w : 大头
         */

        private List<CwEntity> cw;

        public void setBg(int bg) {
            this.bg = bg;
        }

        public void setCw(List<CwEntity> cw) {
            this.cw = cw;
        }

        public int getBg() {
            return bg;
        }

        public List<CwEntity> getCw() {
            return cw;
        }

        public static class CwEntity {
            private double sc;
            private String w;

            public void setSc(double sc) {
                this.sc = sc;
            }

            public void setW(String w) {
                this.w = w;
            }

            public double getSc() {
                return sc;
            }

            public String getW() {
                return w;
            }
        }
    }
}
