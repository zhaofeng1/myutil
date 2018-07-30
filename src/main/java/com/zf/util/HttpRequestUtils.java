package com.zf.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zf.util.useragent.UserAgentUtil;

/**
 * http请求工具
 *
 * @author zhoujian
 */
public class HttpRequestUtils {

	public static final String POST_METHOD = "POST";
	public static final String GET_METHOD = "GET";
	//	public static HttpHost QUJING_PROXY = new HttpHost(ConfigUtils.getConfig("qujing.proxy.host"), Integer.parseInt(ConfigUtils.getConfig("qujing.proxy.port")), "http");
	//	public static HttpHost VPNSO_PROXY = new HttpHost(ConfigUtils.getConfig("vpnso.proxy.host"), 443, "https");
	//	public static final String VPNSO_PROXY_USER = ConfigUtils.getConfig("vpnso.proxy.user");
	//	public static final String VPNSO_PROXY_PASS = ConfigUtils.getConfig("vpnso.proxy.pass");

	private static Logger log = LoggerFactory.getLogger(UserAgentUtil.class);

	private static final int HTTP_REQUEST_TIME_OUT = 60 * 1000;
	private static final int HTTP_SOCKET_TIME_OUT = 60 * 1000;

	private static final CookieSpecProvider easySpecProvider = new CookieSpecProvider() {
		public CookieSpec create(HttpContext context) {
			return new BrowserCompatSpec() {
				@Override
				public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
				}
			};
		}
	};

	private static final Registry<CookieSpecProvider> reg = RegistryBuilder.<CookieSpecProvider> create().register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory()).register(CookieSpecs.BROWSER_COMPATIBILITY, new BrowserCompatSpecFactory()).register("mySpec", easySpecProvider)
			.build();

	public static class HttpClientBuilder {
		private HttpClientBuilder() {
		}

		private HttpHost host;
		private String user;
		private String pass;

		public static HttpClientBuilder custom() {
			return new HttpClientBuilder();
		}

		public HttpClientBuilder setAuth(HttpHost host, String user, String pass) {
			this.host = host;
			this.user = user;
			this.pass = pass;
			return this;
		}

		public CloseableHttpClient build() {
			org.apache.http.impl.client.HttpClientBuilder builder = org.apache.http.impl.client.HttpClientBuilder.create();
			if (host != null && StringUtils.isNotBlank(user) && StringUtils.isNotBlank(pass)) {
				CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
				credentialsProvider.setCredentials(new AuthScope(host), new UsernamePasswordCredentials(user, pass));
				builder.setDefaultCredentialsProvider(credentialsProvider);
			}

			SSLContextBuilder sslCtxBuilder = new SSLContextBuilder().useTLS();
			try {
				sslCtxBuilder.loadTrustMaterial(null, new TrustStrategy() {
					@Override
					public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						return true;
					}
				});
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslCtxBuilder.build(), new X509HostnameVerifier() {
					@Override
					public boolean verify(String arg0, SSLSession arg1) {
						return true;
					}

					@Override
					public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
					}

					@Override
					public void verify(String host, X509Certificate cert) throws SSLException {

					}

					@Override
					public void verify(String host, SSLSocket ssl) throws IOException {

					}
				});
				Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", new PlainConnectionSocketFactory()).register("https", sslsf).build();
				PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
				builder.setSSLSocketFactory(sslsf);
				cm.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(HTTP_REQUEST_TIME_OUT).setSoLinger(HTTP_REQUEST_TIME_OUT).build());
				builder.setConnectionManager(cm);
				//  默认cookie策略
				builder.setDefaultCookieSpecRegistry(reg);
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
			}
			return builder.build();
		}

	}

	private static HttpUriRequest buildRequestMethod(String url, String httpMethod) {
		HttpUriRequest request = new HttpGet(url);
		if (POST_METHOD.equals(httpMethod)) {
			request = new HttpPost(url);
		}
		return request;
	}

	/**
	 * 普通请求
	 *
	 * @param url
	 * @param httpMethod GET、POST
	 * @param headers
	 * @return
	 */
	public static ResponseEntity request(String url, String httpMethod, Header[] headers) {
		return executeRequest(url, httpMethod, null, null, null, headers);
	}

	/**
	 * 无验证网络代理请求
	 *
	 * @param url
	 * @param httpMethod GET、POST
	 * @param proxyHost
	 * @param headers
	 * @return
	 */
	public static ResponseEntity requestByProxyNoAuth(String url, String httpMethod, HttpHost proxyHost, Header[] headers) {
		return executeRequest(url, httpMethod, proxyHost, null, null, headers);
	}

	/**
	 * proxy网络代理(需要认证)请求
	 *
	 * @param url
	 * @param httpMethod GET、POST
	 * @param proxyHost
	 * @param proxyUser
	 * @param proxyPass
	 * @param headers
	 * @return
	 */
	public static ResponseEntity requestByProxyAuth(String url, String httpMethod, HttpHost proxyHost, String proxyUser, String proxyPass, Header[] headers) {
		return executeRequest(url, httpMethod, proxyHost, proxyUser, proxyPass, headers);
	}

	/**
	 * basic认证请求
	 *
	 * @param url
	 * @param httpMethod GET、POST
	 * @param user
	 * @param pass
	 * @param headers
	 * @return
	 */
	//	public static ResponseEntity requestByBasicAuth(String url, String httpMethod, String user, String pass, Header[] headers) {
	//		List<Header> headerList = new ArrayList<Header>();
	//		if (headers != null) {
	//			Collections.addAll(headerList, headers);
	//		}
	//		String authKey = "Basic " + Base64Encoder.encode(user + ":" + pass);
	//		headerList.add(new BasicHeader("Authorization", authKey));
	//		// System.out.println(authKey);
	//		return executeRequest(url, httpMethod, null, null, null, headerList.toArray(new Header[] {}));
	//	}

	public static boolean isOk(int httpStatusCode) {
		if (httpStatusCode == HttpStatus.SC_OK || httpStatusCode == HttpStatus.SC_CREATED) {
			return true;
		}
		return false;
	}

	public static class ResponseEntity {
		private String content;
		private int statusCode;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}

		public boolean isHttpOk() {
			return isOk(statusCode);
		}

	}

	private static ResponseEntity executeRequest(String url, String httpMethod, HttpHost proxyHost, String proxyUser, String prorxyPass, Header[] headers) {
		ResponseEntity entity = new ResponseEntity();
		log.info("execute request " + url);
		if (httpMethod == null) {
			httpMethod = GET_METHOD;
		}

		String responseStr = "";
		CloseableHttpClient client = HttpClientBuilder.custom().setAuth(proxyHost, proxyUser, prorxyPass).build();
		CloseableHttpResponse response = null;
		try {
			HttpUriRequest request = buildRequestMethod(url, httpMethod);
			HttpRequestBase baseRequest = (HttpRequestBase) request;
			RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
			// test
			// requestConfigBuilder.setProxy(QUJING_PROXY);
			if (proxyHost != null) {
				requestConfigBuilder.setProxy(proxyHost);
			}
			// requestConfigBuilder.setProxy(new HttpHost("127.0.0.1", 3213));
			requestConfigBuilder.setConnectionRequestTimeout(HTTP_REQUEST_TIME_OUT);
			requestConfigBuilder.setConnectTimeout(HTTP_REQUEST_TIME_OUT);
			requestConfigBuilder.setSocketTimeout(HTTP_SOCKET_TIME_OUT);

			requestConfigBuilder.setCookieSpec("mySpec");

			baseRequest.setConfig(requestConfigBuilder.build());
			if (headers != null) {
				baseRequest.setHeaders(headers);
			}
			if (POST_METHOD.equals(httpMethod)) {
				buildHttpRequestParams(url, (HttpPost) request);
			}
			response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			entity.setStatusCode(statusCode);
			if (isOk(statusCode)) {
				response.getHeaders("cookie");
				for (Header header : response.getAllHeaders()) {
					System.out.println(header.getName() + ":::" + header.getValue());
				}
				InputStream in = null;
				BufferedReader br = null;
				StringBuilder sb = new StringBuilder();
				try {
					in = response.getEntity().getContent();
					br = new BufferedReader(new InputStreamReader(in, Consts.UTF_8), 1024 * 100);
					String l = null;
					while ((l = br.readLine()) != null) {
						sb.append(l);
					}
				} catch (Exception e) {
					sb.setLength(0);
					entity.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
				} finally {
					IOUtils.closeQuietly(br);
					IOUtils.closeQuietly(in);
				}
				// responseStr = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
				responseStr = sb.toString();
				entity.setContent(responseStr);
			} else {
				log.error(response.getStatusLine() + "");
				if (response.getEntity() != null) {
					log.error(EntityUtils.toString(response.getEntity(), Consts.UTF_8));
				}
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			log.error(sw.toString());
			entity.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
		} finally {
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(client);
		}
		return entity;
	}

	private static void buildHttpRequestParams(String url, HttpPost request) {
		int paramMarkIndex = url.indexOf("?");
		if (paramMarkIndex != -1) {
			String paramStr = url.substring(paramMarkIndex + "?".length());
			if (StringUtils.isNotBlank(paramStr)) {
				request.setEntity(new UrlEncodedFormEntity(URLEncodedUtils.parse(paramStr, Consts.UTF_8), Consts.UTF_8));
			}
		}
	}

	public static void postLargeJson(String url, String json) {

		CloseableHttpClient client = null;
		try {
			int timeout = 600;
			RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
			client = HttpClients.createDefault();

			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(config);

			StringEntity entity = new StringEntity(json);
			httpPost.setEntity(entity);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			CloseableHttpResponse response = client.execute(httpPost);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			IOUtils.closeQuietly(client);
		}

	}

	public static String requestPost(String url, String json) {

		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		String responseJsonStr = "";
		try {
			int timeout = 60;
			RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
			client = HttpClients.createDefault();

			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(config);

			StringEntity entity = new StringEntity(json, "utf-8");
			httpPost.setEntity(entity);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			response = client.execute(httpPost);
			if (response.getStatusLine().getStatusCode() != 200) {
				log.error("httppost response stauts code:" + response.getStatusLine().getStatusCode());
				httpPost.releaseConnection();
				return "";
			}
			HttpEntity respEntity = response.getEntity();
			responseJsonStr = EntityUtils.toString(respEntity, "utf-8");
		} catch (Exception e) {
			log.error("", e);
		} finally {
			IOUtils.closeQuietly(client);
		}
		return responseJsonStr;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String url = args[0];
		
		System.out.println("url:" + url);
		HttpHost host = new HttpHost("10.230.10.30", 8000);
		
		ResponseEntity entity = HttpRequestUtils.requestByProxyNoAuth(url, HttpRequestUtils.GET_METHOD, host, null);
		System.out.println(entity.content);
	}

}
