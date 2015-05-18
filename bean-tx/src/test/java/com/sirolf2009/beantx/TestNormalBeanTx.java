package com.sirolf2009.beantx;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import com.sirolf2009.beantx.beans.TestNormalBean;

public class TestNormalBeanTx {
	
	private GraphDatabaseService graph;
	private BeanTx beanTx;
	private long id;
	
	@Before
	public void setUp() throws Exception {
		graph = new GraphDatabaseFactory().newEmbeddedDatabase("test-db");
		beanTx = new BeanTx(graph);
	}

	@After
	public void tearDown() throws Exception {
		graph.shutdown();
		FileUtils.deleteDirectory(new File("test-db"));
	}

	@Test
	public void test() {
		TestNormalBean bean = TestNormalBean.getDefaultBean();
		id = beanTx.pushBean(bean);
		Assert.assertNotEquals(-1, id);
		TestNormalBean pulled = (TestNormalBean) beanTx.pullBean(id);
		Assert.assertEquals(bean, pulled);
	}

}
