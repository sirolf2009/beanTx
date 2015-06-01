package com.sirolf2009.beantx.beans;

import com.sirolf2009.beantx.GraphID;

public class TestNormalBean {
	
	@GraphID private long graphId;
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

	public long getGraphId() {
		return graphId;
	}

	public void setGraphId(long graphId) {
		this.graphId = graphId;
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
	public String toString() {
		return "TestNormalBean [name=" + name + ", bean1=" + bean1 + ", bean2="
				+ bean2 + "]";
	}

}
