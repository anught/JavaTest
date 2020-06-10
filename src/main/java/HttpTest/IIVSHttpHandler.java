package HttpTest;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.apache.http.client.methods.HttpPost;
import org.glassfish.grizzly.ReadHandler;
import org.glassfish.grizzly.http.io.NIOReader;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

/*
 * HTTP请求处理器
 * 注意：被调用的为唯一实例，因此类成员变量会被所有连接线程共享。
 */
public class IIVSHttpHandler extends HttpHandler {
	private static DateTimeFormatter yyMMddHHmmss = DateTimeFormatter.ofPattern("yyMMddHHmmss");
//	// 线程重用对象
//	private static ThreadLocal<I1ReqParser> localI1Parser = new ThreadLocal<I1ReqParser>() {
//		@Override
//		public I1ReqParser initialValue() {
//			return new I1ReqParser();
//		}
//	};

	// 函数用builder
	private static ThreadLocal<StringBuilder> localBuilder = new ThreadLocal<StringBuilder>() {
		@Override
		public StringBuilder initialValue() {
			return new StringBuilder(65535);
		}
	};
	// 主builder
	private static ThreadLocal<StringBuilder> localBuilderMain = new ThreadLocal<StringBuilder>() {
		@Override
		public StringBuilder initialValue() {
			return new StringBuilder(65535);
		}
	};
	private static ThreadLocal<HttpPost> localPost = new ThreadLocal<HttpPost>() {
		@Override
		public HttpPost initialValue() {
			return new HttpPost();
		}
	};

	@Override
	public void service(Request request, Response response) {
		try {
			response.suspend();
			// 先设定好响应的类型和编码
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			NIOReader reader = request.getNIOReader();

			reader.notifyAvailable(new ReadHandler() {
				char buf[] = new char[65535];
				StringBuilder reqBuilder = new StringBuilder(65535); // 此builder重用可能会出错，因此不使用ThreadLocal（与异步IO有关）

				private void readData() throws IOException {
					int readLen = 0;
					while (reader.isReady()) {
						readLen = reader.read(buf);
						reqBuilder.append(buf, 0, readLen);
					}
				}

				@Override
				public void onDataAvailable() throws Exception {
					readData();
					reader.notifyAvailable(this);
				}

				@Override
				public void onError(Throwable t) {
					String clientIP = request.getHeader("ClientIP");

					try {
						reader.close();
					} catch (Exception e) {
						// do nothing
					}
					try {
						response.getWriter().write("err");
					} catch (Exception e) {
						// do nothing
					}
					response.resume();
					System.out.println(t);
					// alert("", "00", clientIP, ProviderConf.GAEnterId[0]);
				}

				@Override
				public void onAllDataRead() {// 当前请求所有数据读取完成后调用
					String clientIP = (request.getRemoteAddr()); // 获取请求方的ip地址
					try {
						readData();
						try {
							reader.close();
						} catch (Exception e) {
							// do nothing
						}

						// 读取完成，开始解析
						String reqStr = reqBuilder.toString();
						int reqLen = reqStr.getBytes().length;
						try {
						} catch (Exception e) {
						}

						response.getWriter().write("ok");
						response.resume();

					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
