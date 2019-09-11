package com.leap.common_lib.crash;

/**
 * <p>退出自动捕获异常</p>
 *
 * @author zhh
 * @version 1.0
 * @creation 2018/5/8 8:59
 * @see QuitUnexitCrash
 * @since Android API 25
 */
final class QuitUnexitCrash extends RuntimeException {
    public QuitUnexitCrash(String message) {
        super(message);
    }
}
