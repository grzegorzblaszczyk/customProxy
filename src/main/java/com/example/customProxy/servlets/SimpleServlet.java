package com.example.customProxy.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Servlet implementation class SimpleServlet
 */
public class SimpleServlet extends HttpServlet {
	static final String CONTENT_TYPE = "Content-Type";
	static final String CHARSET_UTF_8 = "charset=UTF-8";
	static final String CHARSET_UNDEFINED = "charset=undefined";
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(SimpleServlet.class.getName());

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			RequestConfig config = RequestConfig.custom()
					.build();
			HttpGet httpGet = new HttpGet(request.getRequestURL().toString());
			httpGet.setConfig(config);

			logger.log(Level.INFO, "Executing request " + httpGet.getRequestLine());
			CloseableHttpResponse targetResponse = httpclient.execute(httpGet);
			if (targetResponse != null) {
				try {
					response.setHeader(CONTENT_TYPE, extractContentType(targetResponse));
					response.getWriter().append(EntityUtils.toString(targetResponse.getEntity()));
				} finally {
					targetResponse.close();
				}
			}
		} finally {
			httpclient.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	String extractContentType(CloseableHttpResponse response) {
		return response.getEntity().getContentType().getValue();
	}

}
