package com.leap.common_lib.livedatabus;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: leap
 * @time: 2019/10/15
 * @classname: LiveDataBus
 * @description:  LiveDataBus -> 通信框架
 */
public class LiveDataBus {

    private final Map<String, BusMutableLiveData<Object>> bus;

    private LiveDataBus() {
        bus = new HashMap<>();
    }

    private static class SingletonHolder {
        private static final LiveDataBus DEFAULT_BUS = new LiveDataBus();
    }

    public static LiveDataBus get() {
        return SingletonHolder.DEFAULT_BUS;
    }

    public <T> MutableLiveData<T> with(String key, Class<T> type) {
        if (!bus.containsKey(key)) {
            bus.put(key, new BusMutableLiveData<>());
        }
        return (MutableLiveData<T>) bus.get(key);
    }

    public MutableLiveData<Object> with(String key) {
        return with(key, Object.class);
    }

    private static class ObserverWrapper<T> implements Observer<T> {

        private Observer<T> observer;

        public ObserverWrapper(Observer<T> observer) {
            this.observer = observer;
        }
        @Override
        public void onChanged(@Nullable T t) {
            if (observer != null) {
                if (isCallOnObserve()) {
                    return;
                }
                observer.onChanged(t);
            }
        }
        private boolean isCallOnObserve() {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                for (StackTraceElement element : stackTrace) {
                    if ("android.arch.lifecycle.LiveData".equals(element.getClassName()) &&
                            "observeForever".equals(element.getMethodName())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
    // 自定义LiveData类（解决未订阅接收的bug）
    private static class BusMutableLiveData<T> extends MutableLiveData<T> {
        private Map<Observer, Observer> observerMap = new HashMap<>();
        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
            super.observe(owner, observer);
            try {
                // 设置observer的version和LiveData一致
                hook(observer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public void observeForever(@NonNull Observer<T> observer) {
            if (!observerMap.containsKey(observer)) {
                observerMap.put(observer, new ObserverWrapper(observer));
            }
            super.observeForever(observerMap.get(observer));
        }
        @Override
        public void removeObserver(@NonNull Observer<T> observer) {
            Observer realObserver = null;
            if (observerMap.containsKey(observer)) {
                realObserver = observerMap.remove(observer);
            } else {
                realObserver = observer;
            }
            super.removeObserver(realObserver);
        }
        private void hook(@NonNull Observer<T> observer) throws Exception {
            // 获取livedata的class对象
            Class<LiveData> classLiveData = LiveData.class;
            // 获取   LiveData类的mObservers对象 （Map对象）的 Field对象
            Field fieldObservers = classLiveData.getDeclaredField("mObservers");
            // 将mObservers 的private设置为 public
            fieldObservers.setAccessible(true);
            //  获取当前livedata的mObservers对象(map)
            Object objectObservers = fieldObservers.get(this);
            // 拿到mObservers（map）的class对象
            Class<?> classObservers = objectObservers.getClass();
            // 通过map的class对象拿到get（）的method对象
            Method methodGet = classObservers.getDeclaredMethod("get", Object.class);
            methodGet.setAccessible(true);
            // 通过map 的 get Method对象 拿到值 （Entry）  （arg1：map ，arg2：key ）
            Object objectWrapperEntry = methodGet.invoke(objectObservers, observer);
            // 拿到wrapper
            Object objectWrapper = null;
            if (objectWrapperEntry instanceof Map.Entry) {
                objectWrapper = ((Map.Entry) objectWrapperEntry).getValue();
            }
            if (objectWrapper == null) {
                throw new NullPointerException("Wrapper can not be bull!");
            }
            // 反射wrapper对象
            Class<?> classObserverWrapper = objectWrapper.getClass().getSuperclass();
            // 拿到wrapper的version
            Field fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion");
            fieldLastVersion.setAccessible(true);
            //get livedata's version
            Field fieldVersion = classLiveData.getDeclaredField("mVersion");
            fieldVersion.setAccessible(true);
            Object objectVersion = fieldVersion.get(this);
            //set wrapper's version
            fieldLastVersion.set(objectWrapper, objectVersion);
        }
    }
}
