package HttpTest;

import java.io.IOException;

import javax.net.ssl.SSLContext;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.nio.NIOTransport;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.grizzly.strategies.WorkerThreadIOStrategy;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;

public class GrizzlyHttpServerTest {

	public static HttpServer server;
	public static Integer forcePort = null;
	public static int port = 80; // 监听端口
	public static int selectorNum = 20; // 选择器数量
	public static int coreThreadNum = 20; // 核心工作线程数
	public static int maxThreadNum = 20; // 最大工作线程数

	public static int start() throws IOException {
		server = new HttpServer();
		NetworkListener listener = new NetworkListener("Test", NetworkListener.DEFAULT_NETWORK_HOST, port);

		NIOTransport transport = listener.getTransport();
		transport.setIOStrategy(WorkerThreadIOStrategy.getInstance());
		// 核心线程配置
		transport.setSelectorRunnersCount(selectorNum);

		// 工作线程配置
		ThreadPoolConfig wtpConfig = transport.getWorkerThreadPoolConfig();
		wtpConfig.setCorePoolSize(coreThreadNum);
		wtpConfig.setMaxPoolSize(maxThreadNum);

		if (false == "http".equals("http")) {
			SSLContextConfigurator sslContextConfig = new SSLContextConfigurator();
			// set up security context
			sslContextConfig.setKeyStoreFile("server.keystore"); // contains server cert and key
			sslContextConfig.setKeyStorePass("xtyrivp");

			boolean clientAuth = false;
			if (clientAuth) {
				sslContextConfig.setTrustStoreFile("trust.keystore");
				sslContextConfig.setTrustStorePass("trustrivp");
			}

			// Create context and have exceptions raised if anything wrong with keystore or
			// password
			SSLContext sslContext = sslContextConfig.createSSLContext(true);
			listener.setSecure(true);
			listener.setSSLEngineConfig(
					new SSLEngineConfigurator(sslContext).setClientMode(false).setNeedClientAuth(clientAuth));
		}
		server.addListener(listener);
		ServerConfiguration serverConf = server.getServerConfiguration();
		serverConf.addHttpHandler(new StatusHandler(), "/status");
		server.start();
		return 0;
	}
}
