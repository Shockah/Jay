package io.shockah.json;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class JSONObject extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = -8548703026353148866L;
	
	@SafeVarargs
	public static JSONObject of(Object... values) {
		if (values.length % 2 != 0)
			throw new IllegalArgumentException();
		JSONObject j = new JSONObject();
		for (int i = 0; i < values.length; i += 2) {
			if (!(values[i] instanceof String))
				throw new IllegalArgumentException();
			j.put((String)values[i], values[i + 1]);
		}
		return j;
	}
	
	@SuppressWarnings("unchecked")
	protected static Object prepareObject(Object o) {
		if (o == null)
			return null;
		else if (o instanceof Boolean || o instanceof Integer || o instanceof Long || o instanceof Double
			|| o instanceof String || o instanceof JSONObject || o instanceof JSONList<?>)
			return o;
		else if (o instanceof Map<?, ?>)
			return new JSONObject((Map<String, Object>)o);
		else if (o instanceof List<?>)
			return new JSONList<Object>((List<Object>)o);
		else
			throw new ClassCastException();
	}
	
	protected static Map<String, Object> prepareObjects(Map<? extends String, ? extends Object> map) {
		Map<String, Object> ret = new TreeMap<>();
		for (Map.Entry<? extends String, ? extends Object> entry : map.entrySet())
			ret.put(entry.getKey(), prepareObject(entry.getValue()));
		return ret;
	}
	
	protected Map<String, String> comments;
	
	public JSONObject() {
		super();
	}
	
	public JSONObject(Map<String, Object> map) {
		super();
		putAll(map);
	}
	
	@Override
	public String toString() {
		return new JSONPrettyPrinter().toString(this);
	}
	
	public boolean isNull(String key) {
		if (!containsKey(key))
			throw new NullPointerException();
		return get(key) == null;
	}
	
	public boolean getBool(String key) {
		if (!containsKey(key))
			throw new NullPointerException();
		Object o = get(key);
		if (o instanceof Boolean)
			return (Boolean)o;
		throw new ClassCastException();
	}
	
	public boolean getBool(String key, boolean def) {
		return containsKey(key) ? getBool(key) : def;
	}
	
	public int getInt(String key) {
		if (!containsKey(key))
			throw new NullPointerException();
		Object o = get(key);
		if (o instanceof Integer)
			return (Integer)o;
		throw new ClassCastException();
	}
	
	public int getInt(String key, int def) {
		return containsKey(key) ? getInt(key) : def;
	}
	
	public long getLong(String key) {
		if (!containsKey(key))
			throw new NullPointerException();
		Object o = get(key);
		if (o instanceof Long)
			return (Long)o;
		if (o instanceof Integer)
			return (Integer)o;
		throw new ClassCastException();
	}
	
	public long getLong(String key, long def) {
		return containsKey(key) ? getLong(key) : def;
	}
	
	public double getDouble(String key) {
		if (!containsKey(key))
			throw new NullPointerException();
		Object o = get(key);
		if (o instanceof Double)
			return (Double)o;
		if (o instanceof Long)
			return (Long)o;
		if (o instanceof Integer)
			return (Integer)o;
		throw new ClassCastException();
	}
	
	public double getDouble(String key, double def) {
		return containsKey(key) ? getDouble(key) : def;
	}
	
	public String getString(String key) {
		if (!containsKey(key))
			throw new NullPointerException();
		Object o = get(key);
		if (o instanceof String)
			return (String)o;
		throw new ClassCastException();
	}
	
	public String getString(String key, String def) {
		return containsKey(key) ? getString(key) : def;
	}
	
	public JSONObject getObject(String key) {
		if (!containsKey(key))
			throw new NullPointerException();
		Object o = get(key);
		if (o instanceof JSONObject)
			return (JSONObject)o;
		throw new ClassCastException();
	}
	
	public JSONObject getObject(String key, JSONObject def) {
		return containsKey(key) ? getObject(key) : def;
	}
	
	public JSONObject getObjectOrEmpty(String key) {
		return containsKey(key) ? getObject(key) : new JSONObject();
	}
	
	public JSONList<?> getList(String key) {
		if (!containsKey(key))
			throw new NullPointerException();
		Object o = get(key);
		if (o instanceof JSONList<?>)
			return (JSONList<?>)o;
		throw new ClassCastException();
	}
	
	public JSONList<?> getList(String key, JSONList<?> def) {
		return containsKey(key) ? getList(key) : def;
	}
	
	public JSONList<?> getListOrEmpty(String key) {
		return containsKey(key) ? getList(key) : new JSONList<Object>();
	}
	
	public boolean putDefault(String key, Object value) {
		if (containsKey(key)) {
			return false;
		} else {
			put(key, value);
			return true;
		}
	}
	
	public JSONObject putNewObject(String key) {
		JSONObject j = new JSONObject();
		put(key, j);
		return j;
	}
	
	public JSONList<?> putNewList(String key) {
		JSONList<Object> j = new JSONList<>();
		put(key, j);
		return j;
	}
	
	public void clearComments() {
		comments = null;
	}
	
	public void setComment(String key, String comment) {
		if (comment == null) {
			if (comments != null && comments.containsKey(key))
				comments.remove(key);
		} else {
			if (comments == null)
				comments = new HashMap<>();
			comments.put(key, comment);
		}
	}
	
	public String getComment(String key) {
		if (comments == null)
			return null;
		return comments.get(key);
	}
	
	@Override
	public Object put(String key, Object value) {
		return super.put(key, prepareObject(value));
	}
	
	@Override
	public void putAll(Map<? extends String, ? extends Object> map) {
		super.putAll(prepareObjects(map));
	}
}