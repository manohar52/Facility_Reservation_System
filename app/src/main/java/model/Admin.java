package model;

import android.content.Context;

import DOA.user_doa;

public class Admin extends SysUser {
    public Admin(String username){
        super(username);
    }

    public void updateUser(String uname, String newRole, Context ct) {
        user_doa udao = user_doa.getInstance(SysUser.ct);
        udao.updateUserRole(uname,newRole);
        SysUser.getUser(uname,ct).setRole(newRole);
    }

    public void revokeUser(String uname) {
        user_doa udao = user_doa.getInstance(SysUser.ct);
        udao.revokeUser(uname);
    }
}
