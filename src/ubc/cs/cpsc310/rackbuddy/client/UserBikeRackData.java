package ubc.cs.cpsc310.rackbuddy.client;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class UserBikeRackData extends BikeRackData {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
//	@Persistent
//	private boolean isFave;
	
	@Persistent
	private User user;

	public UserBikeRackData() {
		super();
		//isFave = false;
	}

	public UserBikeRackData(UserBikeRackData other) {
		super(other);
		//this.isFave = other.isFave;
		this.user = other.user;
	}

	public UserBikeRackData(String streetNumber, String bia, String streetName,
			String streetSide, String skytrainStation, int numRacks,
			String yearInstalled, double lat, double lng, boolean isFave, User user) {
		super(streetNumber, bia, streetName, streetSide, skytrainStation, numRacks,
				yearInstalled, lat, lng, isFave);
		
		//this.isFave = isFave;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

//	public boolean isFave() {
//		return isFave;
//	}
//
//	public void setFave(boolean isFave) {
//		this.isFave = isFave;
//	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserBikeRackData other = (UserBikeRackData) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}


	
	

	
	
}
