package RDBMSTest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * hikaricp 连接池示例
 * @author wanghonggang
 * 2018-10-29
 */
public class HikariDemo {
 
    public static void main(String[] args) {
        
        //配置文件
        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/mydata");//mysql
        hikariConfig.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:orcl");//oracle
        hikariConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        hikariConfig.setUsername("whg");
        hikariConfig.setPassword("whg");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        
        hikariConfig.setIdleTimeout(10*1000);//空闲连接退出时间时间
        hikariConfig.setValidationTimeout(10*1000);
       
 
        HikariDataSource ds = new HikariDataSource(hikariConfig);
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try{            
            //创建connection
            conn = ds.getConnection();
            statement = conn.createStatement();
            
            //执行sql
            rs = statement.executeQuery("select 100 s  from dual");
 
            //取数据
            if (rs.next()){
                System.out.println(rs.getString("s"));
            }
            
            //关闭connection
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
 
    }
 
}
