package com.sirolf2009.beantx;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BeanCache {
	
	private Map<Object, Long> indexCache;

	public BeanCache() {
		setIndexCache(new HashMap<Object, Long>());
	}
	
	public void removeIDFromCache(long ID) {
		List<Object> dirty = new ArrayList<Object>();
		for(Entry<Object, Long> entry : getIndexCache().entrySet()) {
			if(entry.getValue().equals(ID)) {
				dirty.add(entry.getKey());
			}
		}
		for(Object obj : dirty) {
			getIndexCache().remove(obj);
			cacheBeanID(-1, obj);
		}
	}
	
	public long getIDFromBean(Object bean) {
		if(getIndexCache().containsKey(bean)) {
			return getIndexCache().get(bean);
		} else {
			return -1;
		}
	}

	public Object getCachedBeanFromID(long ID) {
		for(Entry<Object, Long> entry : getIndexCache().entrySet()) {
			if(entry.getValue() == ID) {
				return entry.getKey();
			}
		}
		return null;
	}

	public void cacheBeanID(long ID, Object bean) {
		getIndexCache().put(bean, ID);
		for(Field field : bean.getClass().getDeclaredFields()) {
			if(field.isAnnotationPresent(GraphID.class)) {
				try {
					boolean modified = false;
					if(!field.isAccessible()) {
						field.setAccessible(true);
						modified = true;
					}
					field.set(bean, ID);
					if(modified) {
						field.setAccessible(false);
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Map<Object, Long> getIndexCache() {
		return indexCache;
	}

	public void setIndexCache(Map<Object, Long> indexCache) {
		this.indexCache = indexCache;
	}

}
