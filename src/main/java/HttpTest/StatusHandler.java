package HttpTest;

import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;
import java.util.Map.Entry;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;

public class StatusHandler extends HttpHandler {

	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);

	@Override
	public void service(Request request, Response response) throws Exception {
		response.suspend();

		response.setContentType("text/plain");
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		// 处理认证信息
		String authStr = request.getAuthorization();
		boolean authed = false;
		if(authStr != null) {
			try {
				String decodedAuth = new String(Base64.getDecoder().decode(authStr.split(" ")[1]));
				if(decodedAuth.equals("teligen:thinker")) {
					authed = true;
				}
			} catch(Exception e) {
				// do nothing
			}
		}
		if(!authed) {
			response.setHeader("WWW-Authenticate", "Basic realm=\"User Visible Realm\" charset=\"UTF-8\"");
			response.setStatus(HttpStatus.UNAUTHORIZED_401);
			response.resume();
		} else {
			String path = request.getRequestURI();
			if(path.startsWith("/status/time")) {
				response.getWriter().write(LocalDateTime.now().format(dateTimeFormatter));
			} else if(path.startsWith("/status/trace")) {
				Writer writer = response.getWriter();
		        for (Entry<Thread,StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet())
		        {
		            Thread thread = entry.getKey();
		            StackTraceElement[] stackTraceElements = entry.getValue();
		            writer.write("Thread： " + thread.getName() + "\n");
		            for (StackTraceElement element : stackTraceElements) {
		            	writer.write("\t" + element + "\n");
		            }
		        }
			} else {
				response.getWriter().write("ok");
			}
			response.resume();
		}
	}

}

