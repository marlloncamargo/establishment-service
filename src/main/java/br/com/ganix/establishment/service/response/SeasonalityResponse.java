package br.com.ganix.establishment.service.response;

public class SeasonalityResponse extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2789162078492806207L;

	private long establishment;
	private int days;
	private String registryTime;

	public long getEstablishment() {
		return establishment;
	}

	public void setEstablishment(long establishment) {
		this.establishment = establishment;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getRegistryTime() {
		return registryTime;
	}

	public void setRegistryTime(String registryTime) {
		this.registryTime = registryTime;
	}
}
