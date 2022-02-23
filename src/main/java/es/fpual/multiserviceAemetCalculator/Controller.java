package es.fpual.multiserviceAemetCalculator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

	@Autowired
	Service service;

	@GetMapping("/media/{stringMunicipios}")
	private ResponseEntity<?> getMedia(@PathVariable String stringMunicipios) {

		String[] codesMunicipios = stringMunicipios.split(",");
				
		Map<String, Object> respuesta = new LinkedHashMap<>();
		List<String> nombreMunicipios = new ArrayList<String>();
		int mediaMax = 0;
		int mediaMin = 0;
				
		int totalMin = 0;
		int totalMax = 0;
		
		for (String municipio : codesMunicipios) {
			DatosAemet dato = this.service.getDatosDeUnMunicipio(municipio);

			totalMax = this.service.suma(dato.getTemperaturaMaxima(), totalMax);
			totalMin = this.service.suma(dato.getTemperaturaMinima(), totalMin);

			nombreMunicipios.add(dato.getMunicipio());
		} 
		
		mediaMax = this.service.division(totalMax, codesMunicipios.length);
		mediaMin = this.service.division(totalMin, codesMunicipios.length);
		
		respuesta.put("Municipios: ", nombreMunicipios);
		respuesta.put("MediaMaxima: ", mediaMax);
		respuesta.put("MediaMinima: ", mediaMin);
		
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

}
