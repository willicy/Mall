package model;

import java.io.Serializable;
/**
 * address∂‘œÛ
 * @author Willicy
 *
 */
public class Address implements Serializable{

	private static final long serialVersionUID = 5452467653824560048L;
	
	private Integer id;
	private Integer uid;
	private String name;
	private String province;
	private String city;
	private String area;
	private String district;
	private String zip;
	private String address;
	private String phone;
	private String tag;
	private Integer isDefault;
	
	@Override
	public String toString() {
		return "Address [id=" + id + ", uid=" + uid + ", name=" + name + ", province=" + province + ", city=" + city
				+ ", area=" + area + ", district=" + district + ", zip=" + zip + ", address=" + address + ", phone="
				+ phone + ", tag=" + tag + ", isDefault=" + isDefault + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	
}
