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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import es.fpual.multiserviceAemetCalculator.model.DatosAemet;

@RestController
@RequestMapping("api")
public class Controller {

//
//	@GetMapping("/datos{}")
//	public DatosAemet getMunicipiosSeparadosPorComas() {
//		
//		return service.getDatos();
//		
//	}

	@GetMapping("/media/{stringMunicipios}")
	private static ResponseEntity<?> getMedia(@PathVariable String stringMunicipios) {

		String[] codesMunicipios = stringMunicipios.split(",");
		int totalMin = 0;
		int totalMax = 0;
		List<String> nombreMunicipios = new ArrayList<String>();

		for (int i = 0; i < codesMunicipios.length; i++) {

			DatosAemet dato = getDatosDeUnMunicipio(codesMunicipios[i]);

			totalMax = suma(dato.getTemperaturaMaxima(), totalMax);
			totalMin = suma(dato.getTemperaturaMinima(), totalMin);
			
			nombreMunicipios.add(dato.getMunicipio());
			
		}

		int mediaMax = division(totalMax, codesMunicipios.length);
		int mediaMin = division(totalMin, codesMunicipios.length);
		
		String respuesta = "De los municipios: " + nombreMunicipios.toString() + "; la temperatura máxima media es " + mediaMax
				+ " y la temperatura mínima media es " + mediaMin;
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	private static DatosAemet getDatosDeUnMunicipio(String municipio) {

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

//	private static List<DatosAemet> getDatos(List<String> municipios) {
//	List<DatosAemet> datos = new ArrayList<>();
//
//	HttpClient httpclient = HttpClients.createDefault();
//	HttpResponse response;
//	HttpEntity entity;
//	String entityString;
//	Gson jsonParser = new Gson();
//
//	for (String municipio : municipios) {
//		HttpGet httpget = new HttpGet("http://localhost:8080/aemet/" + municipio);
//
//		try {
//			response = httpclient.execute(httpget);
//			entity = response.getEntity();
//			entityString = EntityUtils.toString(entity);
//			datos.add(jsonParser.fromJson(entityString, DatosAemet.class));
//
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//	return datos;
//}

}
