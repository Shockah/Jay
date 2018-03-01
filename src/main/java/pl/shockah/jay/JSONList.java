package pl.shockah.jay;

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
	
	@Nullable protected final Class<T> clazz;
	
	@SafeVarargs
	@Nonnull
	public static <T> JSONList<T> of(T... values) {
		JSONList<T> j = new JSONList<>();
		j.addAll(Arrays.asList(values));
		return j;
	}
	
	@SafeVarargs
	@Nonnull
	public static <T> JSONList<T> of(@Nonnull Class<T> clazz, T... values) {
		JSONList<T> j = new JSONList<>(clazz);
		j.addAll(Arrays.asList(values));
		return j;
	}

	@Nullable
	protected static Object prepareObject(@Nullable Object o) {
		return JSONObject.prepareObject(o);
	}
	
	@SuppressWarnings("unchecked")
	@Nonnull
	protected static <T> List<T> prepareObjects(@Nonnull Collection<?> c) {
		List<T> ret = new ArrayList<>();
		for (Object o : c)
			ret.add((T)prepareObject(o));
		return ret;
	}
	
	public JSONList() {
		super();
		this.clazz = null;
	}
	
	public JSONList(@Nonnull List<T> list) {
		this();
		addAll(list);
	}
	
	public JSONList(@Nonnull Class<T> clazz) {
		super();
		this.clazz = clazz;
	}
	
	public JSONList(@Nonnull Class<T> clazz, @Nonnull List<T> list) {
		this(clazz);
		addAll(list);
	}
	
	@Override
	@Nonnull
	public String toString() {
		return new JSONPrettyPrinter().toString(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Nullable
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

	public boolean isBool(int index) {
		Object o = super.get(index);
		return o instanceof Boolean;
	}
	
	public boolean getBool(int index) {
		Object o = super.get(index);
		if (o instanceof Boolean)
			return (Boolean)o;
		throw new ClassCastException();
	}

	@Nullable
	public Boolean getOptionalBool(int index) {
		return isNull(index) ? null : getBool(index);
	}

	public boolean isInteger(int index) {
		Object o = super.get(index);
		return o instanceof BigInteger;
	}

	@Nonnull
	public BigInteger getBigInt(int index) {
		Object o = super.get(index);
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

	public boolean isDecimal(int index) {
		Object o = super.get(index);
		return o instanceof BigDecimal;
	}

	@Nonnull
	public BigDecimal getBigDecimal(int index) {
		Object o = super.get(index);
		if (o instanceof BigDecimal)
			return (BigDecimal)o;
		else if (o instanceof BigInteger)
			return new BigDecimal((BigInteger)o);
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

	public boolean isString(int index) {
		Object o = super.get(index);
		return o instanceof String;
	}

	@Nonnull
	public String getString(int index) {
		Object o = super.get(index);
		if (o instanceof String)
			return (String)o;
		throw new ClassCastException();
	}

	@Nullable
	public String getOptionalString(int index) {
		return isNull(index) ? null : getString(index);
	}

	public boolean isObject(int index) {
		Object o = super.get(index);
		return o instanceof JSONObject;
	}

	@Nonnull
	public JSONObject getObject(int index) {
		Object o = super.get(index);
		if (o instanceof JSONObject)
			return (JSONObject)o;
		throw new ClassCastException();
	}

	@Nullable
	public JSONObject getOptionalObject(int index) {
		return isNull(index) ? null : getObject(index);
	}

	public boolean isList(int index) {
		Object o = super.get(index);
		return o instanceof JSONList<?>;
	}

	@Nonnull
	public JSONList<?> getList(int index) {
		Object o = super.get(index);
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
	public boolean add(@Nullable T e) {
		return super.add((T)prepareObject(e));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void add(int index, @Nullable T element) {
		super.add(index, (T)prepareObject(element));
	}
	
	@Override
	public boolean addAll(@Nonnull Collection<? extends T> c) {
		return super.addAll(prepareObjects(c));
	}
	
	@Override
	public boolean addAll(int index, @Nonnull Collection<? extends T> c) {
		return super.addAll(index, prepareObjects(c));
	}

	public boolean isNumber(int index) {
		return isInteger(index) || isDecimal(index);
	}
}