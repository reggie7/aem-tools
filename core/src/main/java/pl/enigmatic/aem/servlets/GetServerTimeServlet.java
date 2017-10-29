package pl.enigmatic.aem.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.simple.JSONObject;

@SlingServlet(paths = "/bin/private/enigmatic/time/get.json", methods = { "GET", }, metatype = false)
public class GetServerTimeServlet extends SlingAllMethodsServlet {

	/** serial version UID */
	private static final long serialVersionUID = -1930620361755118182L;

	private static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/** Default constructor */
	public GetServerTimeServlet() {
		super();
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException, ServletException {
		final JSONObject json = new JSONObject();
		json.put("result", formatter.format(Calendar.getInstance().getTime()));
		response.setContentType("application/json; charset=UTF-8");
		final PrintWriter writer = response.getWriter();
		writer.write(URLDecoder.decode(json.toJSONString(), "utf-8"));
		writer.flush();
		writer.close();
	}

}