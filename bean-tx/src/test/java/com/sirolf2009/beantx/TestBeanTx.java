package com.sirolf2009.beantx;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import com.sirolf2009.beantx.beans.TestBean;

public class TestBeanTx {
	
	private GraphDatabaseService graph;
	private BeanTx beanTx;

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
	public void testCRUD() {
		CRUD("This", "is", "a", "test", "bean");
		CRUD("This", "is", "also", "a", "test", "bean");
	}
	
	public void CRUD(String... labels) {
		TestBean testBean = TestBean.GetDefaultBean();
		long id = beanTx.pushBean(testBean, labels);
		TestBean pulledBean = (TestBean) beanTx.pullBean(id);
		assertEquals(testBean, pulledBean);
		assertEquals(id, beanTx.getCache().getIDFromBean(testBean));
		assertEquals(id, beanTx.getCache().getIDFromBean(pulledBean));
		assertEquals(id, testBean.graphID);
		assertEquals(id, pulledBean.graphID);
		
		testBean.setEz("Mom says i'm special!");
		beanTx.updateBean(testBean);
		assertEquals(testBean, beanTx.pullBean(id));
		
		beanTx.deleteBean(testBean);
		assertEquals(null, beanTx.pullBean(id));
		assertEquals(-1, beanTx.getCache().getIDFromBean(testBean));
		assertEquals(-1, beanTx.getCache().getIDFromBean(pulledBean));
		assertEquals(-1, beanTx.getCache().getIDFromBean(testBean.graphID));
		assertEquals(-1, beanTx.getCache().getIDFromBean(pulledBean.graphID));
	}

}
