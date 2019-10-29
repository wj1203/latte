package com.leap.latte.accessibility;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessService extends android.accessibilityservice.AccessibilityService {

    private String TAG = "AccessService";
    private AccessService mService;
    private Map<Integer, Boolean> handleMap = new HashMap<>();

    private final int CONTACT_LIST = 0;
    private final int CHAT_LIST = 1;

    private int CURRENT = 0;


    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
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
        setCurrent();
//        Log.d(TAG,"当前页面"+CURRENT);
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                break;
        }
        switch (CURRENT){
            case CONTACT_LIST:
                fromContactToChat(event);
                break;
            case CHAT_LIST:
                fromChatToConfirm(event);
                break;
        }



    }

    private void setCurrent() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        // 聊天界面特有node
        List<AccessibilityNodeInfo> aqeList = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/aqe");
        if (aqeList.size()>0){
            CURRENT = CHAT_LIST;
        }
        // 联系人聊天列表特有node
        List<AccessibilityNodeInfo> raList = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ra");
        if (raList.size()>0){
            CURRENT = CONTACT_LIST;
        }
    }

    private void fromChatToConfirm(AccessibilityEvent event) {
        // window下的node
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        //  获取转账给你/已收钱/   tvList
        List<AccessibilityNodeInfo> moneyNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/av8");
        Log.d(TAG, "聊天界面---转账的个数" + moneyNodes.size());
        for (int i = 0;i<moneyNodes.size();i++){
            AccessibilityNodeInfo  tvMoneyDesNode = moneyNodes.get(i);
//            Log.d(TAG,tvMoneyDesNode.getText().toString());
            if (tvMoneyDesNode.getText().toString().equals("转账给你")){
//                Log.d(TAG,"找到 转账给你 ");
            }
        }
    }

    private void fromContactToChat(AccessibilityEvent event) {
        // window下的node
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        // 聊天列表 node list
        List<AccessibilityNodeInfo> chatNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bae");
        List<AccessibilityNodeInfo> textNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bai");
        Log.d(TAG, "联系人界面   聊天窗口/提示文字 ：" + chatNodes.size() + "  " + textNodes.size());
        for (int i = 0; i < textNodes.size(); i++) {
            AccessibilityNodeInfo textNode = textNodes.get(i);
//            Log.d(TAG,textNode.getText().toString());
            if (textNode.getText().toString().equals("[转账]请你确认收款")) {
                Log.d(TAG, "点击第" + i + " 进入聊天页面");
                //  点击进入收款联系人聊天界面
                clickView(chatNodes.get(i));
                CURRENT = CHAT_LIST;
            }
        }
    }


    @Override
    public void onInterrupt() {
        Log.d(TAG, "无障碍服务被中断");
    }

    /**
     * 点击事件
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

    /**
     * 是否有红包或者转账
     */
    private boolean isNeedClick(AccessibilityNodeInfo nodeInfo) {
        Log.d(TAG, nodeInfo.getText() + "");
        List<AccessibilityNodeInfo> transferList = nodeInfo.findAccessibilityNodeInfosByText("[转账]请你确认收款");
        List<AccessibilityNodeInfo> pocketList = nodeInfo.findAccessibilityNodeInfosByText("[微信红包]");
        Log.d(TAG, "transferList:" + transferList.size() + "pocketList:" + pocketList.size());
        return transferList.size() > 0 || pocketList.size() > 0;
    }
}