package com.leap.latte.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessService extends android.accessibilityservice.AccessibilityService {

    private String TAG = "AccessService";
    private static AccessService mService;

    private final int CONTACT_LIST = 0;         // 聊天列表
    private final int CHAT_LIST = 1;            // 聊天页面
    private final int CONFIRM_MONEY = 2;        // 确认收款
    private final int ALREADY_COLLECTED = 3;     // 已收款
    private final int RECEIVED_POCKET = 4;     // 已收红包


    private int CURRENT = 0;


    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(getApplicationContext(), "辅助功能开启", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "无障碍服务开启");
        mService = this;
    }

    public AccessService() {
    }

    /**
     * 每当有窗口活动时，就会触发 onAccessibilityEvent() 方法
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                Log.d(TAG,"notification change");
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                Log.d(TAG,"TYPE_WINDOW_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                break;
        }
        setCurrent(event);

        switch (CURRENT) {
            case CONTACT_LIST:
                break;
            case CHAT_LIST:
                clickOpenRedPocketAndReturnToChat();
                break;
            case CONFIRM_MONEY:
                break;
            case ALREADY_COLLECTED:
                break;
            case RECEIVED_POCKET:
                fronReceivedPocketToChat(event);
                break;
        }


    }

    private void clickOpenRedPocketAndReturnToChat() {
        boolean haveNotReceivedPocket = false;
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            return;
        }
        //   红包聊天记录节点
        List<AccessibilityNodeInfo> pocketNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/aui");
        for (int i = 0; i < pocketNodes.size(); i++) {
            //  如果这个节点没有 已领取  点击这个红包
            if (pocketNodes.get(i).getParent() != null) {
                if (!pocketNodes.get(i).getParent().getChild(1).getText().toString().contains("已领取")) {
                    haveNotReceivedPocket = true;
                    clickView(pocketNodes.get(i));
                }
            }
        }
        //   红包的  开
        List<AccessibilityNodeInfo> openNodeList = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/da7");
        for (AccessibilityNodeInfo openNode : openNodeList) {
            clickView(openNode);
        }

        //   如果没有可领取红包，退出聊天界面
        if (!haveNotReceivedPocket) {
            List<AccessibilityNodeInfo> backMainList = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/lr");
            for (AccessibilityNodeInfo backMainNode:backMainList){
                if (clickView(backMainNode)){
                    break;
                }
            }
        }
    }


    private void fronReceivedPocketToChat(AccessibilityEvent event) {
//        this.performGlobalAction(GLOBAL_ACTION_BACK);//类似于点击导航上的返回按钮
        AccessibilityNodeInfo rootNodes = getRootInActiveWindow();
        if (rootNodes == null) {
            return;
        }
        List<AccessibilityNodeInfo> backNodeList = rootNodes.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/m0");
        for (int i = 0; i < backNodeList.size(); i++) {
            clickView(backNodeList.get(i));
        }
    }



    /**
     * 点击nodeInfo
     */
    public static boolean clickView(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null) {
            if (nodeInfo.isClickable()) {
                // 发送点击事件
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                return true;
            } else {
                AccessibilityNodeInfo parent = nodeInfo.getParent();
                if (parent != null) {
                    boolean b = clickView(parent);
                    parent.recycle();
                    if (b) return true;
                }
            }
        }
        return false;
    }

    public static boolean isStart() {
        return mService != null;
    }

    /**
     * 判断当前在哪个页面
     * @param event
     */
    private void setCurrent(AccessibilityEvent event) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            return;
        }
        // 聊天界面特有node
        List<AccessibilityNodeInfo> aqeList = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/aqe");
        if (aqeList.size() > 0) {
            CURRENT = CHAT_LIST;
        }
        // 联系人聊天列表特有node
        List<AccessibilityNodeInfo> raList = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ra");
        if (raList.size() > 0) {
            CURRENT = CONTACT_LIST;
        }
        // 待确认收款界面
        List<AccessibilityNodeInfo> efmList = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/efm");
        if (efmList.size() > 0) {
            CURRENT = CONFIRM_MONEY;
        }
        //  已收款页面
        List<AccessibilityNodeInfo> eepList = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/eep");
        if (eepList.size() > 0) {
            CURRENT = ALREADY_COLLECTED;
        }

        //  已收红包页面
        List<AccessibilityNodeInfo> d6dList = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/d6d");
        if (d6dList.size() > 0) {
            CURRENT = RECEIVED_POCKET;
        }
    }



    @Override
    public void onInterrupt() {
        Log.d(TAG, "无障碍服务被中断");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "服务被终止");
        Toast.makeText(getApplicationContext(), "辅助功能被停止", Toast.LENGTH_SHORT).show();
    }
}
