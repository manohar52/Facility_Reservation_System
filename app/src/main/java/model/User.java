package model;

import android.content.Context;

public class User extends SysUser {
    private boolean revokeStatus;
    public User(String username){
        super(username);
    }
    public User getInstance(String username, Context ct){
        User user = (User) SysUser.getUser(username,ct);
        return user;
    }

    public boolean isRevokeStatus() {
        return revokeStatus;
    }

    public void setRevokeStatus(boolean revokeStatus) {
        this.revokeStatus = revokeStatus;
    }
}
