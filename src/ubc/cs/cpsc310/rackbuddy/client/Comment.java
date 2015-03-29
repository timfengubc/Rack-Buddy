package ubc.cs.cpsc310.rackbuddy.client;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

public class Comment implements Serializable{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	protected String message;
	
	@Persistent
	protected Long bikeRackID;
	
	@Persistent
	protected String emailAddress;
	
	public Long getBikeRackID() {
		return bikeRackID;
	}

	public void setBikeRackID(Long bikeRackIDs) {
		this.bikeRackID = bikeRackIDs;
	}

	public String getMessage() {
		return message;
	}

	public String getEmail() {
		return emailAddress;
	}
}
