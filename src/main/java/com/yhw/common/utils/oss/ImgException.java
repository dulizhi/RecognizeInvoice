package com.yhw.common.utils.oss;

/**
 * @author zhang.jiali4
 * @title ImgException
 * @description
 * @date 2019/10/23
 * @copyright Copyright ? 2010-2020 BYD Corporation. All rights reserved.
 */
public class ImgException extends RuntimeException {

    public ImgException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImgException(String message) {
        super(message);
    }

}

