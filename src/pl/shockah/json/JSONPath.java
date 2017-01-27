package pl.shockah.json;

import java.util.Arrays;
import pl.shockah.util.func.Action1;

public class JSONPath {
	public final JSONObject json;
	
	public JSONPath(JSONObject json) {
		this.json = json;
	}
	
	protected static String[] splitPath(String path) {
		return path.split("\\.");
	}
	
	public boolean getBool(String path) {
		return getBool(splitPath(path));
	}
	
	public boolean getBool(String path, boolean def) {
		return getBool(splitPath(path), def);
	}
	
	public Boolean getOptionalBool(String path) {
		return getOptionalBool(splitPath(path));
	}
	
	public void onBool(String path, Action1<Boolean> f) {
		onBool(splitPath(path), f);
	}
	
	protected boolean getBool(String[] path) {
		if (path.length == 1)
			return json.getBool(path[0]);
		return new JSONPath(json.getObject(path[0])).getBool(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected boolean getBool(String[] path, boolean def) {
		if (path.length == 1)
			return json.getBool(path[0], def);
		if (!json.containsKey(path[0]))
			return def;
		return new JSONPath(json.getObject(path[0])).getBool(Arrays.copyOfRange(path, 1, path.length), def);
	}
	
	protected Boolean getOptionalBool(String[] path) {
		if (path.length == 1)
			return json.getOptionalBool(path[0]);
		return new JSONPath(json.getObject(path[0])).getOptionalBool(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected void onBool(String[] path, Action1<Boolean> f) {
		if (path.length == 1)
			json.onBool(path[0], f);
		else
			new JSONPath(json.getObject(path[0])).onBool(Arrays.copyOfRange(path, 1, path.length), f);
	}
	
	public int getInt(String path) {
		return getInt(splitPath(path));
	}
	
	public int getInt(String path, int def) {
		return getInt(splitPath(path), def);
	}
	
	public Integer getOptionalInt(String path) {
		return getOptionalInt(splitPath(path));
	}
	
	public void onInt(String path, Action1<Integer> f) {
		onInt(splitPath(path), f);
	}
	
	protected int getInt(String[] path) {
		if (path.length == 1)
			return json.getInt(path[0]);
		return new JSONPath(json.getObject(path[0])).getInt(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected int getInt(String[] path, int def) {
		if (path.length == 1)
			return json.getInt(path[0], def);
		if (!json.containsKey(path[0]))
			return def;
		return new JSONPath(json.getObject(path[0])).getInt(Arrays.copyOfRange(path, 1, path.length), def);
	}
	
	protected Integer getOptionalInt(String[] path) {
		if (path.length == 1)
			return json.getOptionalInt(path[0]);
		return new JSONPath(json.getObject(path[0])).getOptionalInt(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected void onInt(String[] path, Action1<Integer> f) {
		if (path.length == 1)
			json.onInt(path[0], f);
		else
			new JSONPath(json.getObject(path[0])).onInt(Arrays.copyOfRange(path, 1, path.length), f);
	}
	
	public long getLong(String path) {
		return getLong(splitPath(path));
	}
	
	public long getLong(String path, long def) {
		return getLong(splitPath(path), def);
	}
	
	public Long getOptionalLong(String path) {
		return getOptionalLong(splitPath(path));
	}
	
	public void onLong(String path, Action1<Long> f) {
		onLong(splitPath(path), f);
	}
	
	protected long getLong(String[] path) {
		if (path.length == 1)
			return json.getLong(path[0]);
		return new JSONPath(json.getObject(path[0])).getLong(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected long getLong(String[] path, long def) {
		if (path.length == 1)
			return json.getLong(path[0], def);
		if (!json.containsKey(path[0]))
			return def;
		return new JSONPath(json.getObject(path[0])).getLong(Arrays.copyOfRange(path, 1, path.length), def);
	}
	
	protected Long getOptionalLong(String[] path) {
		if (path.length == 1)
			return json.getOptionalLong(path[0]);
		return new JSONPath(json.getObject(path[0])).getOptionalLong(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected void onLong(String[] path, Action1<Long> f) {
		if (path.length == 1)
			json.onLong(path[0], f);
		else
			new JSONPath(json.getObject(path[0])).onLong(Arrays.copyOfRange(path, 1, path.length), f);
	}
	
	public double getDouble(String path) {
		return getDouble(splitPath(path));
	}
	
	public double getDouble(String path, double def) {
		return getDouble(splitPath(path), def);
	}
	
	public Double getOptionalDouble(String path) {
		return getOptionalDouble(splitPath(path));
	}
	
	public void onDouble(String path, Action1<Double> f) {
		onDouble(splitPath(path), f);
	}
	
	protected double getDouble(String[] path) {
		if (path.length == 1)
			return json.getDouble(path[0]);
		return new JSONPath(json.getObject(path[0])).getDouble(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected double getDouble(String[] path, double def) {
		if (path.length == 1)
			return json.getDouble(path[0], def);
		if (!json.containsKey(path[0]))
			return def;
		return new JSONPath(json.getObject(path[0])).getDouble(Arrays.copyOfRange(path, 1, path.length), def);
	}
	
	protected Double getOptionalDouble(String[] path) {
		if (path.length == 1)
			return json.getOptionalDouble(path[0]);
		return new JSONPath(json.getObject(path[0])).getOptionalDouble(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected void onDouble(String[] path, Action1<Double> f) {
		if (path.length == 1)
			json.onDouble(path[0], f);
		else
			new JSONPath(json.getObject(path[0])).onDouble(Arrays.copyOfRange(path, 1, path.length), f);
	}
	
	public String getString(String path) {
		return getString(splitPath(path));
	}
	
	public String getString(String path, String def) {
		return getString(splitPath(path), def);
	}
	
	public String getOptionalString(String path) {
		return getOptionalString(splitPath(path));
	}
	
	public void onString(String path, Action1<String> f) {
		onString(splitPath(path), f);
	}
	
	protected String getString(String[] path) {
		if (path.length == 1)
			return json.getString(path[0]);
		return new JSONPath(json.getObject(path[0])).getString(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected String getString(String[] path, String def) {
		if (path.length == 1)
			return json.getString(path[0], def);
		if (!json.containsKey(path[0]))
			return def;
		return new JSONPath(json.getObject(path[0])).getString(Arrays.copyOfRange(path, 1, path.length), def);
	}
	
	protected String getOptionalString(String[] path) {
		if (path.length == 1)
			return json.getOptionalString(path[0]);
		return new JSONPath(json.getObject(path[0])).getOptionalString(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected void onString(String[] path, Action1<String> f) {
		if (path.length == 1)
			json.onString(path[0], f);
		else
			new JSONPath(json.getObject(path[0])).onString(Arrays.copyOfRange(path, 1, path.length), f);
	}
	
	public JSONObject getObject(String path) {
		return getObject(splitPath(path));
	}
	
	public JSONObject getObject(String path, JSONObject def) {
		return getObject(splitPath(path), def);
	}
	
	public JSONObject getObjectOrEmpty(String path) {
		return getObjectOrEmpty(splitPath(path));
	}
	
	public JSONObject getObjectOrNew(String path) {
		return getObjectOrNew(splitPath(path));
	}
	
	public JSONObject getOptionalObject(String path) {
		return getOptionalObject(splitPath(path));
	}
	
	public void onObject(String path, Action1<JSONObject> f) {
		onObject(splitPath(path), f);
	}
	
	protected JSONObject getObject(String[] path) {
		if (path.length == 1)
			return json.getObject(path[0]);
		return new JSONPath(json.getObject(path[0])).getObject(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected JSONObject getObject(String[] path, JSONObject def) {
		if (path.length == 1)
			return json.getObject(path[0], def);
		if (!json.containsKey(path[0]))
			return def;
		return new JSONPath(json.getObject(path[0])).getObject(Arrays.copyOfRange(path, 1, path.length), def);
	}
	
	protected JSONObject getObjectOrEmpty(String[] path) {
		if (path.length == 1)
			return json.getObjectOrEmpty(path[0]);
		if (!json.containsKey(path[0]))
			return new JSONObject();
		return new JSONPath(json.getObject(path[0])).getObjectOrEmpty(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected JSONObject getObjectOrNew(String[] path) {
		if (path.length == 1)
			return json.getObjectOrNew(path[0]);
		if (!json.containsKey(path[0]))
			json.put(path[0], new JSONObject());
		return new JSONPath(json.getObject(path[0])).getObjectOrNew(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected JSONObject getOptionalObject(String[] path) {
		if (path.length == 1)
			return json.getOptionalObject(path[0]);
		return new JSONPath(json.getObject(path[0])).getOptionalObject(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected void onObject(String[] path, Action1<JSONObject> f) {
		if (path.length == 1)
			json.onObject(path[0], f);
		else
			new JSONPath(json.getObject(path[0])).onObject(Arrays.copyOfRange(path, 1, path.length), f);
	}
	
	public JSONList<?> getList(String path) {
		return getList(splitPath(path));
	}
	
	public JSONList<?> getList(String path, JSONList<?> def) {
		return getList(splitPath(path), def);
	}
	
	public JSONList<?> getListOrEmpty(String path) {
		return getListOrEmpty(splitPath(path));
	}
	
	public JSONList<?> getListOrNew(String path) {
		return getListOrNew(splitPath(path));
	}
	
	public JSONList<?> getOptionalList(String path) {
		return getOptionalList(splitPath(path));
	}
	
	public void onList(String path, Action1<JSONList<?>> f) {
		onList(splitPath(path), f);
	}
	
	protected JSONList<?> getList(String[] path) {
		if (path.length == 1)
			return json.getList(path[0]);
		return new JSONPath(json.getObject(path[0])).getList(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected JSONList<?> getList(String[] path, JSONList<?> def) {
		if (path.length == 1)
			return json.getList(path[0], def);
		if (!json.containsKey(path[0]))
			return def;
		return new JSONPath(json.getObject(path[0])).getList(Arrays.copyOfRange(path, 1, path.length), def);
	}
	
	protected JSONList<?> getListOrEmpty(String[] path) {
		if (path.length == 1)
			return json.getListOrEmpty(path[0]);
		if (!json.containsKey(path[0]))
			return new JSONList<>();
		return new JSONPath(json.getObject(path[0])).getListOrEmpty(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected JSONList<?> getListOrNew(String[] path) {
		if (path.length == 1)
			return json.getListOrNew(path[0]);
		if (!json.containsKey(path[0]))
			json.put(path[0], new JSONList<>());
		return new JSONPath(json.getObject(path[0])).getListOrNew(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected JSONList<?> getOptionalList(String[] path) {
		if (path.length == 1)
			return json.getOptionalList(path[0]);
		return new JSONPath(json.getObject(path[0])).getOptionalList(Arrays.copyOfRange(path, 1, path.length));
	}
	
	protected void onList(String[] path, Action1<JSONList<?>> f) {
		if (path.length == 1)
			json.onList(path[0], f);
		else
			new JSONPath(json.getObject(path[0])).onList(Arrays.copyOfRange(path, 1, path.length), f);
	}
	
	public void put(String path, Object value) {
		value = JSONObject.prepareObject(value);
		put(splitPath(path), value);
	}
	
	protected void put(String[] path, Object value) {
		if (path.length == 1)
			json.put(path[0], value);
		else
			new JSONPath(json.getObjectOrNew(path[0])).put(Arrays.copyOfRange(path, 1, path.length), value);
	}
}