package JedisTest;

import java.io.Serializable;

public class phoneInfoSerializable implements Serializable {
	private static final long serialVersionUID = 1L;
	private String city = null;

	public phoneInfoSerializable(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
