package es.fpual.multiserviceAemetCalculator;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

@org.springframework.stereotype.Service
public class Service {

	public DatosAemet getDatosDeUnMunicipio(String municipio) {

		HttpClient httpclient = HttpClients.createDefault();
		HttpResponse response;
		HttpEntity entity;
		String entityString;
		Gson jsonParser = new Gson();

		HttpGet httpget = new HttpGet("http://localhost:8084/aemet/" + municipio);

		try {
			response = httpclient.execute(httpget);
			entity = response.getEntity();
			entityString = EntityUtils.toString(entity);
			return jsonParser.fromJson(entityString, DatosAemet.class);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public int division(int a, int b) {
		HttpClient httpclient = HttpClients.createDefault();

		HttpGet httpget = new HttpGet("http://localhost:8082/calculator/divide/" + a + "/" + b);

		HttpResponse response;
		HttpEntity entity;
		try {
			response = httpclient.execute(httpget);
			entity = response.getEntity();

			return Integer.valueOf(EntityUtils.toString(entity));

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;

	}

	public int suma(int a, int b) {

		HttpClient httpclient = HttpClients.createDefault();

		HttpGet httpget = new HttpGet("http://localhost:8082/calculator/add/" + a + "/" + b);

		HttpResponse response;
		HttpEntity entity;
		try {
			response = httpclient.execute(httpget);
			entity = response.getEntity();

			return Integer.valueOf(EntityUtils.toString(entity));

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;
	}

}
