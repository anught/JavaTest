package JedisTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author css
 * @date 2019/9/24 22:20
 */

public class JedisClient {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		// scanALL();
		phoneInfoSerTest();
	}

	public static void scanALL() {
		// 创建连接池配置对象
		JedisPoolConfig config = new JedisPoolConfig();
		// 设置最大连接数
		config.setMaxTotal(25);
		config.setMaxIdle(20);
		config.setMinIdle(5);

		try (JedisPool pool = new JedisPool(config, "192.168.65.128", 6379); Jedis jedis = pool.getResource()) {
			Set s = jedis.keys("*");
			Iterator it = s.iterator();

			while (it.hasNext()) {
				String key = (String) it.next();
				String value = jedis.get(key);
				System.out.println(key + ":" + value);
			}
		}
	}

	public static void jedisClientTest() {
		// 创建连接池配置对象
		JedisPoolConfig config = new JedisPoolConfig();
		// 设置最大连接数
		config.setMaxTotal(25);
		config.setMaxIdle(20);
		config.setMinIdle(5);
		try (JedisPool pool = new JedisPool(config, "192.168.65.128", 6379); Jedis jedis = pool.getResource()) {
			// 登录，如果没有设置密码这段可以省略
			// jedis.auth("1234");
			// 选择DB0数据库
			jedis.select(0);
			Set<String> keyList = jedis.keys("*");
			System.out.println(keyList);

			String key1 = "a", key2 = "key2";
			// none(key不存在),string(字符串),list(列表),set(集合),zset(有序集),hash(哈希表)
			String type = jedis.type(key1);

			// System.out.println(type);

			byte bt[] = new byte[10];

			bt[9] = 0b00000011;
			jedis.set("key1".getBytes(), bt);
			// System.out.println(new String(bt));
			byte[] ces2 = jedis.get("key1".getBytes());
			System.out.println(ces2[9]);
		}
	}

	public static void phoneInfoSerTest() throws IOException, ClassNotFoundException {
		// 创建连接池配置对象
		JedisPoolConfig config = new JedisPoolConfig();
		// 设置最大连接数
		config.setMaxTotal(25);
		config.setMaxIdle(20);
		config.setMinIdle(5);
		try (JedisPool pool = new JedisPool(config, "192.168.65.128", 6379); Jedis jedis = pool.getResource()) {
			// 序列化入库
			List<phoneInfoSerializable> news = new ArrayList<phoneInfoSerializable>();
			news.add(new phoneInfoSerializable("title1"));
			news.add(new phoneInfoSerializable("title2"));
			news.add(new phoneInfoSerializable("title3"));

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(news);

			// 写入 Redis
			String key = "hahah";
			jedis.set(key.getBytes(), baos.toByteArray());
			// 关闭流
			oos.close();

			// 反序列化

			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(jedis.get(key.getBytes()));
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			List<phoneInfoSerializable> oo = (List<phoneInfoSerializable>) objectInputStream.readObject();
			for (phoneInfoSerializable o : oo) {
				System.out.println(o.getCity());
			}
		}

	}

}