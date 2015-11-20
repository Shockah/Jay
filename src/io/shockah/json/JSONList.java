package io.shockah.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JSONList<T> extends ArrayList<T> {
	private static final long serialVersionUID = 1574253633071750087L;
	
	@SafeVarargs
	public static <T> JSONList<T> of(T... values) {
		JSONList<T> j = new JSONList<>();
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
		super();
	}
	
	public JSONList(List<T> list) {
		super();
		addAll(list);
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
	
	public int getInt(int index) {
		Object o = get(index);
		if (o instanceof Integer)
			return (Integer)o;
		if (o instanceof Boolean)
			return (Boolean)o ? 1 : 0;
		throw new ClassCastException();
	}
	
	public long getLong(int index) {
		Object o = get(index);
		if (o instanceof Long)
			return (Long)o;
		if (o instanceof Integer)
			return (Integer)o;
		if (o instanceof Boolean)
			return (Boolean)o ? 1 : 0;
		throw new ClassCastException();
	}
	
	public double getDouble(int index) {
		Object o = get(index);
		if (o instanceof Double)
			return (Double)o;
		if (o instanceof Long)
			return (Long)o;
		if (o instanceof Integer)
			return (Integer)o;
		if (o instanceof Boolean)
			return (Boolean)o ? 1 : 0;
		throw new ClassCastException();
	}
	
	public String getString(int index) {
		Object o = get(index);
		if (o instanceof String)
			return (String)o;
		throw new ClassCastException();
	}
	
	public JSONObject getObject(int index) {
		Object o = get(index);
		if (o instanceof JSONObject)
			return (JSONObject)o;
		throw new ClassCastException();
	}
	
	public JSONList<?> getList(int index) {
		Object o = get(index);
		if (o instanceof JSONList<?>)
			return (JSONList<?>)o;
		throw new ClassCastException();
	}
	
	public JSONList<Boolean> ofBooleans() {
		JSONList<Boolean> j = new JSONList<>();
		for (int i = 0; i < size(); i++)
			j.add(getBool(i));
		return j;
	}
	
	public JSONList<Integer> ofInts() {
		JSONList<Integer> j = new JSONList<>();
		for (int i = 0; i < size(); i++)
			j.add(getInt(i));
		return j;
	}
	
	public JSONList<Long> ofLongs() {
		JSONList<Long> j = new JSONList<>();
		for (int i = 0; i < size(); i++)
			j.add(getLong(i));
		return j;
	}
	
	public JSONList<String> ofStrings() {
		JSONList<String> j = new JSONList<>();
		for (int i = 0; i < size(); i++)
			j.add(getString(i));
		return j;
	}
	
	public JSONList<JSONObject> ofObjects() {
		JSONList<JSONObject> j = new JSONList<>();
		for (int i = 0; i < size(); i++)
			j.add(getObject(i));
		return j;
	}
	
	public JSONList<JSONList<?>> ofLists() {
		JSONList<JSONList<?>> j = new JSONList<>();
		for (int i = 0; i < size(); i++)
			j.add(getList(i));
		return j;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject addNewObject() {
		JSONObject j = new JSONObject();
		add((T)j);
		return j;
	}
	
	@SuppressWarnings("unchecked")
	public JSONList<?> addNewList() {
		JSONList<Object> j = new JSONList<>();
		add((T)j);
		return j;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean add(T e) {
		if (e instanceof Boolean || e instanceof Integer || e instanceof Long || e instanceof Double
			|| e instanceof String || e instanceof JSONObject || e instanceof JSONList<?>)
			return super.add(e);
		else if (e instanceof Map<?, ?>)
			return super.add((T)new JSONObject((Map<String, Object>)e));
		else if (e instanceof List<?>)
			return super.add((T)new JSONList<Object>((List<Object>)e));
		else
			throw new ClassCastException();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void add(int index, T element) {
		if (element instanceof Boolean || element instanceof Integer || element instanceof Long || element instanceof Double
			|| element instanceof String || element instanceof JSONObject || element instanceof JSONList<?>)
			super.add(index, element);
		else if (element instanceof Map<?, ?>)
			super.add(index, (T)new JSONObject((Map<String, Object>)element));
		else if (element instanceof List<?>)
			super.add(index, (T)new JSONList<Object>((List<Object>)element));
		else
			throw new ClassCastException();
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