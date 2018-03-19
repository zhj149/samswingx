package org.sam.swing.utils;

import java.util.Iterator;
import java.util.Objects;

/**
 * 字符串相关的工具助手类 很多代码是从apache-commons包里借鉴的
 * 
 * @author sam
 *
 */
public class StringUtil {

	/**
	 * 空字符串
	 */
	public static final String EMPTY = "";

	/**
	 * 未找到的值
	 */
	public static final int INDEX_NOT_FOUND = -1;

	/**
	 * <p>
	 * Joins the elements of the provided {@code Iterator} into a single String
	 * containing the provided elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty
	 * strings within the iteration are represented by empty strings.
	 * </p>
	 *
	 * <p>
	 * See the examples here: {@link #join(Object[],char)}.
	 * </p>
	 *
	 * @param iterator
	 *            the {@code Iterator} of values to join together, may be null
	 * @param fieldName
	 *            object fieldName
	 * @param separator
	 *            the separator character to use
	 * @return the joined String, {@code null} if null iterator input
	 * @since 2.0
	 */
	public static String join(final Iterator<?> iterator, final String fieldName, final char separator) {

		// handle null, zero and one elements before building a buffer
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		// Java default is 16,probably too small
		final StringBuilder buf = new StringBuilder(256);
		try {
			final Object first = iterator.next();
			String display = ReflectUtil.getDisplay(first, fieldName);
			if (!iterator.hasNext()) {
				String result;
				result = Objects.toString(display, "");
				return result;
			}

			// two or more elements
			if (first != null) {
				buf.append(display);
			}

			while (iterator.hasNext()) {
				buf.append(separator);
				final Object obj = iterator.next();
				if (obj != null) {
					buf.append(ReflectUtil.getDisplay(obj, fieldName));
				}
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code Iterator} into a single String
	 * containing the provided elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. A {@code null} separator
	 * is the same as an empty String ("").
	 * </p>
	 *
	 * <p>
	 * See the examples here: {@link #join(Object[],String)}.
	 * </p>
	 *
	 * @param iterator
	 *            the {@code Iterator} of values to join together, may be null
	 * @param fieldName
	 *            object fieldName
	 * @param separator
	 *            the separator character to use, null treated as ""
	 * @return the joined String, {@code null} if null iterator input
	 */
	public static String join(final Iterator<?> iterator, final String fieldName, final String separator) {

		// handle null, zero and one elements before building a buffer
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		// Java default is 16,probably too small
		final StringBuilder buf = new StringBuilder(256);
		try {
			final Object first = iterator.next();
			String display = ReflectUtil.getDisplay(first, fieldName);
			if (!iterator.hasNext()) {
				String result;
				result = Objects.toString(display, "");
				return result;
			}

			// two or more elements
			if (first != null) {
				buf.append(display);
			}

			while (iterator.hasNext()) {
				buf.append(separator);
				final Object obj = iterator.next();
				if (obj != null) {
					buf.append(ReflectUtil.getDisplay(obj, fieldName));
				}
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code Iterable} into a single String
	 * containing the provided elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty
	 * strings within the iteration are represented by empty strings.
	 * </p>
	 *
	 * <p>
	 * See the examples here: {@link #join(Object[],char)}.
	 * </p>
	 *
	 * @param iterable
	 *            the {@code Iterable} providing the values to join together,
	 *            may be null
	 * @param obje
	 * @param separator
	 *            the separator character to use
	 * @return the joined String, {@code null} if null iterator input
	 * @since 2.3
	 */
	public static String join(final Iterable<?> iterable, final String fieldName, final char separator) {
		if (iterable == null) {
			return null;
		}
		return join(iterable.iterator(), fieldName, separator);
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code Iterable} into a single String
	 * containing the provided elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. A {@code null} separator
	 * is the same as an empty String ("").
	 * </p>
	 *
	 * <p>
	 * See the examples here: {@link #join(Object[],String)}.
	 * </p>
	 *
	 * @param iterable
	 *            the {@code Iterable} providing the values to join together,
	 *            may be null
	 * @param separator
	 *            the separator character to use, null treated as ""
	 * @return the joined String, {@code null} if null iterator input
	 * @since 2.3
	 */
	public static String join(final Iterable<?> iterable, final String fieldName, final String separator) {
		if (iterable == null) {
			return null;
		}
		return join(iterable.iterator(), fieldName, separator);
	}

}
