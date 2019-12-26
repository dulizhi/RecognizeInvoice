package com.yhw.common.constant;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhang.jiali4
 * @title GlobalConstants
 * @description
 * @date 2019/10/18
 * @copyright Copyright ? 2010-2020 BYD Corporation. All rights reserved.
 */
public class GlobalConstants{
    //公用字串
    public static final String OPERATION_000000 = "000000";//OPERATION_SUCCESS
    public static final String OPERATION_100001 = "100001";//INVALID_PARAMS
    public static final String OPERATION_100002 = "100002";//HANDLE_EXCEPTION
    public static final String OPERATION_100003 = "100003";//OPERATION_FAIL
    public static final String OPERATION_100004 = "100004";//INVALID_URL
    public static final String OPERATION_100005 = "100005";//VEHICLE_OFFLINE
    public static final String OPERATION_100006 = "100006";//AUTH_FAIL
    public static final String OPERATION_100007 = "100007";//INVALID_SIGN
    public static final String OPERATION_SUCCESS = "OPERATION_SUCCESS";
    public static final String INVALID_PARAMS = "INVALID_PARAMS";
    public static final String HANDLE_EXCEPTION = "HANDLE_EXCEPTION";
    public static final String OPERATION_FAIL = "OPERATION_FAIL";
    public static final String INVALID_URL = "INVALID_URL";
    public static final String VEHICLE_OFFLINE = "VEHICLE_OFFLINE";
    public static final String AUTH_FAIL = "AUTH_FAIL";
    public static final String INVALID_SIGN = "INVALID_SIGN";
    public static final String BAIDU_API_TOKEN = "BAIDU_API_TOKEN";
    public static final String EMPTYSTR = "";
    public static final String INVALID = "INVALID";

    //前端显示
    public static final String WEB_PARAM_INVALID = "参数有误";
    public static final String WEB_VIN_UNFIND = "未找到车辆";
    public static final String WEB_OPE_FAIL = "操作失败";
    public static final String WEB_OPE_SUCCESS = "操作成功";
    public static final String WEB_OPE_EXCEPTION = "操作异常";
    public static final String WEB_OPE_EXECUTING= "操作执行中";
    public static final String WEB_DATA_UNFIND = "未找相关数据";

    public static final ConcurrentHashMap<String, String> resultCodeMap = new ConcurrentHashMap<String, String>();
    static {
        resultCodeMap.put(OPERATION_SUCCESS, OPERATION_000000);
        resultCodeMap.put(INVALID_PARAMS, OPERATION_100001);
        resultCodeMap.put(HANDLE_EXCEPTION, OPERATION_100002);
        resultCodeMap.put(OPERATION_FAIL, OPERATION_100003);
        resultCodeMap.put(INVALID_URL, OPERATION_100004);
        resultCodeMap.put(VEHICLE_OFFLINE, OPERATION_100005);
        resultCodeMap.put(AUTH_FAIL, OPERATION_100006);
        resultCodeMap.put(INVALID_SIGN, OPERATION_100007);
    }

    public static final String INVALID_FILENAME = "INVALID_FILENAME";
    public static final String PARSE_FAIL = "PARSE_FAIL";

    //dbState
    public static final String DB_EXCEPTION = "exception";
    public static final String DB_FAIL = "fail";
    public static final String DB_SUCCESS = "success";
    public static final String DB_TIMEOUT = "timeout";
    public static final String DB_EXECUTE = "execute";
    public static final String DB_FILENAME_ERROW = "filename error";
    public static final String DB_UNKNOW_LICENCE = "unknow licence";
    public static final String OPE_ADD = "导入";
    public static final String OPE_MODIFY = "修改";
    public static final String OPE_DELETE = "删除";

    //证件名称
    public static final String LICENSE_VEHICLE_HOME = "行驶证正面";
    public static final String LICENSE_VEHICLE_SUB = "行驶证副本";
    public static final String LICENSE_VEHICLE_BACK = "行驶证反面";
    public static final String LICENSE_REGIST = "登记证";
    public static final String LICENSE_INVOICE = "发票";
    public static final String LICENSE_DEDUCT = "发票抵扣联";
    public static final String LICENSE_DUTY_PAID = "完税证明";
    public static final String LICENSE_NETCAR_FRONT = "网络运输证正本";
    public static final String LICENSE_NETCAR_BACK = "网络运输证副本";
    public static final String LICENSE_NETCAR_CERTIFICATE = "网约车证";
    public static final String LICENSE_NETCAR_CERTIFICATE2= "网约车证2";
    public static final String LICENSE_QUALITY = "合格证";
    public static final String LICENSE_INSURE = "保险单";
    public static final String LICENSE_INSURE_SIGN = "保险标识";
    public static final String LICENSE_TAX = "发票报税联";

    //证件录入状态更新标记
    public static final String LICENSE_VLHOME_DB = "1000000000000";
    public static final String LICENSE_VLSUB_DB = "0100000000000";
    public static final String LICENSE_VLBACK_DB = "0010000000000";
    public static final String LICENSE_REGIST_DB = "0001000000000";
    public static final String LICENSE_INVOICE_DB = "0000100000000";
    public static final String LICENSE_DEDUCT_DB = "0000010000000";
    public static final String LICENSE_DUTYPAID_DB = "0000001000000";
    public static final String LICENSE_NCFRONT_DB = "0000000100000";
    public static final String LICENSE_NCBACK_DB = "0000000010000";
    public static final String LICENSE_QUALITY_DB = "0000000001000";
    public static final String LICENSE_INSURE_DB = "0000000000100";
    public static final String LICENSE_INSURES_DB = "0000000000010";
    public static final String LICENSE_TAX_DB = "0000000000001";
    public static final String LICENSE_FULL_DB = "1111111111111";

