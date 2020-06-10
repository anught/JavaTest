package WsServer;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface HelloWsImpl {
	@WebMethod
	public String nc(String a, String b);
}
