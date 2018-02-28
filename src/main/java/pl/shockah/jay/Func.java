package pl.shockah.jay;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.annotation.Nonnull;

public final class Func {
	private Func() {
		throw new UnsupportedOperationException();
	}

	public interface VoidFunc {
		void call();
	}

	public interface BooleanFunc {
		void call(boolean v);
	}

	public interface BigIntegerFunc {
		void call(@Nonnull BigInteger v);
	}

	public interface IntFunc {
		void call(int v);
	}

	public interface LongFunc {
		void call(long v);
	}

	public interface BigDecimalFunc {
		void call(@Nonnull BigDecimal v);
	}

	public interface FloatFunc {
		void call(float v);
	}

	public interface DoubleFunc {
		void call(double v);
	}

	public interface StringFunc {
		void call(@Nonnull String v);
	}

	public interface ObjectFunc {
		void call(@Nonnull JSONObject v);
	}

	public interface ListFunc<T> {
		void call(@Nonnull JSONList<T> v);
	}
}