    public static final List<String> licenceList = Arrays.asList(
            LICENSE_REGIST, LICENSE_INVOICE, LICENSE_DEDUCT,
            LICENSE_TAX, LICENSE_VEHICLE_HOME, LICENSE_VEHICLE_BACK,
            LICENSE_VEHICLE_SUB, LICENSE_QUALITY, LICENSE_NETCAR_FRONT,
            LICENSE_NETCAR_BACK, LICENSE_DUTY_PAID, LICENSE_INSURE,
            LICENSE_INSURE_SIGN
    );
    public static final ConcurrentHashMap<String, String> templateSignMap = new ConcurrentHashMap<String, String>();
    static {
        templateSignMap.put(LICENSE_VEHICLE_HOME, "d6eac95f85d150084ae7f2162d40b390");
        templateSignMap.put(LICENSE_VEHICLE_SUB, "");
        templateSignMap.put(LICENSE_VEHICLE_BACK, "");
        templateSignMap.put(LICENSE_REGIST, "dbd9be6f639423d32dc0c3e397aee68e");
        templateSignMap.put(LICENSE_INVOICE, "63e0a8a0f6f6d0582568f29e7bccb6c1");
        templateSignMap.put(LICENSE_DEDUCT, "63e0a8a0f6f6d0582568f29e7bccb6c1");
        templateSignMap.put(LICENSE_TAX, "63e0a8a0f6f6d0582568f29e7bccb6c1");
        templateSignMap.put(LICENSE_DUTY_PAID, "bb1b8e248299160bc946c3f27216ca9a");
        templateSignMap.put(LICENSE_NETCAR_FRONT, "08aca5e7dda52f52949e07823157d085");
        templateSignMap.put(LICENSE_NETCAR_BACK, "ba6413a2ba8b801beb521c5ac5fdb48b");
        templateSignMap.put(LICENSE_NETCAR_CERTIFICATE, "dd0557c6f976fba68effffc9fb025e0a");
        templateSignMap.put(LICENSE_NETCAR_CERTIFICATE2, "b9f06123908dffee0c67bc560412a096");
        templateSignMap.put(LICENSE_QUALITY, "6a4944216584e98620b8971ed891c0ff");
        templateSignMap.put(LICENSE_INSURE, "919d00f59a1ddc032a78636c9138b817");
        templateSignMap.put(LICENSE_INSURE_SIGN, "9d4afd3efb7488650429f76055cfda0a");
    }
    public static final ConcurrentHashMap<String, String> classifierIdMap = new ConcurrentHashMap<String, String>();
    static {
        classifierIdMap.put(LICENSE_VEHICLE_HOME, "d6eac95f85d150084ae7f2162d40b390");
        classifierIdMap.put(LICENSE_VEHICLE_SUB, "");
        classifierIdMap.put(LICENSE_VEHICLE_BACK, "");
        classifierIdMap.put(LICENSE_REGIST, "dbd9be6f639423d32dc0c3e397aee68e");
        classifierIdMap.put(LICENSE_INVOICE, "63e0a8a0f6f6d0582568f29e7bccb6c1");
        classifierIdMap.put(LICENSE_DEDUCT, "63e0a8a0f6f6d0582568f29e7bccb6c1");
        classifierIdMap.put(LICENSE_TAX, "63e0a8a0f6f6d0582568f29e7bccb6c1");
        classifierIdMap.put(LICENSE_DUTY_PAID, "bb1b8e248299160bc946c3f27216ca9a");
        classifierIdMap.put(LICENSE_NETCAR_FRONT, "1");
        classifierIdMap.put(LICENSE_NETCAR_BACK, "1");
        classifierIdMap.put(LICENSE_NETCAR_CERTIFICATE, "1");
        classifierIdMap.put(LICENSE_NETCAR_CERTIFICATE2, "1");
        classifierIdMap.put(LICENSE_QUALITY, "6a4944216584e98620b8971ed891c0ff");
        classifierIdMap.put(LICENSE_INSURE, "919d00f59a1ddc032a78636c9138b817");
        classifierIdMap.put(LICENSE_INSURE_SIGN, "9d4afd3efb7488650429f76055cfda0a");
    }
    //CCRM
    public static final String CCRM_VEHICLE = "1";
    public static final String CCRM_INVOICE = "2";
    public static final String CCRM_OPE_ADD = "1";
    public static final String CCRM_OPE_MODIFY = "2";
    public static final String CCRM_OPE_DELETE = "3";
    public static final String CCRM_SUCCESS = "Success";

    //系统操作用户
    public static final String CCRM_SYS = "CCRM";
    public static final String APP_SYS = "APP";
    public static final String WEB_SYS = "WEB";

    //操作模块
    public static final String OPE_LICENCE = "1"; //证照
    public static final String OPE_PROCESS = "2"; //流程
    public static final String OPE_IN_OUT = "3"; //出入库
    public static final String OPE_SYNC = "4"; //车辆信息

    //车辆相关操作
    public static final String VEHICLE_SYNC = "同步车辆";
    public static final String VEHICLE_DEL = "删除车辆";
    public static final String APP_OPE_OUT = "出库";
    public static final String APP_OPE_IN = "入库";
    public static final String APP_OPE_TRANSFER = "移交";
    public static final String VEHICLE_OPE_REVOKE_TRANSFER = "撤回移交";
    public static final String APP_OPE_COllECT = "收车";
    public static final String WEB_OPE_CARSELL = "卖车";

