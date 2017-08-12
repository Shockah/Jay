package pl.shockah.json;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public class JSONPrinter {
	@Nonnull
	public String toString(@Nonnull JSONObject j) {
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		
		boolean first = true;
		for (Map.Entry<String, Object> entry : j.entrySet()) {
			if (first)
				first = false;
			else
				sb.append(',');
			sb.append(toString(entry.getKey()));
			sb.append(':');
			sb.append(toString(entry.getValue()));
		}
		
		sb.append('}');
		return sb.toString();
	}

	@Nonnull
	public String toString(@Nonnull JSONList<?> j) {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		
		boolean first = true;
		for (Object o : j) {
			if (first)
				first = false;
			else
				sb.append(',');
			sb.append(toString(o));
		}
		
		sb.append(']');
		return sb.toString();
	}

	@Nonnull
	protected String toString(@Nonnull String s) {
		s = s.replace("\\", "\\\\");
		s = s.replace("\r", "\\r");
		s = s.replace("\n", "\\n");
		s = s.replace("\t", "\\t");
		s = s.replace("\"", "\\\"");
		return String.format("\"%s\"", s);
	}

	@Nonnull
	protected String toString(@Nullable Object o) {
		if (o == null)
			return "null";
		else if (o instanceof String)
			return toString((String)o);
		else if (o instanceof Boolean)
			return (Boolean)o ? "true" : "false";
		else if (o instanceof BigInteger)
			return o.toString();
		else if (o instanceof BigDecimal)
			return o.toString();
		else if (o instanceof JSONObject)
			return toString((JSONObject)o);
		else if (o instanceof JSONList<?>)
			return toString((JSONList<?>)o);
		else
			throw new IllegalArgumentException();
	}
}