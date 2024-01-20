package com.group.usercenter.model.domain.request;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author vic
 */
public class UserLoginRequest implements Serializable {
    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    private static final long serialVersionUID = 8559176096995185206L;
    private String userAccount;

    private String userPassword;

}
