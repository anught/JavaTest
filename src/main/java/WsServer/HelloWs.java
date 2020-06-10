package WsServer;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class HelloWs implements HelloWsImpl {
	@Override
	public String nc(String a, String b) {
		// TODO Auto-generated method stub
		return a + b;
	}

}
