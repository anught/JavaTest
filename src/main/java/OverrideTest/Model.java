package OverrideTest;

/**
 * 测试 override 如果不重写 hashcode 的影响
 *
 */
public class Model {

	private String name;
	private String age;
	private String otherName;

	/**
	 * @param name
	 * @param age
	 * @param otherName
	 */
	public Model(String name, String age, String otherName) {
		super();
		this.name = name;
		this.age = age;
		this.otherName = otherName;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}

	/**
	 * @return the otherName
	 */
	public String getOtherName() {
		return otherName;
	}

	/**
	 * @param otherName the otherName to set
	 */
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	@Override
	public int hashCode() {

		int a = 7;
		int b = 11;
		// a和b为不相等的int型常量
		int r = a;
		r = r * b + name.hashCode();
		r = r * b + age.hashCode();

		return r;
		// return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Model)) {
			return false;
		}

		Model other = (Model) obj;
		if (name.equals(other.getName()) && age.equals(other.getAge())) {
			return true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Model [name=" + name + ", age=" + age + ", otherName=" + otherName + "]";
	}

}