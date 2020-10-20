package DataBase;

import java.sql.*;

public class userDB
{
    public static void main( String args[] )
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:User.db");
            //System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE User " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " PASSWORD           TEXT    NOT NULL)";


            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        //System.out.println("Table created successfully");
    }
}