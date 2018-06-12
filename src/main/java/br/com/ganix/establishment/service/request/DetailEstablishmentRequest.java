package br.com.ganix.establishment.service.request;

import java.io.Serializable;
import java.util.Date;

public class DetailEstablishmentRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4026228333640411801L;

	private long identificationCode;
	private Date date;
	
	public DetailEstablishmentRequest(){};
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public long getIdentificationCode() {
		return identificationCode;
	}

	public void setIdentificationCode(long identificationCode) {
		this.identificationCode = identificationCode;
	}
	
}
