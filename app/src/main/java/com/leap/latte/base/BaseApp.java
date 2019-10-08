package com.leap.latte.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.leap.common_lib.crash.UnexitCrash;
import com.leap.common_lib.thread.ThreadPoolManager;
import com.leap.common_lib.util.DeviceUtil;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class BaseApp extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//        crashIntercept();
        QMUISwipeBackActivityManager.init(this);
    }

    private void crashIntercept() {
        UnexitCrash.install(new UnexitCrash.ExceptionHandler() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                ThreadPoolManager.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                //开发时使用Cockroach可能不容易发现bug，所以建议开发阶段在handlerException中用Toast谈个提示框，
                //由于handlerException可能运行在非ui线程中，Toast又需要在主线程，所以new了一个new Handler(Looper.getMainLooper())，
                //所以千万不要在下面的run方法中执行耗时操作，因为run已经运行在了ui线程中。
                //new Handler(Looper.getMainLooper())只是为了能弹出个toast，并无其他用途
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {

                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });
    }

    public static class Config {
        /**
         * 支持文件目录
         */
        public static boolean isSendingMessage = false;
        /**
         * SD卡路径
         */
//		public static final String SDCARD_PATH = getRootDataPath(mContext);
//        public static final String SDCARD_PATH = getSystemSDCardsDataPath(mContext);
        public static String SDCARD_PATH = getSystemDataPath(mContext);
        public static final String SYS_DCIM = getSysDCIMPath(); // 系统相册路径
        public static String DATA_SUPPORT_ROOT = SDCARD_PATH + "/zc_data_support/";
        //		public static final String ITM_SUPPORT_ROOT = SDCARD_PATH + "/itm_data_support/";
        public static final String PATH = DATA_SUPPORT_ROOT + "config.xml";
        public static final String HELP_PATH = DATA_SUPPORT_ROOT + "help.xml";
        public static final String DOWNLOAD_PATH = DATA_SUPPORT_ROOT + "download";

        public static final String TEMPPATH = DATA_SUPPORT_ROOT + "zcsoft" + File.separator + "temp";
        public static final String LOGPATH = DATA_SUPPORT_ROOT + "log" + File.separator;

        /*** 大小类信息文件路径 */
        public static final String COMPONENT_FILE_PATH = "componentFilePath";
        /*** 地图工作空间路径 */
        public static final String MAP_DATA_PATH = "mapDataPath";
        /**
         * 包含网络数据集的数据源名称
         */
        public static final String DATASOURCE_NAME = "datasource_name";
        /**
         * 网络数据集名称
         */
        public static final String NETWORKDATASET_NAME = "networkdataset_name";
        /**
         * 点到弧段的距离容限
         */
        public static final String TOLERANCE = "tolerance";
        /**
         * 网格图层名称
         **/
        public static final String GRID_LAYER_NAME = "grid_layer_name";
        /**
         * 水印文字
         **/
        public static final String WATER_MARK = "water_mark";
        /*** 数据库文件名路径 */
        public static final String DB_PATH = "dbPath";
        /*** 部件网格数据库文件名路径 */
        public static final String UDB_PATH = "db_grid_part";
        /*** 音频文件路径 */
        public static final String VIDEO_DATA_PATH = "videoDataPath";
        /*** 图片文件路径 */
        public static final String PHOTO_PATH = "photoPath";
        /**
         * 可显示图层名称
         */
        public static final String DISPLAY_LAYERS = "displayLayers";
        // 门前三包
        public static final String DOOR_THREE_KIT = "door_three_kit";
        // 门前五包
        public static final String DOOR_FIVE_KIT = "door_five_kit";
        // 地图的名称
        public static final String MAP_NAME = "map_name";
        /** GPS设置 */
        /**
         * GPS配准点
         */
        public static final String POINT_CONTROL = "pointControl";
        /**
         * 百度配准点
         */
        public static final String BD_POINT_CONTROL = "bdPointControl";
        public static final String BD_2_GPS_POINT_CONTROL = "bd2GpsPointControl";
        public static final String GPS_SETTING = "gpsSetting";
        public static final String MY_POSION_UPLOAD = "myPositionUpload";
        public static final String GPS_ENABLE = "enable";
        public static final String GPS_RANGE = "range";
        public static final String GPS_TIME = "time";
        public static final String TEMPLATE_TD = "templateId";
        /**
         * 呼叫号码
         */
        public static final String CALL_PHONE = "call_phone";
        public static final String SERVICE_PHONE = "service_phone";
        /**
         * supermap objects for android 许可
         */
        public static final String SUPERMAP_DIR = "zc_dir";

        /**
         * 当前APK使用地图类型 分三类：imobile、baidu、html5
         */
        public static final String MAP_TYPE = "mapType";
        /**
         * 使用在线地图还是离线地图 分两类：online、local;只有在mapType为imobile时才考虑该字段，否则使用在线方式
         */
        public static final String MAPFLAG = "mapflag";
        /**
         * 失败案卷消息队列
         */
        public static Queue<String> failTaskQueue = new LinkedList<String>();

        /**
         * 选用百度地图
         */
        public static final String GPS_WHETHERUSEBAIDUMAP = "whetherUseBaiDuMap";
        /**
         * 应用程序的appName
         */
        public static String softAppName = "城管通";
        /**
         * 应用程序的appName
         */
        public static String itmAppName = "信息科技平台";
        /**
         * 百度定位模块是否成功加载
         */
        public static boolean ISBAIDULOAD = true;
        public static final String REN_FILE = "renfile";
        public static final String HU_FILE = "hufile";

        /**
         * FTP的连接模式 的设置
         */
        public static final String FTP_TRAN_MODEL = "ftpTranModel";

        /**
         * 登录人的名称
         */
        public static String LOGINER_NAME = "";
        /**
         * 登录人账号
         */
        public static String LOGINER_CODE = "";
        /**
         * 登录人手机号
         */
        public static String LOGINER_PHONE = "";
        /**
         * 登录人网格号
         */
        public static String LOGING_BG_CODE = "";
        /**
         * 登录人角色信息
         */
        public static String LOGIN_ROLE_INFO = "";
        /**
         * 软件标题的名称
         * 2017/5/25 by zhh
         */
        public static String APP_NAME = "";
        /**
         * 登陆人所属分组部门id
         * 2017/5/25 by zhh
         */
        public static String ENTITY_GROUP = "";
        /**
         * 标志新旧版本的compontent.xml
         */
        public static String COMPONENT_OLD = "comOld";
        /**
         * APK的类型CGT-SGT:0,CZT:1,LDT-DDT:2
         */
        public static int APP_TYPE_VALUE = 0;

        /**
         * <p>
         * 获取Android系统相册路径
         * </p>
         *
         * @return
         * @creation 2013-4-24
         * @author 烜
         */
        private static String getSysDCIMPath() {
            String dcimFolder = "/sdcard/DCIM/Camera";
            String[] mayOption = new String[]{"/sdcard/DCIM/Camera", "/sdcard0/DCIM/Camera", "/sdcard1/DCIM/Camera",
                    SDCARD_PATH + "/DCIM/Camera"};
            File folder = null;
            for (int i = 0; i < mayOption.length; i++) {
                folder = new File(mayOption[i]);
                if (folder.exists() && folder.isDirectory())
                    dcimFolder = mayOption[i];
            }
            return dcimFolder;
        }

        /**
         * <p>
         * 获取城管通主文件夹cgt_data_support
         * </p>
         *
         * @return
         * @creation 2013-4-24
         * @author 烜
         */
        private static String getRootDataPath(Context context) {

            String[] list = DeviceUtil.getVolumePaths(context);
            File f = null;
            if (null == list) {
                return null;
            }
            for (int i = 0; i < list.length; i++) {
                String path = list[i] + "/zc_data_support/";
                f = new File(path);
                if (f.exists()) {
                    return list[i];
                }
            }
            // 都没有返回空
            return null;

        }

        /**
         * <p>
         * 获取城管通系统文件夹目录
         * </p>
         *
         * @param context
         * @return
         */
        public static String getSystemDataPath(Context context) {
            try {
                String d = context.getExternalFilesDir(null).getPath();
//			String d =context.getFilesDir().getPath();
                String[] list = {d};
                if (null == list) {
                    return null;
                }
                for (int i = 0; i < list.length; i++) {
                    String path = list[i] + "/zc_data_support/";
                    File f  = new File(path);
                    if (f.exists()) {
                        return list[i];
                    } else {
                        f.mkdir();
                        return list[i];
                    }
                }

            } catch (Exception e) {
            }
            // 都没有返回空
            return null;

        }

        /**
         * <p>
         * 获取城管通系统文件夹目录
         * </p>
         *
         * @param context
         * @return
         */
        private static String getSystemSDCardsDataPath(Context context) {
            try {
                String[] list = DeviceUtil.getVolumePaths(context);
                File f = null;
                if (null == list) {
                    return null;
                }
                for (int i = 0; i < list.length; i++) {
                    String path = list[i] + "/zc_data_support/";
                    f = new File(path);
                    if (f.exists()) {
                        return list[i];
                    }
                }
            } catch (Exception e) {
            }
            // 都没有返回空
            return null;

        }

        /**
         * <p>
         * 获取特定主文件夹路径
         * </p>
         *
         * @return
         * @author mw
         */
        private static String getSpecificRootDataPath(String dirName) {
            try {
                Runtime runtime = Runtime.getRuntime();
                Process proc = runtime.exec("mount");
                InputStream is = proc.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                String line;
                String mount = new String();
                File f = null;
                BufferedReader br = new BufferedReader(isr);
                while ((line = br.readLine()) != null) {
                    if (line.contains("secure"))
                        continue;
                    if (line.contains("asec"))
                        continue;

                    if (line.contains("fat")) {
                        String columns[] = line.split(" ");
                        if (columns != null && columns.length > 1) {
                            for (int i = 1; i < columns.length; i++) {
                                if (columns[i].contains("/")) {
                                    mount = columns[i];
                                    break;
                                }
                            }
                        }
                    } else if (line.contains("fuse")) {
                        String columns[] = line.split(" ");
                        if (columns != null && columns.length > 1) {
                            for (int i = 1; i < columns.length; i++) {
                                if (columns[i].contains("/")) {
                                    mount = columns[i];
                                    break;
                                }
                            }
                        }
                    } else if (line.contains("data/media")) {
                        String columns[] = line.split(" ");
                        if (columns != null && columns.length > 1) {
                            for (int i = 1; i < columns.length; i++) {
                                if (columns[i].contains("storage")) {
                                    mount = columns[i];
                                    break;
                                }
                            }
                        }
                    } else if (line.contains("data/share")) {
                        String columns[] = line.split(" ");
                        if (columns != null && columns.length > 1) {
                            for (int i = 1; i < columns.length; i++) {
                                if (columns[i].contains("storage")) {
                                    mount = columns[i];
                                    break;
                                }
                            }
                        }
                    } else if (line.contains("dev/fuse")) {
                        String columns[] = line.split(" ");
                        if (columns != null && columns.length > 1) {
                            for (int i = 1; i < columns.length; i++) {
                                if (columns[i].contains("storage")) {
                                    mount = columns[i];
                                    break;
                                }
                            }
                        }
                    }
                    f = new File(mount + "/dirName/");
                    if (f.exists()) {
                        return mount;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
    }
}
