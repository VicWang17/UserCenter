package com.group.usercenter.model.domain.request;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author vic
 */
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 498135722730998432L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

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

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }


}
