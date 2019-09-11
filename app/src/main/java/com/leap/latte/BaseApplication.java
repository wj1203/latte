package com.leap.latte;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.leap.common_lib.crash.UnexitCrash;
import com.leap.common_lib.thread.ThreadPoolManager;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        crashIntercept();
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
}
