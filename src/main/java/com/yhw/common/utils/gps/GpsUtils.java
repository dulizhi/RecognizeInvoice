package com.yhw.common.utils.gps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class GpsUtils {
    private static Logger logger = LoggerFactory.getLogger(GpsUtils.class);
    double casm_rr = 0;
    double casm_t1 = 0;
    double casm_t2 = 0;
    double casm_x1 = 0;
    double casm_y1 = 0;
    double casm_x2 = 0;
    double casm_y2 = 0;
    double casm_f = 0;
//	private static GpsUtils instance = new GpsUtils();


    @Override
    public String toString() {
        return "GpsUtils [casm_rr=" + casm_rr + ", casm_t1=" + casm_t1 + ", casm_t2=" + casm_t2 + ", casm_x1=" + casm_x1 + ", casm_y1=" + casm_y1 + ", casm_x2=" + casm_x2 + ", casm_y2=" + casm_y2 + ", casm_f=" + casm_f + ", x_pi=" + x_pi + "]";
    }

    public GpsUtils() {
        casm_rr = 0;
        casm_t1 = 0;
        casm_t2 = 0;
        casm_x1 = 0;
        casm_y1 = 0;
        casm_x2 = 0;
        casm_y2 = 0;
        casm_f = 0;
    }

//	public static GpsUtils getInstance() {
//		return instance;
//	}

    private double yj_sin2(double x) {
        double tt;
        double ss;
        double ff;
        double s2;
        int cc;
        ff = 0;
        if (x < 0) {
            x = -x;
            ff = 1;
        }
        cc = (int) (x / 6.28318530717959);
        tt = x - cc * 6.28318530717959;
        if (tt > 3.1415926535897932) {
            tt = tt - 3.1415926535897932;
            if (ff == 1) {
                ff = 0;
            } else if (ff == 0) {
                ff = 1;
            }
        }
        x = tt;
        ss = x;
        s2 = x;
        tt = tt * tt;
        s2 = s2 * tt;
        ss = ss - s2 * 0.166666666666667;
        s2 = s2 * tt;
        ss = ss + s2 * 8.33333333333333E-03;
        s2 = s2 * tt;
        ss = ss - s2 * 1.98412698412698E-04;
        s2 = s2 * tt;
        ss = ss + s2 * 2.75573192239859E-06;
        s2 = s2 * tt;
        ss = ss - s2 * 2.50521083854417E-08;
        if (ff == 1) {
            ss = -ss;
        }
        return ss;
    }

    private double Transform_yj5(double x, double y) {
        double tt;
        tt = 300 + 1 * x + 2 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.sqrt(x * x));
        tt = tt + (20 * yj_sin2(18.849555921538764 * x) + 20 * yj_sin2(6.283185307179588 * x)) * 0.6667;
        tt = tt + (20 * yj_sin2(3.141592653589794 * x) + 40 * yj_sin2(1.047197551196598 * x)) * 0.6667;
        tt = tt + (150 * yj_sin2(0.2617993877991495 * x) + 300 * yj_sin2(0.1047197551196598 * x)) * 0.6667;
        return tt;
    }

    private double Transform_yjy5(double x, double y) {
        double tt;
        tt = -100 + 2 * x + 3 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.sqrt(x * x));
        tt = tt + (20 * yj_sin2(18.849555921538764 * x) + 20 * yj_sin2(6.283185307179588 * x)) * 0.6667;
        tt = tt + (20 * yj_sin2(3.141592653589794 * y) + 40 * yj_sin2(1.047197551196598 * y)) * 0.6667;
        tt = tt + (160 * yj_sin2(0.2617993877991495 * y) + 320 * yj_sin2(0.1047197551196598 * y)) * 0.6667;
        return tt;
    }

    private double Transform_jy5(double x, double xx) {
        double n;
        double a;
        double e;
        a = 6378245;
        e = 0.00669342;
        n = Math.sqrt(1 - e * yj_sin2(x * 0.0174532925199433) * yj_sin2(x * 0.0174532925199433));
        n = (xx * 180) / (a / n * Math.cos(x * 0.0174532925199433) * 3.1415926);
        return n;
    }

    private double Transform_jyj5(double x, double yy) {
        double m;
        double a;
        double e;
        double mm;
        a = 6378245;
        e = 0.00669342;
        mm = 1 - e * yj_sin2(x * 0.0174532925199433) * yj_sin2(x * 0.0174532925199433);
        m = (a * (1 - e)) / (mm * Math.sqrt(mm));
        return (yy * 180) / (m * 3.1415926);
    }

    private double random_yj() {
        double t;
        double casm_a = 314159269;
        double casm_c = 453806245;
        casm_rr = casm_a * casm_rr + casm_c;
        t = (int) (casm_rr / 2);
        casm_rr = casm_rr - t * 2;
        casm_rr = casm_rr / 2;
        return (casm_rr);
    }

    private void IniCasm(double w_time, double w_lng, double w_lat) {
        double tt;
        casm_t1 = w_time;
        casm_t2 = w_time;
        tt = (int) (w_time / 0.357);
        casm_rr = w_time - tt * 0.357;
        if (w_time == 0)
            casm_rr = 0.3;
        casm_x1 = w_lng;
        casm_y1 = w_lat;
        casm_x2 = w_lng;
        casm_y2 = w_lat;
        casm_f = 3;
    }

    private EncryptPoint wgtochina_lb(int wg_flag, int wg_lng, int wg_lat, int wg_heit, int wg_week, int wg_time) {
//		logger.info("wgtochina_lb0:"+"wg_flag:"+wg_flag+",wg_lng:"+wg_lng+",wg_lat:"+wg_lat+",wg_heit:"+wg_heit+",wg_week:"+wg_week+",wg_time:"+wg_time);

        EncryptPoint point = null;
        try {
            double x_add;
            double y_add;
            double h_add;
            double x_l;
            double y_l;
            double casm_v;
            double t1_t2;
            double x1_x2;
            double y1_y2;
            point = new EncryptPoint();
            if (wg_heit > 5000) {
                return point;
            }
            x_l = wg_lng;
            x_l = x_l / 3686400.0;
//			logger.info("wgtochina_lb01:"+"x_l="+x_l);
            y_l = wg_lat;
            y_l = y_l / 3686400.0;
//			logger.info("wgtochina_lb02:"+"y_l="+y_l);
            if (x_l < 72.004) {
//				logger.info("wgtochina_lb1:" + JSONObject.fromObject(point).toString());
                return point;
            }
            if (x_l > 137.8347) {
//				logger.info("wgtochina_lb2:" + JSONObject.fromObject(point).toString());
                return point;
            }
            if (y_l < 0.8293) {
//				logger.info("wgtochina_lb3:" + JSONObject.fromObject(point).toString());
                return point;
            }
            if (y_l > 55.8271) {
//				logger.info("wgtochina_lb4:" + JSONObject.fromObject(point).toString());
                return point;
            }
//			logger.info("wgtochina_lb5:" + wg_flag + "/" + wg_lng + "/" + wg_lat + "/" + wg_heit + "/" + wg_week + "/" + wg_time);
            if (wg_flag == 0) {
                IniCasm(wg_time, wg_lng, wg_lat);
                point.setLatitue(wg_lng);
                point.setLongitude(wg_lat);
//				logger.info("wgtochina_lb6:" + JSONObject.fromObject(point).toString());
                return point;
            }
            casm_t2 = wg_time;
            t1_t2 = (casm_t2 - casm_t1) / 1000.0;
//			logger.info("wgtochina_lb7:" + t1_t2 + "/" + casm_t1 + "/" + casm_t2);
            if (t1_t2 <= 0) {
                casm_t1 = casm_t2;
                casm_f = casm_f + 1;
                casm_x1 = casm_x2;
                casm_f = casm_f + 1;
                casm_y1 = casm_y2;
                casm_f = casm_f + 1;
            } else {
                if (t1_t2 > 120) {
                    if (casm_f == 3) {
                        casm_f = 0;
                        casm_x2 = wg_lng;
                        casm_y2 = wg_lat;
                        x1_x2 = casm_x2 - casm_x1;
                        y1_y2 = casm_y2 - casm_y1;
                        casm_v = Math.sqrt(x1_x2 * x1_x2 + y1_y2 * y1_y2) / t1_t2;
                        if (casm_v > 3185) {
//							logger.info("wgtochina_lb8:" + JSONObject.fromObject(point).toString() + "/" + casm_v + "/" + x1_x2 + "/" + y1_y2 + "/" + t1_t2);
                            return (point);
                        }
                    }
                    casm_t1 = casm_t2;
                    casm_f = casm_f + 1;
                    casm_x1 = casm_x2;
                    casm_f = casm_f + 1;
                    casm_y1 = casm_y2;
                    casm_f = casm_f + 1;
//					logger.info("wgtochina_lb9:" + casm_t1 + "/" + casm_f + "/" + casm_x1 + "/" + casm_f + "/" + casm_y1 + "/" + casm_f);
                }
            }
//			logger.info("wgtochina_lb10:" + x_l + "/" + y_l);
            x_add = Transform_yj5(x_l - 105, y_l - 35);
            y_add = Transform_yjy5(x_l - 105, y_l - 35);
//			logger.info("wgtochina_lb11:" + x_add + "/" + y_add);
            h_add = wg_heit;
            x_add = x_add + h_add * 0.001 + yj_sin2(wg_time * 0.0174532925199433) + random_yj();
            y_add = y_add + h_add * 0.001 + yj_sin2(wg_time * 0.0174532925199433) + random_yj();
//			logger.info("wgtochina_lb12:" + x_add + "/" + y_add);
            point.setX((x_l + Transform_jy5(y_l, x_add)) * 3686400);
            point.setY((y_l + Transform_jyj5(y_l, y_add)) * 3686400);
//			logger.info("wgtochina_lb13:" + JSONObject.fromObject(point).toString());
        } catch (Exception e) {
            logger.error("wgtochina_lb异常：" + e.getLocalizedMessage());
        }
        return point;
    }

    /**
     * 对真实的经纬度进行加密操作
     *
     * @param x 十进制经度
     * @param y 十进制纬度
     * @return res[0] 经度，res[1]纬度
     */
    public final double[] getEncryptXY(double x, double y) {

//		logger.info("对真实的经纬度进行加密操作,x=" + x + ",y=" + y);
        double[] res = new double[2];
        EncryptPoint point;
        double x1, tempx;
        double y1, tempy;
        x1 = x * 3686400.0;
        y1 = y * 3686400.0;
        double gpsWeek = 0;
        double gpsWeekTime = 0;
        double gpsHeight = 0;
        point = wgtochina_lb(1, (int) (x1), (int) (y1), (int) (gpsHeight), (int) (gpsWeek), (int) (gpsWeekTime));
        tempx = point.getX();
        tempy = point.getY();
        tempx = tempx / 3686400.0;
        tempy = tempy / 3686400.0;
//		logger.info("对真实的经纬度进行加密操作,tempx=" + tempx + ",tempy=" + tempy+"，"+this.toString());
        res[0] = (round(tempx, 5));
        res[1] = (round(tempy, 5));

        return res;
    }


    /**
     * 对数据库中原始的整形GPS坐标进行加密
     *
     * @param intX
     * @param intY
     * @return res[0] 经度，res[1]纬度
     */
    public final double[] getEncryptXY(int intX, int intY) {
        return getEncryptXY(chageRad(intX), chageRad(intY));
    }

    double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    /**
     * 对真实的经纬度进行百度加密操作
     *
     * @param x 经度
     * @param y 纬度
     * @return res[0] 经度，res[1]纬度
     */
    public final double[] getBaiduEncryptXY(double x, double y) {
//		logger.info("对真实的经纬度进行加密操作（百度）,x=" + x + ",y=" + y);
        double[] tmpXY = this.getEncryptXY(x, y);
        x = tmpXY[0];
        y = tmpXY[1];
        double bd_lat, bd_lon;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        bd_lon = z * Math.cos(theta) + 0.0065;
        bd_lat = z * Math.sin(theta) + 0.006;
        double[] res = new double[2];
//		logger.info("对真实的经纬度进行加密操作（百度）,bd_lon=" + bd_lon + ",bd_lat=" + bd_lat);
        res[0] = (round(bd_lon, 5));
        res[1] = (round(bd_lat, 5));
        return res;
    }

    /**
     * 对数据库中的经纬度进行百度加密操作
     *
     * @param x
     * @param y
     * @return res[0] 经度，res[1]纬度
     */
    public final double[] getBaiduEncryptXY(int intX, int intY) {
        return getBaiduEncryptXY(chageRad(intX), chageRad(intY));
    }

    /**
     * 对double进行四舍五入
     *
     * @param v
     * @param scale
     * @return
     */
    private double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        try {
//			logger.info("对double进行四舍五入,参数：v is " + v + ",scale is " + scale);
            BigDecimal b = new BigDecimal(Double.toString(v));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception e) {
            logger.error("对double进行四舍五入异常：v is " + v + ",scale is " + scale + e.getLocalizedMessage());
            return new BigDecimal(v).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        // 修改四舍五入方法
        // double value=0;
        // try {
        // value = new BigDecimal(v).setScale(scale, BigDecimal.ROUND_HALF_UP)
        // .doubleValue();
        // } catch (Exception e) {
        // logger.error("对double进行四舍五入,异常v is："+v+",scale is "+scale+e.getLocalizedMessage());
        // }
        // return value;
    }

    /**
     * 临时中间坐标控制
     *
     * @author 张庆旭(zhang.qingxu @ byd.com)
     * @since 2014年12月22日
     */
    public class EncryptPoint {
        /**
         * 可以使用的偏移后的经度
         */
        private double x;
        /**
         * 可以使用的偏移后的纬度
         */
        private double y;
        /**
         *
         */
        private double longitude;
        /**
         *
         */
        private double latitue;

        /**
         * @return the x
         */
        public double getX() {
            return this.x;
        }

        /**
         * @param x the x to set
         */
        public void setX(double x) {
            this.x = x;
        }

        /**
         * @return the y
         */
        public double getY() {
            return this.y;
        }

        /**
         * @param y the y to set
         */
        public void setY(double y) {
            this.y = y;
        }

        /**
         * @return the longitude
         */
        public double getLongitude() {
            return this.longitude;
        }

        /**
         * @param longitude the longitude to set
         */
        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        /**
         * @return the latitue
         */
        public double getLatitue() {
            return this.latitue;
        }

        /**
         * @param latitue the latitue to set
         */
        public void setLatitue(double latitue) {
            this.latitue = latitue;
        }
    }

    /**
     * 百度坐标
     *
     * @author 张庆旭(zhang.qingxu @ byd.com)
     * @since 2014年12月22日
     */
    public class BaiduEncryptPoint {
        /**
         * 可以使用的偏移后的经度
         */
        private double x;
        /**
         * 可以使用的偏移后的纬度
         */
        private double y;

        /**
         * @return the x
         */
        public double getX() {
            return this.x;
        }

        /**
         * @param x the x to set
         */
        public void setX(double x) {
            this.x = x;
        }

        /**
         * @return the y
         */
        public double getY() {
            return this.y;
        }

        /**
         * @param y the y to set
         */
        public void setY(double y) {
            this.y = y;
        }

    }

    public static double chageRad(int val) {
        if (val >= 0) {
            return Math
                    .round((val / 1000000 + (val - (val / 1000000) * 1000000) / 600000.0) * 100000.0) / 100000.0;
        } else {
            val = -val;
            return -Math
                    .round((val / 1000000 + (val - (val / 1000000) * 1000000) / 600000.0) * 100000.0) / 100000.0;
        }

    }

    /**
     * 测试用例
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        GpsUtils util = new GpsUtils();
        double[] str = util.getBaiduEncryptXY(114212102, 22407374);
        String currentPosition = new GeocoderUtils().getCurrentPosition(str);
        System.out.println(currentPosition);
    }
}