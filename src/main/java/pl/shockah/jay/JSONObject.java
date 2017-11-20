package pl.shockah.jay;

import pl.shockah.util.func.Action0;
import pl.shockah.util.func.Action1;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class JSONObject extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = -8548703026353148866L;

	@Nonnull
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
	@Nullable
	protected static Object prepareObject(@Nullable Object o) {
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
			return new JSONList<>((List<Object>) o);
		else
			throw new ClassCastException();
	}

	@Nonnull
	protected static Map<String, Object> prepareObjects(@Nonnull Map<? extends String, ?> map) {
		Map<String, Object> ret = new TreeMap<>();
		for (Map.Entry<? extends String, ?> entry : map.entrySet())
			ret.put(entry.getKey(), prepareObject(entry.getValue()));
		return ret;
	}
	
	@Nullable protected Map<String, String> comments;
	
	public JSONObject() {
		super();
	}
	
	public JSONObject(@Nonnull Map<String, Object> map) {
		super();
		putAll(map);
	}
	
	@Override
	@Nonnull
	public String toString() {
		return new JSONPrettyPrinter().toString(this);
	}
	
	public boolean isNull(@Nonnull String key) {
		if (!containsKey(key))
			throw new NullPointerException();
		return get(key) == null;
	}
	
	public boolean getBool(@Nonnull String key) {
		if (!containsKey(key))
			throw new NullPointerException();
		Object o = get(key);
		if (o instanceof Boolean)
			return (Boolean)o;
		throw new ClassCastException();
	}
	
	public boolean getBool(@Nonnull String key, boolean def) {
		return containsKey(key) ? getBool(key) : def;
	}

	@Nullable
	public Boolean getOptionalBool(@Nonnull String key) {
		return containsKey(key) && !isNull(key) ? getBool(key) : null;
	}
	
	public void onBool(@Nonnull String key, @Nonnull Action1<Boolean> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getBool(key));
	}

	public void onBool(@Nonnull String key, @Nonnull Action1<Boolean> f, @Nonnull Action0 orElse) {
		if (containsKey(key) && !isNull(key))
			f.call(getBool(key));
		else
			orElse.call();
	}

	@Nonnull
	public BigInteger getBigInt(@Nonnull String key) {
		if (!containsKey(key))
			throw new NullPointerException();
		Object o = get(key);
		if (o instanceof BigInteger)
			return (BigInteger)o;
		throw new ClassCastException();
	}

	@Nonnull
	public BigInteger getBigInt(@Nonnull String key, @Nonnull BigInteger def) {
		return containsKey(key) ? getBigInt(key) : def;
	}

	@Nullable
	public BigInteger getOptionalBigInt(@Nonnull String key) {
		return containsKey(key) && !isNull(key) ? getBigInt(key) : null;
	}
	
	public void onBigInt(@Nonnull String key, @Nonnull Action1<BigInteger> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getBigInt(key));
	}

	public void onBigInt(@Nonnull String key, @Nonnull Action1<BigInteger> f, @Nonnull Action0 orElse) {
		if (containsKey(key) && !isNull(key))
			f.call(getBigInt(key));
		else
			orElse.call();
	}
	
	public int getInt(@Nonnull String key) {
		return getBigInt(key).intValueExact();
	}
	
	public int getInt(@Nonnull String key, int def) {
		return containsKey(key) ? getInt(key) : def;
	}

	@Nullable
	public Integer getOptionalInt(@Nonnull String key) {
		return containsKey(key) && !isNull(key) ? getInt(key) : null;
	}
	
	public void onInt(@Nonnull String key, @Nonnull Action1<Integer> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getInt(key));
	}

	public void onInt(@Nonnull String key, @Nonnull Action1<Integer> f, @Nonnull Action0 orElse) {
		if (containsKey(key) && !isNull(key))
			f.call(getInt(key));
		else
			orElse.call();
	}
	
	public long getLong(@Nonnull String key) {
		return getBigInt(key).longValueExact();
	}
	
	public long getLong(@Nonnull String key, long def) {
		return containsKey(key) ? getLong(key) : def;
	}

	@Nullable
	public Long getOptionalLong(@Nonnull String key) {
		return containsKey(key) && !isNull(key) ? getLong(key) : null;
	}
	
	public void onLong(@Nonnull String key, @Nonnull Action1<Long> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getLong(key));
	}

	public void onLong(@Nonnull String key, @Nonnull Action1<Long> f, @Nonnull Action0 orElse) {
		if (containsKey(key) && !isNull(key))
			f.call(getLong(key));
		else
			orElse.call();
	}

	@Nonnull
	public BigDecimal getBigDecimal(@Nonnull String key) {
		if (!containsKey(key))
			throw new NullPointerException();
		Object o = get(key);
		if (o instanceof BigDecimal)
			return (BigDecimal)o;
		throw new ClassCastException();
	}

	@Nonnull
	public BigDecimal getBigDecimal(@Nonnull String key, @Nonnull BigDecimal def) {
		return containsKey(key) ? getBigDecimal(key) : def;
	}

	@Nullable
	public BigDecimal getOptionalBigDecimal(@Nonnull String key) {
		return containsKey(key) && !isNull(key) ? getBigDecimal(key) : null;
	}
	
	public void onBigDecimal(@Nonnull String key, @Nonnull Action1<BigDecimal> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getBigDecimal(key));
	}

	public void onBigDecimal(@Nonnull String key, @Nonnull Action1<BigDecimal> f, @Nonnull Action0 orElse) {
		if (containsKey(key) && !isNull(key))
			f.call(getBigDecimal(key));
		else
			orElse.call();
	}
	
	public float getFloat(@Nonnull String key) {
		return getBigDecimal(key).floatValue();
	}
	
	public float getFloat(@Nonnull String key, float def) {
		return containsKey(key) ? getFloat(key) : def;
	}

	@Nullable
	public Float getOptionalFloat(@Nonnull String key) {
		return containsKey(key) && !isNull(key) ? getFloat(key) : null;
	}
	
	public void onFloat(@Nonnull String key, @Nonnull Action1<Float> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getFloat(key));
	}

	public void onFloat(@Nonnull String key, @Nonnull Action1<Float> f, @Nonnull Action0 orElse) {
		if (containsKey(key) && !isNull(key))
			f.call(getFloat(key));
		else
			orElse.call();
	}
	
	public double getDouble(@Nonnull String key) {
		return getBigDecimal(key).doubleValue();
	}
	
	public double getDouble(@Nonnull String key, double def) {
		return containsKey(key) ? getDouble(key) : def;
	}

	@Nullable
	public Double getOptionalDouble(@Nonnull String key) {
		return containsKey(key) && !isNull(key) ? getDouble(key) : null;
	}
	
	public void onDouble(@Nonnull String key, @Nonnull Action1<Double> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getDouble(key));
	}

	public void onDouble(@Nonnull String key, @Nonnull Action1<Double> f, @Nonnull Action0 orElse) {
		if (containsKey(key) && !isNull(key))
			f.call(getDouble(key));
		else
			orElse.call();
	}

	@Nonnull
	public String getString(@Nonnull String key) {
		if (!containsKey(key))
			throw new NullPointerException();
		Object o = get(key);
		if (o instanceof String)
			return (String)o;
		throw new ClassCastException();
	}

	@Nonnull
	public String getString(@Nonnull String key, @Nonnull String def) {
		return containsKey(key) ? getString(key) : def;
	}

	@Nullable
	public String getOptionalString(@Nonnull String key) {
		return containsKey(key) && !isNull(key) ? getString(key) : null;
	}
	
	public void onString(@Nonnull String key, @Nonnull Action1<String> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getString(key));
	}

	public void onString(@Nonnull String key, @Nonnull Action1<String> f, @Nonnull Action0 orElse) {
		if (containsKey(key) && !isNull(key))
			f.call(getString(key));
		else
			orElse.call();
	}

	@Nonnull
	public JSONObject getObject(@Nonnull String key) {
		if (!containsKey(key))
			throw new NullPointerException();
		Object o = get(key);
		if (o instanceof JSONObject)
			return (JSONObject)o;
		throw new ClassCastException();
	}

	@Nonnull
	public JSONObject getObject(@Nonnull String key, @Nonnull JSONObject def) {
		return containsKey(key) ? getObject(key) : def;
	}

	@Nonnull
	public JSONObject getObjectOrEmpty(@Nonnull String key) {
		return containsKey(key) ? getObject(key) : new JSONObject();
	}

	@Nonnull
	public JSONObject getObjectOrNew(@Nonnull String key) {
		if (containsKey(key)) {
			return getObject(key);
		} else {
			JSONObject j = new JSONObject();
			put(key, j);
			return j;
		}
	}

	@Nullable
	public JSONObject getOptionalObject(@Nonnull String key) {
		return containsKey(key) && !isNull(key) ? getObject(key) : null;
	}
	
	public void onObject(@Nonnull String key, @Nonnull Action1<JSONObject> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getObject(key));
	}

	public void onObject(@Nonnull String key, @Nonnull Action1<JSONObject> f, @Nonnull Action0 orElse) {
		if (containsKey(key) && !isNull(key))
			f.call(getObject(key));
		else
			orElse.call();
	}

	@Nonnull
	public JSONList<?> getList(@Nonnull String key) {
		if (!containsKey(key))
			throw new NullPointerException();
		Object o = get(key);
		if (o instanceof JSONList<?>)
			return (JSONList<?>)o;
		throw new ClassCastException();
	}

	@Nonnull
	public JSONList<?> getList(@Nonnull String key, @Nonnull JSONList<?> def) {
		return containsKey(key) ? getList(key) : def;
	}

	@Nonnull
	public JSONList<?> getListOrEmpty(@Nonnull String key) {
		return containsKey(key) ? getList(key) : new JSONList<>();
	}

	@Nonnull
	public JSONList<?> getListOrNew(@Nonnull String key) {
		if (containsKey(key)) {
			return getList(key);
		} else {
			JSONList<?> j = new JSONList<>();
			put(key, j);
			return j;
		}
	}

	@Nullable
	public JSONList<?> getOptionalList(@Nonnull String key) {
		return containsKey(key) && !isNull(key) ? getList(key) : null;
	}
	
	public void onList(@Nonnull String key, @Nonnull Action1<JSONList<?>> f) {
		if (containsKey(key) && !isNull(key))
			f.call(getList(key));
	}

	public void onList(@Nonnull String key, @Nonnull Action1<JSONList<?>> f, @Nonnull Action0 orElse) {
		if (containsKey(key) && !isNull(key))
			f.call(getList(key));
		else
			orElse.call();
	}
	
	public boolean putDefault(@Nonnull String key, @Nullable Object value) {
		if (containsKey(key)) {
			return false;
		} else {
			put(key, value);
			return true;
		}
	}

	@Nonnull
	public JSONObject putNewObject(@Nonnull String key) {
		JSONObject j = new JSONObject();
		put(key, j);
		return j;
	}

	@Nonnull
	public JSONList<?> putNewList(@Nonnull String key) {
		JSONList<Object> j = new JSONList<>();
		put(key, j);
		return j;
	}
	
	public void clearComments() {
		comments = null;
	}
	
	public void setComment(@Nonnull String key, @Nullable String comment) {
		if (comment == null) {
			if (comments != null && comments.containsKey(key))
				comments.remove(key);
		} else {
			if (comments == null)
				comments = new HashMap<>();
			comments.put(key, comment);
		}
	}

	public String getComment(@Nonnull String key) {
		if (comments == null)
			return null;
		return comments.get(key);
	}
	
	@Override
	public Object put(@Nonnull String key, @Nullable Object value) {
		return super.put(key, prepareObject(value));
	}
	
	@Override
	public void putAll(@Nonnull Map<? extends String, ?> map) {
		super.putAll(prepareObjects(map));
	}
}