    //出入库状态
    public static final String APP_STATE_OUT = "出库";
    public static final String APP_STATE_IN = "在库";
    public static final String APP_STATE_TRANSFER = "移交中";
    public static final String VEHICLE_STATE_FINISH_TRANSFER = "已移交";
    public static final String VEHICLE_STATE_SELLED = "已卖出";
    public static final String VEHICLE_STATE_WAIT_IN = "待收车";

    //出入库操作角色
    public static final String AGENCY = "经销商";
    public static final String OPERATOR = "运营商";
    public static final String BYD = "BYD";

    //出入库趋势图
    public final static int DAY_HOURS = 24;

    //出入库操作状态对应
    public static final ConcurrentHashMap<String, String> appOpeStateMap = new ConcurrentHashMap<String, String>();

    static {
        appOpeStateMap.put(APP_OPE_OUT, APP_STATE_OUT);
        appOpeStateMap.put(APP_OPE_IN, APP_STATE_IN);
        appOpeStateMap.put(APP_OPE_TRANSFER, APP_STATE_TRANSFER);
        appOpeStateMap.put(APP_OPE_COllECT, APP_STATE_IN);
    }

    //经销商和运营商
    public static final Long AGENCY_ID = 105L; //经销商
    public static final Long OPERATOR_ID = 106L; //运营商
    // 资产经理
    public static final Long OPERASSETMAN_ID = 113L;
    public static final Long AGENCYASSETMAN_ID = 118L;
    //系统开发人员
    public static final Long DEVELOPMENT_ID = 103L;//开发人员

    //roleKey
    public static final String ADMIN_KEY = "admin";
    public static final String SUPERADMIN_KEY = "administrator";
    public static final String DEVELOPMENT_KEY = "development";
    public static final String OPERATOR_KEY = "operator";
    public static final String BYD_KEY = "byd";
    public static final String AGENCY_KEY = "agency";
    public static final String ASSETMANAGER_KEY = "assetManager";

    //盘点状态
    public static final String APP_INVENTORY_STATE_NO = "未盘点"; //未盘点
    public static final String APP_INVENTORY_STATE_NORMAL = "正常"; //正常
    public static final String APP_INVENTORY_STATE_ABNORMAL = "异常"; //异常
    public static final String APP_INVENTORY_STATE_FLAG_NO = "未盘点"; //未盘点
    public static final String APP_INVENTORY_STATE_FLAG_ALREADY = "已盘点"; //已盘点
    public static final String INVENTORY_STATE_FALG_CHECKING = "盘点中"; // 盘点进度 盘点中
    public static final String INVENTORY_STATE_FALG_FINISH = "盘点完毕"; // 盘点进度 盘点完成

    public static final ConcurrentHashMap<String, String> appInvStateMap = new ConcurrentHashMap<String, String>();

    static {
        appInvStateMap.put(APP_INVENTORY_STATE_NO, APP_INVENTORY_STATE_FLAG_NO);
        appInvStateMap.put(APP_INVENTORY_STATE_NORMAL, APP_INVENTORY_STATE_FLAG_ALREADY);
        appInvStateMap.put(APP_INVENTORY_STATE_ABNORMAL, APP_INVENTORY_STATE_FLAG_ALREADY);
    }

    //消息中心
    public static final int APP_MESSAGE_UNREAD = 0; //未读
    public static final int APP_MESSAGE_READ = 1; //已读

    //PROCESS_CENETR
    public final static int PROCESS_VERSION = 1;
    public final static String PROCESS_DEPLOY_PATH = "processDeploy/";
    public final static String PROCESS_PROJECT_URL = "http://icar.bydauto.com.cn/sc_web/";

    public final static String PROC_START_ACT_ID = "_2";                                //流程中心流程启动节点id
    public final static String PROC_END_ACT_ID = "_3";                                  //流程中心流程结案节点id
    public final static String PROC_APPLY_ACT_ID = "_4";                                //流程中心流程申请节点id
    public final static String PROC_MAX_ACT_ID = "_999";                                //流程中心流程已结案节点id
    public final static String PROC_CHECK_ACT_ID = "_5";  // 流程中心执行节点

    //证照变更操作
    public final static String PROC_LICOPE_BORROW = "证照借用";
    public final static String PROC_LICOPE_RENEW = "证照续借";
    public final static String PROC_LICOPE_RETURN = "证照归还";
    public final static String PROC_LICOPE_FILE = "证照归档";
    public final static String PROC_LICOPE_TRANSFER = "证照移交";

