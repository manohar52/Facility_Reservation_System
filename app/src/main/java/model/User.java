package model;

public class User extends SysUser {
    private boolean revokeStatus;
    public User(String username){
        super(username);
    }

    public boolean isRevokeStatus() {
        return revokeStatus;
    }

    public void setRevokeStatus(boolean revokeStatus) {
        this.revokeStatus = revokeStatus;
    }
}
