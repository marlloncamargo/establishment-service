package br.com.ganix.establishment.service.response;

public class EstablishmentResponse extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7286470415481857706L;
	/**
	 * 
	 */
	private String cnpj;
	private String fantasyName;
	private double ticketValue;
	private long manQuantity;
	private long womanQuantity;
	private long quantityLimit;
	private String establishmentType;
	private double latitude;
	private double longitude;

	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getFantasyName() {
		return fantasyName;
	}
	public void setFantasyName(String fantasyName) {
		this.fantasyName = fantasyName;
	}
	public double getTicketValue() {
		return ticketValue;
	}
	public void setTicketValue(double ticketValue) {
		this.ticketValue = ticketValue;
	}
	public long getManQuantity() {
		return manQuantity;
	}
	public void setManQuantity(long manQuantity) {
		this.manQuantity = manQuantity;
	}
	public long getWomanQuantity() {
		return womanQuantity;
	}
	public void setWomanQuantity(long womanQuantity) {
		this.womanQuantity = womanQuantity;
	}
	public long getQuantityLimit() {
		return quantityLimit;
	}
	public void setQuantityLimit(long quantityLimit) {
		this.quantityLimit = quantityLimit;
	}
	public String getEstablishmentType() {
		return establishmentType;
	}
	public void setEstablishmentType(String establishmentType) {
		this.establishmentType = establishmentType;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}