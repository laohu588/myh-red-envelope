package com.myh.red.envelope.constant;

public enum ChannelEnum {

	A("A", "1"),
	B("B", "2");

	/**
	 * 通道名称;
	 */
	private String name;
	/**
	 * 通道序号;
	 */
	private String no;

	private ChannelEnum(String name, String no) {
		this.name = name;
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

}
