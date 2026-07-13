// 例外クラス
package com.zosyo.exception;

public class AlreadyReturnedException extends RuntimeException {
    public AlreadyReturnedException(String message) {
        super(message);
    }
}