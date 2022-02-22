package es.fpual.multiserviceAemetCalculator.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import es.fpual.multiserviceAemetCalculator.model.DatosAemet;

@RestController
@RequestMapping("api")
public class Controller {

//
//	@GetMapping("/datos")
//	public DatosAemet getMunicipiosSeparadosPorComas() {
//		
//		return service.getDatos();
//		
//	}

	private static List<DatosAemet> getDatos(List<String> municipios) {
		List<DatosAemet> datos = new ArrayList<>();

		HttpClient httpclient = HttpClients.createDefault();
		HttpResponse response;
		HttpEntity entity;
		String entityString;
		Gson jsonParser = new Gson();

		for (String municipio : municipios) {
			HttpGet httpget = new HttpGet("http://localhost:8080/aemet/" + municipio);

			try {
				response = httpclient.execute(httpget);
				entity = response.getEntity();
				entityString = EntityUtils.toString(entity);
				datos.add(jsonParser.fromJson(entityString, DatosAemet.class));

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return datos;
	}

	private static int division(int a, int b) {
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

	private static int suma(int a, int b) {

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