    //证照归档
    public final static String PROC_LICENSEARCH_TEMPLATE_URL = "downloadTemp/归档证照列表导入模板.xlsx";
    public final static String PROC_LICENSEARCH_KEY = "licenseArch";                    //证照归档流程定义id
    public final static String PROC_LICARCH_IMPORT_TYPE = "0";                          //证照归档证照列表上传类型
    public final static String PROC_LICARCH_LICADMIN_ACT_ID = "_5";                     //正照归档证照移交节点id
    public final static int PROC_LICARCH_ISROLLBACK = 1;                                //证照归档退回标识
    public final static int PROC_LICARCH_ISNOTROLLBACK = 0;                             //证照归档非退回标识
    //证照借用";
    public final static String PROC_LICENSELEND_KEY = "licenseLend";                    //证照借用流程定义id
    public final static String PROC_LICLEN_IMPORT_TYPE = "1";                           //证照借用证照列表上传类型
    public final static String PROC_LICLEN_LICMNG_ACT_ID = "_5";                        //证照借用证照经理审批节点id
    public final static String PROC_LICLEN_LICADMIN_ACT_ID = "_6";                      //证照借用证照移交节点id
    public final static String PROC_LICLEN_RECEIVER_ACT_ID = "_11";                     //证照借用证照签收节点id
    //证照归还
    public final static String PROC_LICENSERET_KEY = "licenseRet";                      //证照归还流程定义id
    public final static String PROC_LICRET_IMPORT_TYPE = "2";                           //证照归还证照列表上传类型
    public final static String PROC_LICRET_LICADMIN_ACT_ID = "_5";                      //证照归还证照管理员审批节点id
    //卖车
    public final static String PROC_CARSELL_KEY = "carSell";                            //卖车流程定义id
    public final static int PROC_CARSELL_ISROLLBACK = 1;                                //卖车流程退回标识
    public final static int PROC_CARSELL_ISNOTROLLBACK = 0;                             //卖车流程非退回标识
    public final static int PROC_CARSELL_NONEMONEY = 0;                                 //卖车流程无款发车标识
    public final static int PROC_CARSELL_MONEY = 1;                                     //卖车流程非无款发车标识
    public final static String PROC_CARSELL_COMAPPRO_ACT_ID = "_5";                     //卖车流程商务部审批节点id
    public final static String PROC_CARSELL_FIN_ACT_ID = "_7";                          //卖车流程财务部复审节点id
    public final static String PROC_CARSELL_COMMAND_ACT_ID = "_8";                      //卖车流程商务部发出指令节点id
    public final static String PROC_CARSELL_OUTAPPL_ACT_ID = "_11";                     //卖车流程运营商申请出库节点id
    public final static String PROC_CARSELL_ASSERTADMIN_ACT_ID = "_12";                 //卖车流程资产管理员审批节点id
    public final static String PROC_CARSELL_SUBMAT_ACT_ID = "_23";                      //卖车流程提交申请材料节点id
    public final static String PROC_CARSELL_LICMAN_ACT_ID = "_24";                      //卖车流程证照经理审批节点id
    public final static String PROC_CARSELL_LICADMIN_ACT_ID = "_25";                    //卖车流程证照移交节点id
    public final static String PROC_CARSELL_RECEIVER_ACT_ID = "_26";                    //卖车流程证照签收节点id
    //证照续借
    public final static String PROC_LICENSERENEW_KEY = "licenseRenew";                         //证照续借定义id
    public final static String PROC_RENEW_LICMANACT_ID = "_5";                          //证照续借证照经理审批节点id
    public final static String PROC_RENEW_LICADMIN_ACT_ID = "_6";                       //证照续借证照管理员审批节点id

    //保险
    public final static String INSURANCE_MANAGER= "保险管理员";//保险管理员用户角色名
    public final static String INSURANCE_TCI = "tci";//交强险前端
    public final static String INSURANCE_VCI = "vci";//商业险前端
    public final static String INSURANCE_CLI = "cli";//承运人责任险前端
    public static final String INSURANCE_TRAFFIC = "交强险";
    public static final String INSURANCE_VEHICLE = "商业险";
    public static final String INSURANCE_CARRIER = "承运人责任险";
    public final static String INSURANCE_MISS = "未导入";//保险未导入
    public final static String INSURANCE_INSERT = "已导入";//保险已导入
    public final static String INSURANCE_OVERDUE = "已过期";//保险过期

    //证照状态
    public final static String LICENSE_NOTEXIST="无";
    public final static String LICENSE_EXIST="有";
    public final static String LICENSE_OVERDUE="过期";
    public final static String LICENSE_NORMAL="正常";
    public final static String LICENSE_COMPLETE="齐全";
    public final static String LICENSE_LACK="缺失";
    public final static String NORETURNHAND = "未回库移交";
    public final static String RETURNHAND = "已回库移交";
    public final static String NORETURNBORROW = "未回库外借";
    public final static String RETURNBORROW = "已回库外借";
    public final static String NORETURN = "未回库";
    public final static String HASRETURN = "已回库";
    //public final static String HASSALE = "已卖出";

    //证件名称英文
    public static final String LICENSE_VEHICLE_HOME_EN = "licenceVehicle";
    public static final String LICENSE_VEHICLE_SUB_EN  = "licenceVehicleSub";
    public static final String LICENSE_VEHICLE_BACK_EN  = "licenceVehicleBack";
    public static final String LICENSE_REGIST_EN = "licenceRegist";
    public static final String LICENSE_INVOICE_EN = "licenceInvoice";
    public static final String LICENSE_DEDUCT_EN  = "licenceDeduct";
    public static final String LICENSE_DUTY_PAID_EN  = "licenceDutyPaid";
    public static final String LICENSE_NETCAR_FRONT_EN  = "licenceNetcar";
    public static final String LICENSE_NETCAR_BACK_EN  = "licenceNetcarCopy";
    public static final String LICENSE_QUALITY_EN  = "licenceQuality";
    public static final String LICENSE_INSURE_EN = "licenceInsurance";
    public static final String LICENSE_INSURE_SIGN_EN  = "licenceInsSign";
    public static final String LICENSE_TAX_EN  = "licenceTax";

