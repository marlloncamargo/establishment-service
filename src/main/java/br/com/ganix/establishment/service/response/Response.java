package br.com.ganix.establishment.service.response;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;

public abstract class Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5308304699349327870L;
	
	private @Value("${application.version}") String serviceVersion;

	public String getServiceVersion() {
		return this.serviceVersion;
	}
	
}
