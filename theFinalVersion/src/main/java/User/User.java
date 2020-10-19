package User;
import java.sql.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author gacl
 * 用户实体类
 */
public class User implements Serializable {
    Connection c = null;
    Statement stmt = null;
    private static final long serialVersionUID = -4313782718477229465L;

    // 用户ID
    private String id;
    // 用户名
    private String userName;
    // 用户密码
    private String userPwd;
    // 用户邮箱


    public String getId(int IDInput) {

        return id;
    }

    public boolean checkLogin(int id,String pwd){
        int ID;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/Users/lasuerte/Desktop/sso/theFinalVersion/User.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM User" );
            System.out.println("Change database successfully");
            while ( rs.next() ) {

                ID = rs.getInt("ID");
                String password = rs.getString("PASSWORD");
                if(ID == id && pwd.equals(password)){
                    rs.close();
                    stmt.close();
                    c.close();
                    System.out.println("密码正确");
                    return true;
                }
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return false;
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
