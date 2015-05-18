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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestBean other = (TestBean) obj;
		if (ez == null) {
			if (other.ez != null)
				return false;
		} else if (!ez.equals(other.ez))
			return false;
		if (!Arrays.equals(ezArray, other.ezArray))
			return false;
		if (ezByte != other.ezByte)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TestBean [ez=" + ez + ", ezByte=" + ezByte + ", ezArray="
				+ Arrays.toString(ezArray) + "]";
	}

}
