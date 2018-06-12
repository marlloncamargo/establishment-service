package br.com.ganix.establishment.service.response;

public class DetailEstablishmentResponse extends Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5181014802599762038L;

	private long documentNumber;
	private String name;
	private String[] tags;
	private String description;
	private String startTime;
	private String endTime;
	private int distance;
	private String distanceUnit;
	private double ticketValue;
	public long getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(long documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public String getDistanceUnit() {
		return distanceUnit;
	}
	public void setDistanceUnit(String distanceUnit) {
		this.distanceUnit = distanceUnit;
	}
	public double getTicketValue() {
		return ticketValue;
	}
	public void setTicketValue(double ticketValue) {
		this.ticketValue = ticketValue;
	}	
}
