package WsServer;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;

public class wsclient {
	private Client client;

	public void init(long connectTimeout, int requestTimeout) throws MalformedURLException, Exception {
		String url = "file:///usr/rivp/cf.d/NciicServices.wsdl";
		client = new Client(new URL(url));
		HttpClientParams params = new HttpClientParams();
		DefaultHttpMethodRetryHandler retryhandler = new DefaultHttpMethodRetryHandler(0, false);
		params.setParameter(HttpClientParams.RETRY_HANDLER, retryhandler);
		// 100 hanshark 关闭
		params.setParameter(HttpClientParams.USE_EXPECT_CONTINUE, Boolean.FALSE);
		// set connection timeout
		params.setParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, connectTimeout);
		// set response timeout
		params.setIntParameter(HttpClientParams.SO_TIMEOUT, requestTimeout);

		client.setProperty(CommonsHttpMessageSender.HTTP_CLIENT_PARAMS, params);
		// 防止出现CLOSE_WAIT
		client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");

	}

	public String nc(String inLicense, String inConditions, long timeout) throws Exception {
		init(timeout, (int) timeout); // flag 190121
		Object[] res = client.invoke("nc", new Object[] { inLicense, inConditions });
		return (String) res[0];
	}

}
