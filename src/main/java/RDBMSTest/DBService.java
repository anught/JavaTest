package RDBMSTest;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 数据库服务 Created by Lovell on 16/6/18.
 */
public class DBService {
	private static Logger logger = LoggerFactory.getLogger(DBService.class);

	private static final String DB_CONFIG_FILE = "/db.properties";

	// 数据库连接数
	private short db_max_conn = 0;

	// 数据库服务器addr
	private String db_url = null;

	// 数据库连接端口
	private short db_port = 0;

	// 数据库名称
	private String db_name = null;

	// 数据库登录用户名
	private String db_username = null;

	// 数据库登录密码
	private String db_password = null;

	// 数据库连接

	private static DBService dBService;

	public static DBService getInstance() {
		if (dBService == null) {
			dBService = new DBService();
		}
		return dBService;
	}

	HikariDataSource dataSource = null;

	public void start() throws IOException, SQLException {
		Properties properties = new Properties();
		InputStream in = DBService.class.getClass().getResourceAsStream(DB_CONFIG_FILE);
		properties.load(in);

		db_max_conn = Short.valueOf(properties.getProperty("db_max_conn"));
		db_url = String.valueOf(properties.getProperty("db_url"));
		db_port = Short.valueOf(properties.getProperty("db_port"));
		db_name = String.valueOf(properties.getProperty("db_name"));
		db_username = String.valueOf(properties.getProperty("db_username"));
		db_password = String.valueOf(properties.getProperty("db_password"));

		if (db_url == null || db_url.length() == 0) {
			logger.error("配置的数据库ip地址错误!");
			System.exit(0);
		}

		HikariConfig config = new HikariConfig();
		config.setMaximumPoolSize(db_max_conn);
		config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		config.addDataSourceProperty("serverName", db_url);
		config.addDataSourceProperty("port", db_port);
		config.addDataSourceProperty("databaseName", db_name);
		config.addDataSourceProperty("user", db_username);
		config.addDataSourceProperty("password", db_password);
		dataSource = new HikariDataSource(config);

//        // 也可以这样写
//        config.setDriverClassName("com.mysql.jdbc.Driver");
//        config.setJdbcUrl("jdbc:mysql://"+ db_url +"/" + db_name + "?useUnicode=true&characterEncoding=utf8&useSSL=false");
//        config.setUsername(db_username);
//        config.setPassword(db_password);
//        config.addDataSourceProperty("cachePrepStmts", "true");
//        config.addDataSourceProperty("prepStmtCacheSize", "250");
//        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
//        // 设置连接超时为8小时
//        config.setConnectionTimeout(8 * 60 * 60);
//        HikariDataSource dataSource = new HikariDataSource(config);
	}

	public Connection getConnection() throws SQLException {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean stop() throws SQLException {
		dataSource.close();
		return true;
	}
}