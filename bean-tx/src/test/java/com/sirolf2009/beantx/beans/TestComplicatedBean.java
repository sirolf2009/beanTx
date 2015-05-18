package com.sirolf2009.beantx.beans;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TestComplicatedBean {
	
	private Map<Map<String, String>, Map<Object, Object>> notSoEzMap;
	private List<InnerBean> innerBeans;

	private String ez;
	private byte ezByte;
	private int[] ezArray;
	
	public static TestComplicatedBean GetDefaultBean() {
		TestComplicatedBean bean = new TestComplicatedBean();
		bean.setEz("Oh boy! This is easy");
		bean.setEzArray(new int[] {1337, 1337, 1337});
		bean.setEzByte((byte) 4);
		
		Map<String, String> key = new HashMap<String, String>();
		key.put("fuck", "all");
		key.put("y'all", "faggots");
		Map<Object, Object> value = new HashMap<Object, Object>();
		value.put(new InnerBean(), new Object());
		value.put(new Integer(6), true);
		bean.setNotSoEzMap(new HashMap<Map<String, String>, Map<Object, Object>>());
		bean.getNotSoEzMap().put(key, value);
		
		List<InnerBean> innerBeans = new LinkedList<InnerBean>();
		innerBeans.add(new InnerBean());
		InnerBean bean1 = new InnerBean();
		bean1.setEzStringArray(new String[] {"So a zombie walks into a bar.", "Through the wall..."}); //Dayz jokes
		InnerBean bean2 = new InnerBean();
		bean2.setEzStringArray(new String[] {"Don't let the door hit you in the ass", "I mean... kill you"}); //Dayz jokes
		bean2.setInnerbean(bean1);
		innerBeans.add(bean2);
		bean.setInnerBeans(innerBeans);
		return bean;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((innerBeans == null) ? 0 : innerBeans.hashCode());
		result = prime * result
				+ ((notSoEzMap == null) ? 0 : notSoEzMap.hashCode());
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
		TestComplicatedBean other = (TestComplicatedBean) obj;
		if (innerBeans == null) {
			if (other.innerBeans != null)
				return false;
		} else if (!innerBeans.equals(other.innerBeans))
			return false;
		if (notSoEzMap == null) {
			if (other.notSoEzMap != null)
				return false;
		} else if (!notSoEzMap.equals(other.notSoEzMap))
			return false;
		return true;
	}
	
	public Map<Map<String, String>, Map<Object, Object>> getNotSoEzMap() {
		return notSoEzMap;
	}

	public void setNotSoEzMap(
			Map<Map<String, String>, Map<Object, Object>> notSoEzMap) {
		this.notSoEzMap = notSoEzMap;
	}

	public List<InnerBean> getInnerBeans() {
		return innerBeans;
	}

	public void setInnerBeans(List<InnerBean> innerBeans) {
		this.innerBeans = innerBeans;
	}
	
	public static class InnerBean {
		
		private InnerBean innerbean;
		private String[] ezStringArray;
		
		public InnerBean getInnerbean() {
			return innerbean;
		}
		public void setInnerbean(InnerBean innerbean) {
			this.innerbean = innerbean;
		}
		public String[] getEzStringArray() {
			return ezStringArray;
		}
		public void setEzStringArray(String[] ezStringArray) {
			this.ezStringArray = ezStringArray;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(ezStringArray);
			result = prime * result
					+ ((innerbean == null) ? 0 : innerbean.hashCode());
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			InnerBean other = (InnerBean) obj;
			if (!Arrays.equals(ezStringArray, other.ezStringArray))
				return false;
			if (innerbean == null) {
				if (other.innerbean != null)
					return false;
			} else if (!innerbean.equals(other.innerbean))
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "InnerBean [innerbean=" + innerbean + ", ezStringArray="
					+ Arrays.toString(ezStringArray) + "]";
		}
		
	}

	@Override
	public String toString() {
		return "TestComplicatedBean [notSoEzMap=" + notSoEzMap
				+ ", innerBeans=" + innerBeans + ", ez=" + ez + ", ezByte="
				+ ezByte + ", ezArray=" + Arrays.toString(ezArray) + "]";
	}

	public String getEz() {
		return ez;
	}

	public void setEz(String ez) {
		this.ez = ez;
	}

	public byte getEzByte() {
		return ezByte;
	}

	public void setEzByte(byte ezByte) {
		this.ezByte = ezByte;
	}

	public int[] getEzArray() {
		return ezArray;
	}

	public void setEzArray(int[] ezArray) {
		this.ezArray = ezArray;
	}

}
