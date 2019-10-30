package com.leap.latte.accessibility;

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
    private Map<Integer, Boolean> handleMap = new HashMap<>();

    private final int CONTACT_LIST = 0;         // 聊天列表
    private final int CHAT_LIST = 1;            // 聊天页面
    private final int CONFIRM_MONEY = 2;        // 确认收款
    private final int ALREADY_COLLECTED = 3;     // 已收款

    private int CURRENT = 0;


    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(getApplicationContext(),"辅助功能开启",Toast.LENGTH_SHORT).show();
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
        switch (CURRENT) {
            case CONTACT_LIST:
                fromContactToChat(event);
                break;
            case CHAT_LIST:
                fromChatToConfirm(event);
                break;
            case CONFIRM_MONEY:
                confirmGetMoney(event);
                break;
            case ALREADY_COLLECTED:
                backToChatList(event);
                break;
        }


    }
    /**
     * 对话列表
     */
    private void fromContactToChat(AccessibilityEvent event) {
        Log.d(TAG, "----对话列表页面---");
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            return;
        }
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


    /**
     * 聊天页面
     */
    private void fromChatToConfirm(AccessibilityEvent event) {
        Log.d(TAG, "----聊天页面---");
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            return;
        }
        //  获取转账给你/已收钱/   tvList
        List<AccessibilityNodeInfo> moneyNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/av8");
        Log.d(TAG, "聊天界面---转账的个数" + moneyNodes.size());
        boolean flag = false;
        for (int i = 0; i < moneyNodes.size(); i++) {
            AccessibilityNodeInfo tvMoneyDesNode = moneyNodes.get(i);
//            Log.d(TAG,tvMoneyDesNode.getText().toString());
            if (tvMoneyDesNode.getText().toString().equals("转账给你")) {
//                Log.d(TAG,"找到 转账给你 ");
                clickView(tvMoneyDesNode);
                flag = true;
            }
        }
        //  如果没有未转账消息，退出当前聊天页面
        if (!flag) {
            List<AccessibilityNodeInfo> backNodeList = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/lr");
            for (int i = 0; i < backNodeList.size(); i++) {
                clickView(backNodeList.get(i));
            }
        }
    }
    /**
     * 确认收款页面
     */
    private void confirmGetMoney(AccessibilityEvent event) {
        Log.d(TAG, "----确认收款页面---");
        AccessibilityNodeInfo rootNodes = getRootInActiveWindow();
        if (rootNodes == null) {
            return;
        }
        List<AccessibilityNodeInfo> confirmBtnList = rootNodes.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/efi");
        for (int i = 0; i < confirmBtnList.size(); i++) {
//            Log.d(TAG,confirmBtnList.get(i).getText().toString());
            if (confirmBtnList.get(i).getText().toString().equals("确认收款")) {
                clickView(confirmBtnList.get(i));
                CURRENT = ALREADY_COLLECTED;
            }
        }
    }

    /**
     * 已收款
     */
    private void backToChatList(AccessibilityEvent event) {
        Log.d(TAG, "----已收款页面---");
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
     *  判断当前在哪个页面
     * */
    private void setCurrent() {
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
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "无障碍服务被中断");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"服务被终止");
        Toast.makeText(getApplicationContext(),"辅助功能被停止",Toast.LENGTH_SHORT).show();
    }
}