    public static final ConcurrentHashMap<String, String> templateEnglishSignMap = new ConcurrentHashMap<String, String>();
    static {
        templateEnglishSignMap.put(LICENSE_VEHICLE_HOME_EN,LICENSE_VEHICLE_HOME );
        templateEnglishSignMap.put(LICENSE_VEHICLE_SUB_EN, LICENSE_VEHICLE_SUB);
        templateEnglishSignMap.put(LICENSE_VEHICLE_BACK_EN, LICENSE_VEHICLE_BACK);
        templateEnglishSignMap.put(LICENSE_REGIST_EN, LICENSE_REGIST);
        templateEnglishSignMap.put(LICENSE_INVOICE_EN, LICENSE_INVOICE);
        templateEnglishSignMap.put(LICENSE_DEDUCT_EN, LICENSE_DEDUCT);
        templateEnglishSignMap.put(LICENSE_TAX_EN, LICENSE_TAX);
        templateEnglishSignMap.put(LICENSE_DUTY_PAID_EN, LICENSE_DUTY_PAID);
        templateEnglishSignMap.put(LICENSE_NETCAR_FRONT_EN, LICENSE_NETCAR_FRONT);
        templateEnglishSignMap.put(LICENSE_NETCAR_BACK_EN, LICENSE_NETCAR_BACK);
        templateEnglishSignMap.put(LICENSE_QUALITY_EN, LICENSE_QUALITY);
        templateEnglishSignMap.put(LICENSE_INSURE_EN, LICENSE_INSURE);
        templateEnglishSignMap.put(LICENSE_INSURE_SIGN_EN, LICENSE_INSURE_SIGN);
    }



    //文件存放地址
    //证照完整路径：LICENCE_LOAD / vin / timestam / filename
    public static final String LICENCE_LOAD  = "supercloud/licence/";
    //流程完整路径：PROCESS_LOAD / processID / filename
    public static final String PROCESS_LOAD  = "supercloud/process/";
    //盘点APP图片完整路径：CHECK_LOAD / processID / vin / filename
    public static final String CHECK_LOAD  = "supercloud/check/";

    //电子围栏默认定位时间
    public static final String LOCATE_TIME = "17:00";
    //电子围栏默认最小里程
    public static final Integer MINI_MILEAGE = 200;

    //定时任务
    public static final long 定时任务_发票提醒_任务ID=1001;
    public static final long 定时任务_保险统计_任务ID=1002;
    public static final long 定时任务_监控预警_任务ID = 1003;
    public static final long 定时任务_证照回传_任务ID=1004;
    public static final long 定时任务_证照提醒_任务ID=1005;

    // 资产盘点
    public final static String PROC_PROCESSCHECK_KEY = "processCheck";

