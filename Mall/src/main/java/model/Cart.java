package model;

import java.io.Serializable;
/**
 * cart∂‘œÛ
 * @author Willicy
 *
 */
public class Cart implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9186549562958848259L;

	
	private Integer id;
	private Integer uid;
	private Integer buy;
	private Long gid;
	private Long price;
	private Integer count;
	
	public Integer getBuy() {
		return buy;
	}
	public void setBuy(Integer buy) {
		this.buy = buy;
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
	public Long getGid() {
		return gid;
	}
	public void setGid(Long gid) {
		this.gid = gid;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "Cart [id=" + id + ", uid=" + uid + ", buy=" + buy + ", gid=" + gid + ", price=" + price + ", count="
				+ count + "]";
	}
	
}
