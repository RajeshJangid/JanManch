package com.janmanch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class APICallUtility {

	public JSONObject sendGetRequest(String requrl) {
		try {
			URL url = new URL(requrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Host", "apitest.sewadwaar.rajasthan.gov.in");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setAllowUserInteraction(false);
			conn.setDoOutput(true);

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			StringBuilder response = new StringBuilder();
			while ((output = br.readLine()) != null) {
				response.append(output);
			}

			conn.disconnect();

			return new JSONObject(response.toString());
		} catch (Exception e) {

		}
		return null;
	}
}
