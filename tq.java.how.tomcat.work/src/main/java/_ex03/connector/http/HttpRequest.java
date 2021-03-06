package _ex03.connector.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.util.ParameterMap;

public class HttpRequest implements HttpServletRequest{

	protected HashMap<String, String> headers = new HashMap<String, String>();

	protected ArrayList<String> cookies = new ArrayList<String>();

	protected ParameterMap parameters = null;


	public HttpRequest(SocketInputStream inputStream) {
		//
	}

	public String getRequestURI() {

		return null;
	}

	public void setQueryString(String string) {

	}

	public void setRequestedSessionId(String substring) {


	}

	public void setRequestedSessionURL(boolean b) {


	}

	public Object getAttribute(String arg0) {

		return null;
	}

	public Enumeration<?> getAttributeNames() {

		return null;
	}

	public String getCharacterEncoding() {

		return null;
	}

	public int getContentLength() {

		return 0;
	}

	public String getContentType() {

		return null;
	}

	public ServletInputStream getInputStream() throws IOException {

		return null;
	}

	public String getLocalAddr() {

		return null;
	}

	public String getLocalName() {

		return null;
	}

	public int getLocalPort() {

		return 0;
	}

	public Locale getLocale() {

		return null;
	}

	public Enumeration<?> getLocales() {

		return null;
	}

	public String getParameter(String arg0) {

		return null;
	}

	public Map<?,?> getParameterMap() {

		return null;
	}

	public Enumeration<?> getParameterNames() {

		return null;
	}

	public String[] getParameterValues(String arg0) {

		return null;
	}

	public String getProtocol() {

		return null;
	}

	public BufferedReader getReader() throws IOException {

		return null;
	}

	public String getRealPath(String arg0) {

		return null;
	}

	public String getRemoteAddr() {

		return null;
	}

	public String getRemoteHost() {

		return null;
	}

	public int getRemotePort() {

		return 0;
	}

	public RequestDispatcher getRequestDispatcher(String arg0) {

		return null;
	}

	public String getScheme() {

		return null;
	}

	public String getServerName() {

		return null;
	}

	public int getServerPort() {

		return 0;
	}

	public boolean isSecure() {

		return false;
	}

	public void removeAttribute(String arg0) {


	}

	public void setAttribute(String arg0, Object arg1) {


	}

	public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {


	}

	public String getAuthType() {

		return null;
	}

	public String getContextPath() {

		return null;
	}

	public Cookie[] getCookies() {

		return null;
	}

	public long getDateHeader(String arg0) {

		return 0;
	}

	public String getHeader(String arg0) {

		return null;
	}

	public Enumeration<?> getHeaderNames() {

		return null;
	}

	public Enumeration<?> getHeaders(String arg0) {

		return null;
	}

	public int getIntHeader(String arg0) {

		return 0;
	}

	public String getMethod() {

		return null;
	}

	public String getPathInfo() {

		return null;
	}

	public String getPathTranslated() {

		return null;
	}

	public String getQueryString() {

		return null;
	}

	public String getRemoteUser() {

		return null;
	}

	public StringBuffer getRequestURL() {

		return null;
	}

	public String getRequestedSessionId() {

		return null;
	}

	public String getServletPath() {

		return null;
	}

	public HttpSession getSession() {

		return null;
	}

	public HttpSession getSession(boolean arg0) {

		return null;
	}

	public Principal getUserPrincipal() {

		return null;
	}

	public boolean isRequestedSessionIdFromCookie() {

		return false;
	}

	public boolean isRequestedSessionIdFromURL() {

		return false;
	}

	public boolean isRequestedSessionIdFromUrl() {

		return false;
	}

	public boolean isRequestedSessionIdValid() {

		return false;
	}

	public boolean isUserInRole(String arg0) {

		return false;
	}

	public void setMethod(String method) {


	}

	public void setProtocol(String protocol) {


	}

	public void setRequestURI(String normalizedUri) {


	}

}
