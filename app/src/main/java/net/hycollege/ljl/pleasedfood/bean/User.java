package net.hycollege.ljl.pleasedfood.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    @Id
    private String id;
    private String userinfobean;
    private String loginstatus;
    private String username;
    private String password;
    private String phonenum;
    @Generated(hash = 1870644674)
    public User(String id, String userinfobean, String loginstatus, String username,
            String password, String phonenum) {
        this.id = id;
        this.userinfobean = userinfobean;
        this.loginstatus = loginstatus;
        this.username = username;
        this.password = password;
        this.phonenum = phonenum;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public String getLoginstatus() {
        return this.loginstatus;
    }
    public void setLoginstatus(String loginstatus) {
        this.loginstatus = loginstatus;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhonenum() {
        return this.phonenum;
    }
    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserinfobean() {
        return this.userinfobean;
    }
    public void setUserinfobean(String userinfobean) {
        this.userinfobean = userinfobean;
    }
  
}
