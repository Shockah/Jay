package pl.shockah.json;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class JSONList<T> extends ArrayList<T> {
	private static final long serialVersionUID = 1574253633071750087L;
	
	protected final Class<T> clazz;
	
	@SafeVarargs
	@Nonnull
	public static <T> JSONList<T> of(T... values) {
		return of(null, values);
	}
	
	@SafeVarargs
	@Nonnull
	public static <T> JSONList<T> of(Class<T> clazz, T... values) {
		JSONList<T> j = new JSONList<>(clazz);
		j.addAll(Arrays.asList(values));
		return j;
	}
	
	protected static Object prepareObject(Object o) {
		return JSONObject.prepareObject(o);
	}
	
	@SuppressWarnings("unchecked")
	protected static <T> List<T> prepareObjects(Collection<?> c) {
		List<T> ret = new ArrayList<>();
		for (Object o : c)
			ret.add((T)prepareObject(o));
		return ret;
	}
	
	public JSONList() {
		this((Class<T>)null);
	}
	
	public JSONList(List<T> list) {
		this(null, list);
	}
	
	public JSONList(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}
	
	public JSONList(Class<T> clazz, List<T> list) {
		super();
		this.clazz = clazz;
		addAll(list);
	}
	
	@Override
	public String toString() {
		return new JSONPrettyPrinter().toString(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T get(int index) {
		if (clazz != null) {
			if (clazz == Integer.class)
				return (T)(Integer)getInt(index);
			else if (clazz == Long.class)
				return (T)(Long)getLong(index);
			else if (clazz == Float.class)
				return (T)(Float)getFloat(index);
			else if (clazz == Double.class)
				return (T)(Double)getDouble(index);
		}
		return super.get(index);
	}
	
	public boolean isNull(int index) {
		return get(index) == null;
	}
	
	public boolean getBool(int index) {
		Object o = get(index);
		if (o instanceof Boolean)
			return (Boolean)o;
		throw new ClassCastException();
	}

	@Nullable
	public Boolean getOptionalBool(int index) {
		return isNull(index) ? null : getBool(index);
	}

	@Nonnull
	public BigInteger getBigInt(int index) {
		Object o = get(index);
		if (o instanceof BigInteger)
			return (BigInteger)o;
		throw new ClassCastException();
	}

	@Nullable
	public BigInteger getOptionalBigInt(int index) {
		return isNull(index) ? null : getBigInt(index);
	}
	
	public int getInt(int index) {
		return getBigInt(index).intValueExact();
	}

	@Nullable
	public Integer getOptionalInt(int index) {
		return isNull(index) ? null : getInt(index);
	}
	
	public long getLong(int index) {
		return getBigInt(index).longValueExact();
	}

	@Nullable
	public Long getOptionalLong(int index) {
		return isNull(index) ? null : getLong(index);
	}

	@Nonnull
	public BigDecimal getBigDecimal(int index) {
		Object o = get(index);
		if (o instanceof BigDecimal)
			return (BigDecimal)o;
		throw new ClassCastException();
	}

	@Nullable
	public BigDecimal getOptionalBigDecimal(int index) {
		return isNull(index) ? null : getBigDecimal(index);
	}
	
	public float getFloat(int index) {
		return getBigDecimal(index).floatValue();
	}

	@Nullable
	public Float getOptionalFloat(int index) {
		return isNull(index) ? null : getFloat(index);
	}
	
	public double getDouble(int index) {
		return getBigDecimal(index).doubleValue();
	}

	@Nullable
	public Double getOptionalDouble(int index) {
		return isNull(index) ? null : getDouble(index);
	}

	@Nonnull
	public String getString(int index) {
		Object o = get(index);
		if (o instanceof String)
			return (String)o;
		throw new ClassCastException();
	}

	@Nullable
	public String getOptionalString(int index) {
		return isNull(index) ? null : getString(index);
	}

	@Nonnull
	public JSONObject getObject(int index) {
		Object o = get(index);
		if (o instanceof JSONObject)
			return (JSONObject)o;
		throw new ClassCastException();
	}

	@Nullable
	public JSONObject getOptionalObject(int index) {
		return isNull(index) ? null : getObject(index);
	}

	@Nonnull
	public JSONList<?> getList(int index) {
		Object o = get(index);
		if (o instanceof JSONList<?>)
			return (JSONList<?>)o;
		throw new ClassCastException();
	}

	@Nullable
	public JSONList<?> getOptionalList(int index) {
		return isNull(index) ? null : getList(index);
	}

	@Nonnull
	public JSONList<Boolean> ofBooleans() {
		JSONList<Boolean> j = new JSONList<>();
		for (int i = 0; i < size(); i++)
			j.add(getBool(i));
		return j;
	}

	@Nonnull
	public JSONList<BigInteger> ofBigInts() {
		JSONList<BigInteger> j = new JSONList<>();
		for (int i = 0; i < size(); i++)
			j.add(getBigInt(i));
		return j;
	}

	@Nonnull
	public JSONList<Integer> ofInts() {
		JSONList<Integer> j = new JSONList<>(Integer.class);
		for (int i = 0; i < size(); i++)
			j.add(getInt(i));
		return j;
	}

	@Nonnull
	public JSONList<Long> ofLongs() {
		JSONList<Long> j = new JSONList<>(Long.class);
		for (int i = 0; i < size(); i++)
			j.add(getLong(i));
		return j;
	}

	@Nonnull
	public JSONList<BigDecimal> ofBigDecimals() {
		JSONList<BigDecimal> j = new JSONList<>();
		for (int i = 0; i < size(); i++)
			j.add(getBigDecimal(i));
		return j;
	}

	@Nonnull
	public JSONList<Float> ofFloats() {
		JSONList<Float> j = new JSONList<>(Float.class);
		for (int i = 0; i < size(); i++)
			j.add(getFloat(i));
		return j;
	}

	@Nonnull
	public JSONList<Double> ofDoubles() {
		JSONList<Double> j = new JSONList<>(Double.class);
		for (int i = 0; i < size(); i++)
			j.add(getDouble(i));
		return j;
	}

	@Nonnull
	public JSONList<String> ofStrings() {
		JSONList<String> j = new JSONList<>();
		for (int i = 0; i < size(); i++)
			j.add(getString(i));
		return j;
	}

	@Nonnull
	public JSONList<JSONObject> ofObjects() {
		JSONList<JSONObject> j = new JSONList<>();
		for (int i = 0; i < size(); i++)
			j.add(getObject(i));
		return j;
	}

	@Nonnull
	public JSONList<JSONList<?>> ofLists() {
		JSONList<JSONList<?>> j = new JSONList<>();
		for (int i = 0; i < size(); i++)
			j.add(getList(i));
		return j;
	}
	
	@SuppressWarnings("unchecked")
	@Nonnull
	public JSONObject addNewObject() {
		JSONObject j = new JSONObject();
		add((T)j);
		return j;
	}
	
	@SuppressWarnings("unchecked")
	@Nonnull
	public JSONList<?> addNewList() {
		JSONList<Object> j = new JSONList<>();
		add((T)j);
		return j;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean add(T e) {
		return super.add((T)prepareObject(e));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void add(int index, T element) {
		super.add(index, (T)prepareObject(element));
	}
	
	@Override
	public boolean addAll(Collection<? extends T> c) {
		return super.addAll(prepareObjects(c));
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return super.addAll(index, prepareObjects(c));
	}
}