package com.mdsd.library.net.http;

import java.io.Serializable;

public class HttpEntityParam implements Serializable {
	private static final long serialVersionUID = 1L;

	private String url;

	private HttpParams httpParams;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpParams getHttpParams() {
		return httpParams;
	}

	public void setHttpParams(HttpParams httpParams) {
		this.httpParams = httpParams;
	}
	
	public HttpEntityParam(String url){
		this.url = url;
	}
	
	public HttpEntityParam(String url,HttpParams httpParams){
		this.url = url;
		this.httpParams = httpParams;
	}

}
