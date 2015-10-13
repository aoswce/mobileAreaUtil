package entity;

import java.io.Serializable;

public class MobileArea implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer mbId;
	private String mbNum;
	private String mbArea;
	private String mbType;
	private String mbCode;
	private String mbMail;
	private String cId;
	
	public MobileArea(){}
	
	public MobileArea(Integer mbId, String mbNum, String mbArea, String mbType,
			String mbCode, String mbMail,String cId) {
		super();
		this.mbId = mbId;
		this.mbNum = mbNum;
		this.mbArea = mbArea;
		this.mbType = mbType;
		this.mbCode = mbCode;
		this.mbMail = mbMail;
		this.cId = cId;
	}
	
	
	public Integer getMbId() {
		return mbId;
	}

	public void setMbId(Integer mbId) {
		this.mbId = mbId;
	}

	public String getMbNum() {
		return mbNum;
	}
	public void setMbNum(String mbNum) {
		this.mbNum = mbNum;
	}
	public String getMbArea() {
		return mbArea;
	}
	public void setMbArea(String mbArea) {
		this.mbArea = mbArea;
	}
	public String getMbType() {
		return mbType;
	}
	public void setMbType(String mbType) {
		this.mbType = mbType;
	}
	public String getMbCode() {
		return mbCode;
	}
	public void setMbCode(String mbCode) {
		this.mbCode = mbCode;
	}

	public String getMbMail() {
		return mbMail;
	}

	public void setMbMail(String mbMail) {
		this.mbMail = mbMail;
	}

	
	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	@Override
	public String toString() {
		return "MobileArea [cId=" + cId + ", mbArea=" + mbArea + ", mbCode="
				+ mbCode + ", mbId=" + mbId + ", mbMail=" + mbMail + ", mbNum="
				+ mbNum + ", mbType=" + mbType + "]";
	}
}