    //批量执行请求VIN数量
    public final static Integer BATCH_REQUEST_SIZE = 800;
    public final static String CITIES = "[\"北京市\",\"上海市\",\"广州市\",\"深圳市\",\"南京市\",\"杭州市\",\"天津市\",\"重庆市\",\"成都市\",\"青岛市\",\"苏州市\",\"无锡市\",\"常州市\",\"温州市\",\"武汉市\",\"长沙市\",\"石家庄市\",\"南昌市\",\"三亚市\",\"合肥市\",\"郑州市\",\"保定市\",\"唐山市\",\"秦皇岛市\",\"邯郸市\",\"邢台市\",\"张家口市\",\"承德市\",\"衡水市\",\"廊坊市\",\"沧州市\",\"太原市\",\"大同市\",\"阳泉市\",\"长治市\",\"晋城市\",\"朔州市\",\"晋中市\",\"运城市\",\"忻州市\",\"临汾市\",\"吕梁市\",\"呼和浩特市\",\"包头市\",\"乌海市\",\"赤峰市\",\"通辽市\",\"鄂尔多斯市\",\"呼伦贝尔市\",\"巴彦淖尔市\",\"乌兰察布市\",\"兴安盟\",\"锡林郭勒盟\",\"阿拉善盟\",\"沈阳市\",\"大连市\",\"鞍山市\",\"抚顺市\",\"本溪市\",\"丹东市\",\"锦州市\",\"营口市\",\"阜新市\",\"辽阳市\",\"盘锦市\",\"铁岭市\",\"朝阳市\",\"葫芦岛市\",\"长春市\",\"吉林市\",\"四平市\",\"辽源市\",\"通化市\",\"白山市\",\"松原市\",\"白城市\",\"延边朝鲜族自治州\",\"哈尔滨市\",\"齐齐哈尔市\",\"鸡西市\",\"鹤岗市\",\"双鸭山市\",\"大庆市\",\"伊春市\",\"佳木斯市\",\"七台河市\",\"牡丹江市\",\"黑河市\",\"绥化市\",\"大兴安岭地区\",\"徐州市\",\"南通市\",\"连云港市\",\"淮安市\",\"盐城市\",\"扬州市\",\"镇江市\",\"泰州市\",\"宿迁市\",\"宁波市\",\"嘉兴市\",\"湖州市\",\"绍兴市\",\"舟山市\",\"衢州市\",\"金华市\",\"台州市\",\"丽水市\",\"芜湖市\",\"蚌埠市\",\"淮南市\",\"马鞍山市\",\"淮北市\",\"铜陵市\",\"安庆市\",\"黄山市\",\"滁州市\",\"阜阳市\",\"宿州市\",\"巢湖市\",\"六安市\",\"亳州市\",\"池州市\",\"宣城市\",\"福州市\",\"厦门市\",\"莆田市\",\"三明市\",\"泉州市\",\"漳州市\",\"南平市\",\"龙岩市\",\"宁德市\",\"景德镇市\",\"萍乡市\",\"九江市\",\"新余市\",\"鹰潭市\",\"赣州市\",\"吉安市\",\"宜春市\",\"抚州市\",\"上饶市\",\"济南市\",\"淄博市\",\"枣庄市\",\"东营市\",\"烟台市\",\"潍坊市\",\"济宁市\",\"泰安市\",\"威海市\",\"日照市\",\"莱芜市\",\"临沂市\",\"德州市\",\"聊城市\",\"滨州市\",\"菏泽市\",\"开封市\",\"洛阳市\",\"平顶山市\",\"安阳市\",\"鹤壁市\",\"新乡市\",\"焦作市\",\"濮阳市\",\"许昌市\",\"漯河市\",\"三门峡市\",\"南阳市\",\"商丘市\",\"信阳市\",\"周口市\",\"驻马店市\",\"济源市\",\"黄石市\",\"十堰市\",\"宜昌市\",\"襄樊市\",\"鄂州市\",\"荆门市\",\"孝感市\",\"荆州市\",\"黄冈市\",\"咸宁市\",\"随州市\",\"恩施土家族苗族自治州\",\"仙桃市\",\"潜江市\",\"天门市\",\"神农架林区\",\"株洲市\",\"湘潭市\",\"衡阳市\",\"邵阳市\",\"岳阳市\",\"常德市\",\"张家界市\",\"益阳市\",\"郴州市\",\"永州市\",\"怀化市\",\"娄底市\",\"湘西土家族苗族自治州\",\"韶关市\",\"珠海市\",\"汕头市\",\"佛山市\",\"江门市\",\"湛江市\",\"茂名市\",\"肇庆市\",\"惠州市\",\"梅州市\",\"汕尾市\",\"河源市\",\"阳江市\",\"清远市\",\"东莞市\",\"中山市\",\"潮州市\",\"揭阳市\",\"云浮市\",\"南宁市\",\"柳州市\",\"桂林市\",\"梧州市\",\"北海市\",\"防城港市\",\"钦州市\",\"贵港市\",\"玉林市\",\"百色市\",\"贺州市\",\"河池市\",\"来宾市\",\"崇左市\",\"海口市\",\"三亚市\",\"五指山市\",\"琼海市\",\"儋州市\",\"文昌市\",\"万宁市\",\"东方市\",\"定安县\",\"屯昌县\",\"澄迈县\",\"临高县\",\"白沙黎族自治县\",\"昌江黎族自治县\",\"乐东黎族自治县\",\"陵水黎族自治县\",\"保亭黎族苗族自治县\",\"琼中黎族苗族自治县\",\"西沙群岛\",\"南沙群岛\",\"中沙群岛的岛礁及其海域\",\"自贡市\",\"攀枝花市\",\"泸州市\",\"德阳市\",\"绵阳市\",\"广元市\",\"遂宁市\",\"内江市\",\"乐山市\",\"南充市\",\"眉山市\",\"宜宾市\",\"广安市\",\"达州市\",\"雅安市\",\"巴中市\",\"资阳市\",\"阿坝藏族羌族自治州\",\"甘孜藏族自治州\",\"凉山彝族自治州\",\"贵阳市\",\"六盘水市\",\"遵义市\",\"安顺市\",\"铜仁地区\",\"黔西南布依族苗族自治州\",\"毕节地区\",\"黔东南苗族侗族自治州\",\"黔南布依族苗族自治州\",\"昆明市\",\"曲靖市\",\"玉溪市\",\"保山市\",\"昭通市\",\"丽江市\",\"思茅市\",\"临沧市\",\"楚雄彝族自治州\",\"红河哈尼族彝族自治州\",\"文山壮族苗族自治州\",\"西双版纳傣族自治州\",\"大理白族自治州\",\"德宏傣族景颇族自治州\",\"怒江傈僳族自治州\",\"迪庆藏族自治州\",\"拉萨市\",\"昌都地区\",\"山南地区\",\"日喀则地区\",\"那曲地区\",\"阿里地区\",\"林芝地区\",\"西安市\",\"铜川市\",\"宝鸡市\",\"咸阳市\",\"渭南市\",\"延安市\",\"汉中市\",\"榆林市\",\"安康市\",\"商洛市\",\"兰州市\",\"嘉峪关市\",\"金昌市\",\"白银市\",\"天水市\",\"武威市\",\"张掖市\",\"平凉市\",\"酒泉市\",\"庆阳市\",\"定西市\",\"陇南市\",\"临夏回族自治州\",\"甘南藏族自治州\",\"西宁市\",\"海东地区\",\"海北藏族自治州\",\"黄南藏族自治州\",\"海南藏族自治州\",\"果洛藏族自治州\",\"玉树藏族自治州\",\"海西蒙古族藏族自治州\",\"银川市\",\"石嘴山市\",\"吴忠市\",\"固原市\",\"中卫市\",\"乌鲁木齐市\",\"克拉玛依市\",\"吐鲁番地区\",\"哈密地区\",\"昌吉回族自治州\",\"博尔塔拉蒙古自治州\",\"巴音郭楞蒙古自治州\",\"阿克苏地区\",\"克孜勒苏柯尔克孜自治州\",\"喀什地区\",\"和田地区\",\"伊犁哈萨克自治州\",\"塔城地区\",\"阿勒泰地区\",\"石河子市\",\"阿拉尔市\",\"图木舒克市\",\"五家渠市\",\"台北市\",\"高雄市\",\"基隆市\",\"台中市\",\"台南市\",\"新竹市\",\"嘉义市\",\"台北县\",\"宜兰县\",\"桃园县\",\"新竹县\",\"苗栗县\",\"台中县\",\"彰化县\",\"南投县\",\"云林县\",\"嘉义县\",\"台南县\",\"高雄县\",\"屏东县\",\"澎湖县\",\"台东县\",\"花莲县\",\"中西区\",\"东区\",\"九龙城区\",\"观塘区\",\"南区\",\"深水埗区\",\"黄大仙区\",\"湾仔区\",\"油尖旺区\",\"离岛区\",\"葵青区\",\"北区\",\"西贡区\",\"沙田区\",\"屯门区\",\"大埔区\",\"荃湾区\",\"元朗区\",\"花地玛堂区\",\"圣安多尼堂区\",\"大堂区\",\"望德堂区\",\"风顺堂区\",\"嘉模堂区\",\"圣方济各堂区\"]";
    //云服务正常返回状态码
    public final static String HTTP_CODE_SUCCESS = "1000";
    //云服务请求频繁异常码
    public final static String HTTP_CODE_FREQUENT = "X9991";
    //云服务GPS接口成功返回集合名称
    public final static String HTTP_GPS_SUCCESS_LIST = "gpsSuccessInfoList";
    //云服务Mileage接口成功返回集合名称
    public final static String HTTP_MILEAGE_SUCCESS_LIST = "mileageSuccessInfoList";
    public final static String PROANDCIT= "{\"city\": {\"福建省\": [\"福州市\",\"厦门市\",\"莆田市\",\"三明市\",\"泉州市\",\"漳州市\",\"南平市\",\"龙岩市\",\"宁德市\"],\"西藏自治区\": [\"拉萨市\",\"昌都地区\",\"山南地区\",\"日喀则地区\",\"那曲地区\",\"阿里地区\",\"林芝地区\"],\"贵州省\": [\"贵阳市\",\"六盘水市\",\"遵义市\",\"安顺市\",\"铜仁地区\",\"黔西南布依族苗族自治州\",\"毕节地区\",\"黔东南苗族侗族自治州\",\"黔南布依族苗族自治州\"],\"湖北省\": [\"武汉市\",\"黄石市\",\"十堰市\",\"宜昌市\",\"襄樊市\",\"鄂州市\",\"荆门市\",\"孝感市\",\"荆州市\",\"黄冈市\",\"咸宁市\",\"随州市\",\"恩施土家族苗族自治州\",\"仙桃市\",\"潜江市\",\"天门市\",\"神农架林区\"],\"湖南省\":" +
            " [\"长沙市\",\"株洲市\",\"湘潭市\",\"衡阳市\",\"邵阳市\",\"岳阳市\",\"常德市\",\"张家界市\",\"益阳市\",\"郴州市\",\"永州市\",\"怀化市\",\"娄底市\",\"湘西土家族苗族自治州\"],\"广东省\": [\"广州市\",\"韶关市\",\"深圳市\",\"珠海市\",\"汕头市\",\"佛山市\",\"江门市\",\"湛江市\",\"茂名市\",\"肇庆市\",\"惠州市\",\"梅州市\",\"汕尾市\",\"河源市\",\"阳江市\",\"清远市\",\"东莞市\",\"中山市\",\"潮州市\",\"揭阳市\",\"云浮市\"],\"重庆\": [\"重庆市\"],\"澳门特别行政区\": [\"澳门特别行政区\"],\"香港特别行政区\":" +
            " [\"中西区\",\"东区\",\"九龙城区\",\"观塘区\",\"南区\",\"深水埗区\",\"黄大仙区\",\"湾仔区\",\"油尖旺区\",\"离岛区\",\"葵青区\",\"北区\",\"西贡区\",\"沙田区\",\"屯门区\",\"大埔区\",\"荃湾区\",\"元朗区\"],\"安徽省\": [\"合肥市\",\"芜湖市\",\"蚌埠市\",\"淮南市\",\"马鞍山市\",\"淮北市\",\"铜陵市\",\"安庆市\",\"黄山市\",\"滁州市\",\"阜阳市\",\"宿州市\",\"巢湖市\",\"六安市\",\"亳州市\",\"池州市\",\"宣城市\"],\"四川省\": [\"成都市\",\"自贡市\",\"攀枝花市\",\"泸州市\",\"德阳市\",\"绵阳市\",\"广元市\",\"遂宁市\",\"内江市\",\"乐山市\",\"南充市\",\"眉山市\",\"宜宾市\",\"广安市\",\"达州市\",\"雅安市\",\"巴中市\",\"资阳市\",\"阿坝藏族羌族自治州\",\"甘孜藏族自治州\",\"凉山彝族自治州\"],\"新疆维吾尔自治区\":" +
            " [\"乌鲁木齐市\",\"克拉玛依市\",\"吐鲁番地区\",\"哈密地区\",\"昌吉回族自治州\",\"博尔塔拉蒙古自治州\",\"巴音郭楞蒙古自治州\",\"阿克苏地区\",\"克孜勒苏柯尔克孜自治州\",\"喀什地区\",\"和田地区\",\"伊犁哈萨克自治州\",\"塔城地区\",\"阿勒泰地区\",\"石河子市\",\"阿拉尔市\",\"图木舒克市\",\"五家渠市\"],\"江苏省\": [\"南京市\",\"无锡市\",\"徐州市\",\"常州市\",\"苏州市\",\"南通市\",\"连云港市\",\"淮安市\",\"盐城市\",\"扬州市\",\"镇江市\",\"泰州市\",\"宿迁市\"],\"天津\": [\"天津市\"],\"吉林省\":" +
            " [\"长春市\",\"吉林市\",\"四平市\",\"辽源市\",\"通化市\",\"白山市\",\"松原市\",\"白城市\",\"延边朝鲜族自治州\"],\"宁夏回族自治区\": [\"银川市\",\"石嘴山市\",\"吴忠市\",\"固原市\",\"中卫市\"],\"河北省\": [\"石家庄市\",\"唐山市\",\"秦皇岛市\",\"邯郸市\",\"邢台市\",\"保定市\",\"张家口市\",\"承德市\",\"衡水市\",\"廊坊市\",\"沧州市\"],\"河南省\": [\"郑州市\",\"开封市\",\"洛阳市\",\"平顶山市\",\"安阳市\",\"鹤壁市\",\"新乡市\",\"焦作市\",\"濮阳市\",\"许昌市\",\"漯河市\",\"三门峡市\",\"南阳市\",\"商丘市\",\"信阳市\",\"周口市\",\"驻马店市\",\"济源市\"],\"广西壮族自治区\":" +
            " [\"南宁市\",\"柳州市\",\"桂林市\",\"梧州市\",\"北海市\",\"防城港市\",\"钦州市\",\"贵港市\",\"玉林市\",\"百色市\",\"贺州市\",\"河池市\",\"来宾市\",\"崇左市\"],\"上海\": [\"上海市\"],\"海南省\": [\"海口市\",\"三亚市\",\"五指山市\",\"琼海市\",\"儋州市\",\"文昌市\",\"万宁市\",\"东方市\",\"定安县\",\"屯昌县\",\"澄迈县\",\"临高县\",\"白沙黎族自治县\",\"昌江黎族自治县\",\"乐东黎族自治县\",\"陵水黎族自治县\",\"保亭黎族苗族自治县\",\"琼中黎族苗族自治县\",\"西沙群岛\",\"南沙群岛\",\"中沙群岛的岛礁及其海域\"],\"江西省\":" +
            " [\"南昌市\",\"景德镇市\",\"萍乡市\",\"九江市\",\"新余市\",\"鹰潭市\",\"赣州市\",\"吉安市\",\"宜春市\",\"抚州市\",\"上饶市\"],\"云南省\": [\"昆明市\",\"曲靖市\",\"玉溪市\",\"保山市\",\"昭通市\",\"丽江市\",\"思茅市\",\"临沧市\",\"楚雄彝族自治州\",\"红河哈尼族彝族自治州\",\"文山壮族苗族自治州\",\"西双版纳傣族自治州\",\"大理白族自治州\",\"德宏傣族景颇族自治州\",\"怒江傈僳族自治州\",\"迪庆藏族自治州\"],\"甘肃省\": [\"兰州市\",\"嘉峪关市\",\"金昌市\",\"白银市\",\"天水市\",\"武威市\",\"张掖市\",\"平凉市\",\"酒泉市\",\"庆阳市\",\"定西市\",\"陇南市\",\"临夏回族自治州\",\"甘南藏族自治州\"],\"山东省\":" +
            " [\"济南市\",\"青岛市\",\"淄博市\",\"枣庄市\",\"东营市\",\"烟台市\",\"潍坊市\",\"济宁市\",\"泰安市\",\"威海市\",\"日照市\",\"莱芜市\",\"临沂市\",\"德州市\",\"聊城市\",\"滨州市\",\"菏泽市\"],\"陕西省\": [\"西安市\",\"铜川市\",\"宝鸡市\",\"咸阳市\",\"渭南市\",\"延安市\",\"汉中市\",\"榆林市\",\"安康市\",\"商洛市\"],\"海外\": [\"海外\"],\"浙江省\": [\"杭州市\",\"宁波市\",\"温州市\",\"嘉兴市\",\"湖州市\",\"绍兴市\",\"舟山市\",\"衢州市\",\"金华市\",\"台州市\",\"丽水市\"],\"内蒙古自治区\":" +
            " [\"呼和浩特市\",\"包头市\",\"乌海市\",\"赤峰市\",\"通辽市\",\"鄂尔多斯市\",\"呼伦贝尔市\",\"巴彦淖尔市\",\"乌兰察布市\",\"兴安盟\",\"锡林郭勒盟\",\"阿拉善盟\"],\"青海省\": [\"西宁市\",\"海东地区\",\"海北藏族自治州\",\"黄南藏族自治州\",\"海南藏族自治州\",\"果洛藏族自治州\",\"玉树藏族自治州\",\"海西蒙古族藏族自治州\"],\"辽宁省\": [\"沈阳市\",\"大连市\",\"鞍山市\",\"抚顺市\",\"本溪市\",\"丹东市\",\"锦州市\",\"营口市\",\"阜新市\",\"辽阳市\",\"盘锦市\",\"铁岭市\",\"朝阳市\",\"葫芦岛市\"],\"台湾省\":" +
            " [\"台北市\",\"高雄市\",\"基隆市\",\"台中市\",\"台南市\",\"新竹市\",\"嘉义市\",\"台北县\",\"宜兰县\",\"桃园县\",\"新竹县\",\"苗栗县\",\"台中县\",\"彰化县\",\"南投县\",\"云林县\",\"嘉义县\",\"台南县\",\"高雄县\",\"屏东县\",\"澎湖县\",\"台东县\",\"花莲县\"],\"黑龙江省\": [\"哈尔滨市\",\"齐齐哈尔市\",\"鸡西市\",\"鹤岗市\",\"双鸭山市\",\"大庆市\",\"伊春市\",\"佳木斯市\",\"七台河市\",\"牡丹江市\",\"黑河市\",\"绥化市\",\"大兴安岭地区\"],\"山西省\": [\"太原市\",\"大同市\",\"阳泉市\",\"长治市\",\"晋城市\",\"朔州市\",\"晋中市\",\"运城市\",\"忻州市\",\"临汾市\",\"吕梁市\"],\"北京\":" +
            " [\"北京市\"]}}";
}
