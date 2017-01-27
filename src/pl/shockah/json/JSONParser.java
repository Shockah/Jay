package pl.shockah.json;

import java.util.List;

public class JSONParser {
	protected String tokenToString(Object token) {
		if (token == null)
			return "null";
		else if (token instanceof Boolean)
			return ((Boolean)token) ? "true" : "false";
		else if (token instanceof Integer)
			return Integer.toString((Integer)token);
		else if (token instanceof Long)
			return Long.toString((Long)token);
		else if (token instanceof Double)
			return Double.toString((Double)token);
		else if (token instanceof String)
			return String.format("'%s'", token);
		else if (token instanceof JSONSpecialToken)
			return ((JSONSpecialToken)token).name();
		else
			throw new UnsupportedOperationException(String.format("Unknown token type: %s", token.getClass().getName()));
	}
	
	public JSONObject parseObject(String json) {
		List<Object> tokens = new JSONTokenizer().tokenize(json);
		TokenBuffer buf = new TokenBuffer(tokens);
		JSONObject j = parseObject(buf);
		if (buf.position < buf.length())
			throw new JSONParseException(String.format("Additional token %s after the ObjectEnd token", tokenToString(buf.get())));
		return j;
	}
	
	public JSONList<Object> parseList(String json) {
		List<Object> tokens = new JSONTokenizer().tokenize(json);
		TokenBuffer buf = new TokenBuffer(tokens);
		JSONList<Object> j = parseList(buf);
		if (buf.position < buf.length())
			throw new JSONParseException(String.format("Additional token %s after the ListEnd token", tokenToString(buf.get())));
		return j;
	}
	
	protected JSONObject parseObject(TokenBuffer buf) {
		JSONObject j = null;
		while (buf.hasLeft()) {
			Object token = buf.get();
			if (j == null) {
				if (token == JSONSpecialToken.ObjectBegin) {
					j = new JSONObject();
				} else {
					throw new JSONParseException(String.format("Invalid token %s; expected ObjectBegin", tokenToString(token)));
				}
			} else {
				if (token == JSONSpecialToken.ObjectEnd) {
					return j;
				} else {
					if (!j.isEmpty()) {
						if (token != JSONSpecialToken.Comma)
							throw new JSONParseException(String.format("Invalid token %s; expected Comma", tokenToString(token)));
						if (buf.hasLeft()) {
							token = buf.get();
						} else {
							throw new JSONParseException("Missing token; expected key token");
						}
					}
					if (token instanceof String) {
						String key = (String)token;
						if (buf.hasLeft()) {
							Object token2 = buf.get();
							if (token2 == JSONSpecialToken.Colon) {
								j.put(key, parseValue(buf));
							} else {
								throw new JSONParseException(String.format("Invalid token %s; expected Colon", tokenToString(token2)));
							}
						} else {
							throw new JSONParseException("Missing token; expected Colon");
						}
					}
				}
			}
		}
		throw new JSONParseException("Missing token; expected ObjectEnd");
	}
	
	protected JSONList<Object> parseList(TokenBuffer buf) {
		JSONList<Object> j = null;
		while (buf.hasLeft()) {
			Object token = buf.get();
			if (j == null) {
				if (token == JSONSpecialToken.ListBegin) {
					j = new JSONList<Object>();
				} else {
					throw new JSONParseException(String.format("Invalid token %s; expected ListBegin", tokenToString(token)));
				}
			} else {
				if (token == JSONSpecialToken.ListEnd) {
					return j;
				} else {
					if (j.isEmpty()) {
						buf.seek(-1);
					} else {
						if (token != JSONSpecialToken.Comma)
							throw new JSONParseException(String.format("Invalid token %s; expected Comma", tokenToString(token)));
					}
					j.add(parseValue(buf));
				}
			}
		}
		throw new JSONParseException("Missing token; expected ListEnd");
	}
	
	protected Object parseValue(TokenBuffer buf) {
		if (!buf.hasLeft())
			throw new JSONParseException("Missing token; expected value token");
		Object token = buf.get();
		if (token instanceof JSONSpecialToken) {
			JSONSpecialToken jsontoken = (JSONSpecialToken)token;
			if (jsontoken == JSONSpecialToken.ObjectBegin) {
				buf.seek(-1);
				return parseObject(buf);
			} else if (jsontoken == JSONSpecialToken.ListBegin) {
				buf.seek(-1);
				return parseList(buf);
			} else {
				throw new JSONParseException(String.format("Invalid token %s; expected value token", tokenToString(token)));
			}
		} else {
			return token;
		}
	}
	
	protected static class TokenBuffer {
		public final List<Object> list;
		public int position = 0;
		
		public TokenBuffer(List<Object> list) {
			this.list = list;
		}
		
		public int length() {
			return list.size();
		}
		
		public int tokensLeft() {
			return list.size() - position;
		}
		
		public boolean hasLeft() {
			return tokensLeft() > 0;
		}
		
		public void seek(int offset) {
			int newPosition = position + offset;
			if (newPosition < 0 || newPosition > list.size())
				throw new IndexOutOfBoundsException();
			position = newPosition;
		}
		
		public void seekTo(int position) {
			if (position < 0 || position > list.size())
				throw new IndexOutOfBoundsException();
			this.position = position;
		}
		
		public Object get() {
			if (position >= list.size())
				throw new IndexOutOfBoundsException();
			return list.get(position++);
		}
	}
}