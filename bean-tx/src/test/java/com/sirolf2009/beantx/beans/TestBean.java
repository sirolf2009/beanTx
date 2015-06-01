package com.sirolf2009.beantx.beans;

import java.util.Arrays;

import com.sirolf2009.beantx.GraphID;

public class TestBean {

	private String ez;
	private byte ezByte;
	private int[] ezArray;
	
	@GraphID public long graphID;
	
	public TestBean() {}
	
	public static TestBean GetDefaultBean() {
		TestBean bean = new TestBean();
		bean.setEz("Oh boy! This is easy");
		bean.setEzArray(new int[] {1337, 1337, 1337});
		bean.setEzByte((byte) 4);
		return bean;
	}

	public String getEz() {
		return ez;
	}

	public void setEz(String ez) {
		this.ez = ez;
	}

	public int[] getEzArray() {
		return ezArray;
	}

	public void setEzArray(int[] ezArray) {
		this.ezArray = ezArray;
	}

	public byte getEzByte() {
		return ezByte;
	}

	public void setEzByte(byte ezByte) {
		this.ezByte = ezByte;
	}

	@Override
	public String toString() {
		return "TestBean [ez=" + ez + ", ezByte=" + ezByte + ", ezArray="
				+ Arrays.toString(ezArray) + "]";
	}

}
