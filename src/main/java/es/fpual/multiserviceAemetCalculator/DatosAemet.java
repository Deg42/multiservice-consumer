package es.fpual.multiserviceAemetCalculator;

public class DatosAemet {
	
	private String municipio;
	private Integer temperaturaMaxima;
	private Integer temperaturaMinima;
	
	public String getMunicipio() {
		return municipio;
	}
	public Integer getTemperaturaMaxima() {
		return temperaturaMaxima;
	}
	public Integer getTemperaturaMinima() {
		return temperaturaMinima;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public void setTemperaturaMaxima(Integer temperaturaMaxima) {
		this.temperaturaMaxima = temperaturaMaxima;
	}
	public void setTemperaturaMinima(Integer temperaturaMinima) {
		this.temperaturaMinima = temperaturaMinima;
	}
	
	
	
	
	@Override
	public String toString() {
		return "DatosAemet [municipio=" + municipio + ", temperaturaMaxima=" + temperaturaMaxima
				+ ", temperaturaMinima=" + temperaturaMinima + "]";
	}
			
}
