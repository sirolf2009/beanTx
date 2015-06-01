package com.sirolf2009.beantx;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;

public class BeanUtil {
	
	public static Map<String, Map<String, Object>> describe(Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Map<String, Map<String, Object>> descriptions = new HashMap<String, Map<String,Object>>();

		Map<String, Object> properties = PropertyUtils.describe(bean);
		properties.put("class", ((Class<?>) properties.get("class")).getName());
		Map<String, Object> primitives = new HashMap<String, Object>();
		Map<String, Object> objects = new HashMap<String, Object>();
		for(Entry<String, Object> entry : properties.entrySet()) {
			if(entry.getValue() != null) {
				if(BeanUtil.isPrimitive(entry.getValue())) {
					try {
						boolean modified = false;
						if(!bean.getClass().getField(entry.getKey()).isAccessible()) {
							bean.getClass().getField(entry.getKey()).setAccessible(true);
							modified = true;
						}
						if(bean.getClass().getField(entry.getKey()).isAnnotationPresent(GraphID.class)) {
							continue;
						}
						if(modified) {
							bean.getClass().getField(entry.getKey()).setAccessible(false);
						}
					} catch (NoSuchFieldException e) {}
					primitives.put(entry.getKey(), entry.getValue());
				} else {
					objects.put(entry.getKey(), entry.getValue());
				}
			}
		}

		descriptions.put(BeanTx.PRIMITIVES, primitives);
		descriptions.put(BeanTx.OBJECTS, objects);
		return descriptions;
	}
	
	public static long pushMap(BeanTx beanTx, Map<String, Object> props, String... labels) {
		Result result;
		long id;
		try(Transaction tx = beanTx.getService().beginTx()) {
			result = beanTx.getService().execute(generateCreateQuery(props, labels), props);
			id = (Long) result.columnAs("id(n)").next();
			tx.success();
		}
		return id;
	}

	public static String generateCreateQuery(Map<String, Object> props, String... labels) {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("CREATE (n%s {", combineLabels(labels)));
		for(String key : props.keySet()) {
			builder.append(String.format(" %s: {%s},", key, key));
		}
		builder.deleteCharAt(builder.length()-1);
		builder.append("}) RETURN id(n)");
		return builder.toString();
	}

	public static String generateDeleteQuery(Map<String, Object> props, String... labels) {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("MATCH (n%s {", combineLabels(labels)));
		for(String key : props.keySet()) {
			builder.append(String.format(" %s: {%s},", key, key));
		}
		builder.deleteCharAt(builder.length()-1);
		builder.append("}) OPTIONAL MATCH (n)-[r]-() DELETE n, r");
		return builder.toString();
	}

	public static String generateUpdateQuery(Map<String, Object> props, String... labels) {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("MATCH (n%s {", combineLabels(labels)));
		for(String key : props.keySet()) {
			builder.append(String.format(" %s: {%s},", key, key));
		}
		builder.deleteCharAt(builder.length()-1);
		builder.append("}) OPTIONAL MATCH (n)-[r]-() DELETE n, r");
		return builder.toString();
	}
	
	public static String combineLabels(String... labels) {
		if(labels.length > 0) {
			return ":"+Arrays.toString(labels).replace("[", "").replace("]", "").replace(", ", ":");
		}
		return "";
	}

	public static boolean isPrimitive(Object object) {
		return object != null && isPrimitive(object.getClass());
	}

	public static boolean isPrimitive(Class<?> clazz) {
		if (clazz.isPrimitive() || clazz.isArray()) {
			return true;
		} else if (clazz == Byte.class
				|| clazz == Short.class
				|| clazz == Integer.class
				|| clazz == Long.class
				|| clazz == Float.class
				|| clazz == Double.class
				|| clazz == Boolean.class
				|| clazz == Character.class
				|| clazz == String.class) { //Strings are technically not primitive, but Neo4J accepts them
			return true;
		} else {
			return false;
		}
	}

}
