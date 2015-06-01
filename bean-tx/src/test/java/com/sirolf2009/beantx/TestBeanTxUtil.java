package com.sirolf2009.beantx;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBeanTxUtil {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCombineLabels() {
		assertEquals(":BeanTx:is:KEWL", BeanUtil.combineLabels("BeanTx", "is", "KEWL"));
	}

	@Test
	public void testGenerateQuery() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("name", "sirolf2009");
		props.put("age", 19);
		props.put("legs", new String[] {"left", "right"});
		
		assertEquals("MERGE (n:testNode:testLabel { age: {age}, name: {name}, legs: {legs}}) RETURN id(n)", BeanUtil.generateCreateQuery(props, "testNode", "testLabel"));
	}

}
