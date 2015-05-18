package com.sirolf2009.beantx;

import static com.sirolf2009.beantx.BeanUtil.describe;
import static com.sirolf2009.beantx.BeanUtil.isPrimitive;
import static com.sirolf2009.beantx.BeanUtil.pushMap;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

public class BeanTx {

	private GraphDatabaseService service;
	private BeanCache cache;
	protected static final String PROPS = "props";
	protected static final String PRIMITIVES = "primitives";
	protected static final String OBJECTS = "objects";

	public BeanTx(GraphDatabaseService service) {
		setService(service);
		setCache(new BeanCache());
	}

	public long pushBean(Object bean, String... labels) {
		try {
			Map<String, Map<String, Object>> description = describe(bean);
			Map<String, Object> props = new HashMap<String, Object>();
			props.put(PROPS, description.get(PRIMITIVES));
			long id = pushMap(this, description.get(PRIMITIVES), labels);
			try(Transaction tx = getService().beginTx()){
				Node parent = getService().getNodeById(id);
				for(Entry<String, Object> entry : description.get(OBJECTS).entrySet()) {
					long childID = pushBean(entry.getValue(), labels);
					parent.createRelationshipTo(getService().getNodeById(childID), DynamicRelationshipType.withName(entry.getKey()));
				}
				tx.success();
			}
			getCache().cacheBeanID(id, bean);
			return id;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public Object pullBean(long id) {
		try {
			Object bean = getCache().getCachedBeanFromID(id);
			if(bean != null) {
				return bean;
			}
			Node node;
			try(Transaction tx = getService().beginTx()) {
				node = getService().getNodeById(id);
				bean = Class.forName((String) node.getProperty("class")).newInstance();
				tx.success();
			}
			//Populate primitives
			try(Transaction tx = getService().beginTx()) {
				for(String key : node.getPropertyKeys()) {
					if(!key.equals("class")) {
						try {
							PropertyUtils.setProperty(bean, key, node.getProperty(key));
						} catch(NoSuchMethodException e) {}
					}
				}
				tx.success();
			}

			//Populate objects
			try {
				for(Entry<String, Object> entry : PropertyUtils.describe(bean).entrySet()) {
					if(!isPrimitive(entry.getValue()) && !entry.getKey().equals("class")) {
						try(Transaction tx = getService().beginTx()) {
							Relationship rel = node.getRelationships(DynamicRelationshipType.withName(entry.getKey()), Direction.OUTGOING).iterator().next();
							PropertyUtils.setProperty(bean, entry.getKey(), pullBean(rel.getEndNode().getId()));
							tx.success();
						}
					}
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			getCache().cacheBeanID(id, bean);
			return bean;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {}
		return null;
	}

	public void deleteBean(Object bean) {
		long ID = getCache().getIDFromBean(bean);
		try(Transaction tx = getService().beginTx()) {
			getService().getNodeById(ID).delete();
			getCache().removeIDFromCache(ID);
			tx.success();
		}
	}
	
	public void updateBean(Object bean) {
		long ID = getCache().getIDFromBean(bean);
		try(Transaction tx = getService().beginTx()) {
			Node node = getService().getNodeById(ID);
			try {
				Map<String, Map<String, Object>> description = describe(bean);
				for(Entry<String, Object> entry : description.get(PRIMITIVES).entrySet()) {
					node.setProperty(entry.getKey(), entry.getValue());
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			getCache().cacheBeanID(ID, bean);
			tx.success();
		}
	}

	public GraphDatabaseService getService() {
		return service;
	}

	public void setService(GraphDatabaseService service) {
		this.service = service;
	}

	public BeanCache getCache() {
		return cache;
	}

	public void setCache(BeanCache cache) {
		this.cache = cache;
	}

}
