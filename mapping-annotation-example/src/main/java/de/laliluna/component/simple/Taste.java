
package de.laliluna.component.simple;

import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable
public class Taste {


	private String firstImpression;

	private String evaluation;

	public Taste(){
		
	}
	
	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public String getFirstImpression() {
		return firstImpression;
	}

	public void setFirstImpression(String firstImpression) {
		this.firstImpression = firstImpression;
	}

}
