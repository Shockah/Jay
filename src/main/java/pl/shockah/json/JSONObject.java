package pl.shockah.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import pl.shockah.util.func.Action1;

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
		else if (o instanceof Boolean || o instanceof BigInteger || o instanceof BigDecimal
			|| o instanceof String || o instanceof JSONObject || o instanceof JSONList<?>)
			return o;
		else if (o instanceof Integer)
			return BigInteger.valueOf((Integer)o);
		else if (o instanceof Long)
			return BigInteger.valueOf((Long)o);
		else if (o instanceof Float)
			return BigDecimal.valueOf((Float)o);
		else if (o instanceof Double)
			return BigDecimal.valueOf((Double)o);
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
	
	public Boolean getOptionalBool(String key) {
		return containsKey(key) && !isNull(key) ? getBool(key) : null;
	}
	
	public void onBool(String key, Action1<Boolean> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getBool(key));
	}
	
	public BigInteger getBigInt(String key) {
		if (!containsKey(key))
			throw new NullPointerException();
		Object o = get(key);
		if (o instanceof BigInteger)
			return (BigInteger)o;
		throw new ClassCastException();
	}
	
	public BigInteger getBigInt(String key, BigInteger def) {
		return containsKey(key) ? getBigInt(key) : def;
	}
	
	public BigInteger getOptionalBigInt(String key) {
		return containsKey(key) && !isNull(key) ? getBigInt(key) : null;
	}
	
	public void onBigInt(String key, Action1<BigInteger> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getBigInt(key));
	}
	
	public int getInt(String key) {
		return getBigInt(key).intValueExact();
	}
	
	public int getInt(String key, int def) {
		return containsKey(key) ? getInt(key) : def;
	}
	
	public Integer getOptionalInt(String key) {
		return containsKey(key) && !isNull(key) ? getInt(key) : null;
	}
	
	public void onInt(String key, Action1<Integer> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getInt(key));
	}
	
	public long getLong(String key) {
		return getBigInt(key).longValueExact();
	}
	
	public long getLong(String key, long def) {
		return containsKey(key) ? getLong(key) : def;
	}
	
	public Long getOptionalLong(String key) {
		return containsKey(key) && !isNull(key) ? getLong(key) : null;
	}
	
	public void onLong(String key, Action1<Long> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getLong(key));
	}
	
	public BigDecimal getBigDecimal(String key) {
		if (!containsKey(key))
			throw new NullPointerException();
		Object o = get(key);
		if (o instanceof BigInteger)
			return (BigDecimal)o;
		throw new ClassCastException();
	}
	
	public BigDecimal getBigDecimal(String key, BigDecimal def) {
		return containsKey(key) ? getBigDecimal(key) : def;
	}
	
	public BigDecimal getOptionalBigDecimal(String key) {
		return containsKey(key) && !isNull(key) ? getBigDecimal(key) : null;
	}
	
	public void onBigDecimal(String key, Action1<BigDecimal> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getBigDecimal(key));
	}
	
	public float getFloat(String key) {
		return getBigDecimal(key).floatValue();
	}
	
	public float getFloat(String key, float def) {
		return containsKey(key) ? getFloat(key) : def;
	}
	
	public Float getOptionalFloat(String key) {
		return containsKey(key) && !isNull(key) ? getFloat(key) : null;
	}
	
	public void onFloat(String key, Action1<Float> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getFloat(key));
	}
	
	public double getDouble(String key) {
		return getBigDecimal(key).doubleValue();
	}
	
	public double getDouble(String key, double def) {
		return containsKey(key) ? getDouble(key) : def;
	}
	
	public Double getOptionalDouble(String key) {
		return containsKey(key) && !isNull(key) ? getDouble(key) : null;
	}
	
	public void onDouble(String key, Action1<Double> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getDouble(key));
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
	
	public String getOptionalString(String key) {
		return containsKey(key) && !isNull(key) ? getString(key) : null;
	}
	
	public void onString(String key, Action1<String> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getString(key));
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
	
	public JSONObject getObjectOrNew(String key) {
		if (containsKey(key)) {
			return getObject(key);
		} else {
			JSONObject j = new JSONObject();
			put(key, j);
			return j;
		}
	}
	
	public JSONObject getOptionalObject(String key) {
		return containsKey(key) && !isNull(key) ? getObject(key) : null;
	}
	
	public void onObject(String key, Action1<JSONObject> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getObject(key));
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
	
	public JSONList<?> getListOrNew(String key) {
		if (containsKey(key)) {
			return getList(key);
		} else {
			JSONList<?> j = new JSONList<>();
			put(key, j);
			return j;
		}
	}
	
	public JSONList<?> getOptionalList(String key) {
		return containsKey(key) && !isNull(key) ? getList(key) : null;
	}
	
	public void onList(String key, Action1<JSONList<?>> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getList(key));
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