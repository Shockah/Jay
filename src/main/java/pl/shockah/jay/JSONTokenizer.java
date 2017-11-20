package pl.shockah.jay;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class JSONTokenizer {
	protected static final String LITERAL_ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyz1234567890.-+";
	protected static final String NULL_LITERAL = "null";
	protected static final String TRUE_LITERAL = "true";
	protected static final String FALSE_LITERAL = "false";

	@Nonnull
	public List<Object> tokenize(@Nonnull String json) {
		List<Object> tokens = new ArrayList<>();
		
		StrBuffer buf = new StrBuffer(json);
		char inString = 0;
		boolean inLiteral = false;
		StringBuilder sb = null;
		int openingPosition = 0;
		
		char oldc = 0;
		while (true) {
			char c = buf.hasLeft() ? buf.read() : 0;
			
			if (inLiteral) {
				if (c == 0) {
					throw new JSONParseException("Invalid JSON file: reached end of string");
				} else if (LITERAL_ALLOWED_CHARACTERS.indexOf(c) != -1) {
					sb.append(c);
					continue;
				} else {
					String literal = sb.toString();
					buf.seek(-1);
					switch (literal) {
						case NULL_LITERAL:
							tokens.add(null);
							break;
						case TRUE_LITERAL:
							tokens.add(true);
							break;
						case FALSE_LITERAL:
							tokens.add(false);
							break;
						default:
							try {
								tokens.add(new BigInteger(literal));
							} catch (NumberFormatException e1) {
								try {
									tokens.add(new BigDecimal(literal));
								} catch (NumberFormatException e2) {
									throw new JSONParseException(String.format("Invalid literal '%s' at position %d", literal, openingPosition));
								}
							}
							break;
					}
					inLiteral = false;
				}
			} else if (inString != 0) {
				if (oldc == '\\') {
					if (c == '\\') {
						sb.append('\\');
						oldc = 0;
						continue;
					} else if (c == inString) {
						sb.append(inString);
						oldc = 0;
						continue;
					} else if (c == 't') {
						sb.append('\t');
						oldc = 0;
						continue;
					} else if (c == 'n') {
						sb.append('\n');
						oldc = 0;
						continue;
					} else if (c == 'r') {
						sb.append('\r');
						oldc = 0;
						continue;
					} else if (c == 'u') {
						sb.append((char)Integer.parseInt(buf.read(4), 16));
						oldc = 0;
						continue;
					} else {
						sb.append(c);
						oldc = 0;
						continue;
						//throw new JSONParseException(String.format("Invalid string escape '\\%c' at position %d", c, buf.position - 2));
					}
				} else {
					if (c == inString) {
						tokens.add(sb.toString());
						inString = 0;
					} else if (c != '\\') {
						sb.append(c);
					}
				}
			} else {
				if (oldc == '/') {
					if (c == '/') {
						while (true) {
							char c2 = buf.hasLeft() ? buf.read() : 0;
							if (c2 == '\r' || c2 == '\n') {
								c = c2;
								break;
							}
						}
					} else if (c == '*') {
						char oldc2 = 0;
						while (true) {
							char c2 = buf.hasLeft() ? buf.read() : 0;
							if (oldc2 == '*' && c2 == '/')
								break;
							oldc2 = c2;
							c = ' ';
						}
					} else {
						throw new JSONParseException(String.format("Invalid token '/%c' at position %d", c, buf.position - 2));
					}
				} else if (c == '{') {
					tokens.add(JSONSpecialToken.ObjectBegin);
				} else if (c == '}') {
					tokens.add(JSONSpecialToken.ObjectEnd);
				} else if (c == '[') {
					tokens.add(JSONSpecialToken.ListBegin);
				} else if (c == ']') {
					tokens.add(JSONSpecialToken.ListEnd);
				} else if (c == ':') {
					tokens.add(JSONSpecialToken.Colon);
				} else if (c == ',') {
					tokens.add(JSONSpecialToken.Comma);
				} else if (c == '"' || c == '\'') {
					sb = new StringBuilder();
					inString = c;
				} else if (c != '/' && !Character.isWhitespace(c)) {
					sb = new StringBuilder();
					inLiteral = true;
					buf.seek(-1);
				}
			}
			oldc = c;
			
			if (c == 0)
				break;
		}
		
		return tokens;
	}
	
	protected static class StrBuffer {
		@Nonnull public final String string;
		public int position = 0;
		
		public StrBuffer(@Nonnull String string) {
			this.string = string;
		}
		
		public int length() {
			return string.length();
		}
		
		public int charsLeft() {
			return string.length() - position;
		}
		
		public boolean hasLeft() {
			return charsLeft() > 0;
		}
		
		public void seek(int offset) {
			int newPosition = position + offset;
			if (newPosition < 0 || newPosition > string.length())
				throw new IndexOutOfBoundsException();
			position = newPosition;
		}
		
		public void seekTo(int position) {
			if (position < 0 || position > string.length())
				throw new IndexOutOfBoundsException();
			this.position = position;
		}
		
		public char read() {
			if (position >= string.length())
				throw new IndexOutOfBoundsException();
			return string.charAt(position++);
		}

		@Nonnull
		public String read(int length) {
			int pos = position;
			try {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < length; i++)
					sb.append(read());
				return sb.toString();
			} catch (IndexOutOfBoundsException e) {
				position = pos;
				throw e;
			}
		}
	}
}