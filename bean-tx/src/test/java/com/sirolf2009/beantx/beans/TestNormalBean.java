package com.sirolf2009.beantx.beans;

public class TestNormalBean {
	
	private String name;
	private TestBean bean1;
	private TestBean bean2;
	
	public static TestNormalBean getDefaultBean() {
		TestNormalBean bean = new TestNormalBean();
		bean.setName("Normal Bean");
		bean.setBean1(TestBean.GetDefaultBean());
		bean.setBean2(TestBean.GetDefaultBean());
		return bean;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TestBean getBean1() {
		return bean1;
	}

	public void setBean1(TestBean bean1) {
		this.bean1 = bean1;
	}

	public TestBean getBean2() {
		return bean2;
	}

	public void setBean2(TestBean bean2) {
		this.bean2 = bean2;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestNormalBean other = (TestNormalBean) obj;
		if (bean1 == null) {
			if (other.bean1 != null)
				return false;
		} else if (!bean1.equals(other.bean1))
			return false;
		if (bean2 == null) {
			if (other.bean2 != null)
				return false;
		} else if (!bean2.equals(other.bean2))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TestNormalBean [name=" + name + ", bean1=" + bean1 + ", bean2="
				+ bean2 + "]";
	}

}
