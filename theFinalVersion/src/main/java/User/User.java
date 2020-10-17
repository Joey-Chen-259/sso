package User;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gacl
 * 用户实体类
 */
public class User implements Serializable {

    private static final long serialVersionUID = -4313782718477229465L;

    // 用户ID
    private String id;
    // 用户名
    private String userName;
    // 用户密码
    private String userPwd;
    // 用户邮箱


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
