package com.yhw.common.utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author sun.meixian
 * @title MyAuthentication
 * @description
 * @date 2018/7/9
 * @copyright Copyright ? 2010-2020 BYD Corporation. All rights reserved.
 */

public class EmailAuthentication extends Authenticator {
    private String username;
    private String password;

    public EmailAuthentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {

        return new PasswordAuthentication(username, password);
    }
}
