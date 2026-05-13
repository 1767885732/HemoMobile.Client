package com.mdsd.library.manage.update;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class CheckUpdateTask extends AsyncTask<String, Integer, String> {

	@Override
	protected String doInBackground(String... params) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(params[0]);
		
		HttpParams httpParams = httpClient.getParams();
		ConnManagerParams.setTimeout(httpParams, 3000);
		/* 连接超时 */
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		/* 请求超时 */
		HttpConnectionParams.setSoTimeout(httpParams, 10000);

		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (Exception e) {
			Log.e("CheckUpdateTask", e.getMessage());
		}

		return null;
	}

}
