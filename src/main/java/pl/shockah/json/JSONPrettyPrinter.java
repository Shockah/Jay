package pl.shockah.json;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public class JSONPrettyPrinter extends JSONPrinter {
	public static final String DEFAULT_INDENT = "\t";
	
	public final String indent;
	
	protected boolean compactLiterals = true;
	protected int addInitialCompactNewlineForAtLeast = 4;
	protected int addCompactNewlineEvery = 8;
	
	public JSONPrettyPrinter() {
		this(DEFAULT_INDENT);
	}
	
	public JSONPrettyPrinter(String indent) {
		this.indent = indent;
	}

	@Nonnull
	public JSONPrettyPrinter setCompactLiterals(int initialNewlineForAtLeast, int newlineEvery) {
		compactLiterals = true;
		addInitialCompactNewlineForAtLeast = initialNewlineForAtLeast;
		addCompactNewlineEvery = newlineEvery;
		return this;
	}

	@Nonnull
	public JSONPrettyPrinter resetCompactLiterals() {
		compactLiterals = false;
		return this;
	}

	@Override
	@Nonnull
	public String toString(@Nonnull JSONObject j) {
		return toString(j, 0);
	}

	@Nonnull
	protected String toString(@Nonnull JSONObject j, int indentLevel) {
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		
		int i = 0;
		for (Map.Entry<String, Object> entry : j.entrySet()) {
			sb.append('\n');
			
			insertIndent(sb, indentLevel + 1);
			sb.append(toString(entry.getKey()));
			sb.append(": ");
			sb.append(toString(entry.getValue(), indentLevel + 1));
			
			if (i != j.size() - 1)
				sb.append(',');
			
			String comment = j.getComment(entry.getKey());
			if (comment != null) {
				sb.append(" //");
				sb.append(comment);
			}
			
			i++;
		}
		
		sb.append('\n');
		insertIndent(sb, indentLevel);
		sb.append('}');
		return sb.toString();
	}

	@Override
	@Nonnull
	public String toString(@Nonnull JSONList<?> j) {
		return toString(j, 0);
	}

	@Nonnull
	protected String toString(@Nonnull JSONList<?> j, int indentLevel) {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		
		boolean compact = false;
		if (compactLiterals) {
			compact = true;
			for (Object o : j) {
				if (!(o instanceof Boolean || o instanceof BigInteger || o instanceof BigDecimal || o instanceof String)) {
					compact = false;
					break;
				}
			}
		}
		
		if (compact) {
			if (j.size() < addInitialCompactNewlineForAtLeast) {
				int i = 0;
				for (Object o : j) {
					if (i != 0)
						sb.append(", ");
					sb.append(toString(o, indentLevel + 1));
					i++;
				}
				
				sb.append(']');
			} else {
				sb.append('\n');
				insertIndent(sb, indentLevel + 1);
				
				int i = 0;
				for (Object o : j) {
					if (i != 0) {
						sb.append(',');
						if (i % addCompactNewlineEvery == 0) {
							sb.append('\n');
							insertIndent(sb, indentLevel + 1);
						} else {
							sb.append(' ');
						}
					}
					sb.append(toString(o, indentLevel + 1));
					i++;
				}
				
				sb.append('\n');
				insertIndent(sb, indentLevel);
				sb.append(']');
			}
		} else {
			int i = 0;
			for (Object o : j) {
				if (i != 0)
					sb.append(',');
				sb.append('\n');
				
				insertIndent(sb, indentLevel + 1);
				sb.append(toString(o, indentLevel + 1));
				i++;
			}
			
			sb.append('\n');
			insertIndent(sb, indentLevel);
			sb.append(']');
		}
		
		return sb.toString();
	}

	@Nonnull
	protected String toString(@Nullable Object o, int indentLevel) {
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
			return toString((JSONObject)o, indentLevel);
		else if (o instanceof JSONList<?>)
			return toString((JSONList<?>)o, indentLevel);
		else
			throw new IllegalArgumentException();
	}
	
	protected void insertIndent(@Nonnull StringBuilder sb, int indentLevel) {
		for (int i = 0; i < indentLevel; i++)
			sb.append(indent);
	}
}