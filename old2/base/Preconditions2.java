/*
 * Copyright (C) 2010 Zhenya Leonov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package base;

import com.google.common.base.Preconditions;

/**
 * Static utility methods used to verify correctness of arguments passed to your
 * own methods.
 * 
 * @author Zhenya Leonov
 * @see Preconditions
 */
final public class Preconditions2 {

	private Preconditions2() {
	};

	/**
	 * Ensures that an argument passed as a parameter to the calling method is
	 * not {@code null}.
	 * <p>
	 * Unlike {@link Preconditions#checkNotNull(Object)
	 * Preconditions.checkNotNull(T)} this method throws an
	 * {@code IllegalArgumentException} instead of a
	 * {@code NullPointerException} if the {@code arg} parameter is {@code null}.
	 * 
	 * @param arg
	 *            the argument passed to the calling method
	 * @return the non-null argument that was validated
	 * @throws IllegalArgumentException
	 *             if the argument is {@code null}
	 */
	public static <T> T checkArgumentNotNull(final T arg) {
		if (arg == null)
			throw new IllegalArgumentException();
		return arg;
	}

	/**
	 * Ensures that an argument passed as a parameter to the calling method is
	 * not {@code null}.
	 * <p>
	 * Unlike {@link Preconditions#checkNotNull(Object, Object)
	 * Preconditions.checkNotNull(T, Object)} this method throws an
	 * {@code IllegalArgumentException} instead of a
	 * {@code NullPointerException} if the {@code arg} parameter is {@code null}.
	 * 
	 * @param arg
	 *            the argument passed to the calling method
	 * @param message
	 *            the exception message to use if the check fails; will be
	 *            converted to a string using {@link String#valueOf(Object)}
	 * @return the non-null argument that was validated
	 * @throws IllegalArgumentException
	 *             if the argument is {@code null}
	 */
	public static <T> T checkArgumentNotNull(final T arg, final Object message) {
		if (arg == null)
			throw new IllegalArgumentException(String.valueOf(message));
		return arg;
	}

	/**
	 * Ensures that an argument passed as a parameter to the calling method is
	 * not {@code null}.
	 * <p>
	 * Unlike {@link Preconditions#checkNotNull(Object, String, Object...)
	 * Preconditions.checkNotNull(T, String, Object...)} this method throws an
	 * {@code IllegalArgumentException} instead of a
	 * {@code NullPointerException} if the {@code arg} parameter is {@code null}.
	 * 
	 * @param arg
	 *            the argument passed to the calling method
	 * @param template
	 *            a template for the exception message should the check fail.
	 *            The message is formed by replacing each {@code %s} placeholder
	 *            in the template with an argument. These are matched by
	 *            position - the first {@code %s} gets
	 *            {@code errorMessageArgs[0]}, etc. Unmatched arguments will be
	 *            appended to the formatted message in square braces. Unmatched
	 *            placeholders will be left as-is.
	 * @param messages
	 *            the arguments to be substituted into the message template.
	 *            Arguments are converted to strings using
	 *            {@link String#valueOf(Object)}.
	 * @return the non-null argument that was validated
	 * @throws IllegalArgumentException
	 *             if the argument is {@code null}
	 */
	public static <T> T checkArgumentNotNull(final T arg,
			final String template, final Object... messages) {
		if (arg == null)
			throw new IllegalArgumentException(format(template, messages));
		return arg;
	}

	// /**
	// * Ensures that the specified element is located between {@code
	// fromElement}
	// * inclusive, and {@code toElement} inclusive, according to their
	// <i>natural
	// * ordering</i>.
	// *
	// * @param element
	// * a user-supplied element
	// * @param fromElement
	// * low endpoint (inclusive) of the specified range
	// * @param toElement
	// * high endpoint (inclusive) of the specified range
	// * @return the specified element
	// */
	// public static <T> T checkElementPosition(final T element,
	// final T fromElement, final T toElement) {
	// return checkElementPosition(element, fromElement, toElement,
	// (Comparator<? super T>) Ordering.natural());
	// }
	//
	// /**
	// * Ensures that the specified element is located between {@code
	// fromElement}
	// * inclusive, and {@code toElement} inclusive, according to the specified
	// * {@code Comparator}.
	// *
	// * @param element
	// * a user-supplied element
	// * @param fromElement
	// * low endpoint (inclusive) of the specified range
	// * @param toElement
	// * high endpoint (inclusive) of the specified range
	// * @param comparator
	// * a user-supplied comparator
	// * @return the specified element
	// */
	// public static <T> T checkElementPosition(final T element,
	// final T fromElement, final T toElement,
	// Comparator<? super T> comparator) {
	// if (comparator.compare(element, fromElement) < 0)
	// throw new IllegalArgumentException(format(
	// "element (%s) must be greater or equal to (%s)", element,
	// fromElement));
	// if (comparator.compare(element, toElement) >= 0)
	// throw new IllegalArgumentException(format(
	// "element (%s) must be strictly less than (%s)", element,
	// toElement));
	// return element;
	// }

	/**
	 * Substitutes each {@code %s} in {@code template} with an argument. These
	 * are matched by position - the first {@code %s} gets {@code args[0]}, etc.
	 * If there are more arguments than placeholders, the unmatched arguments
	 * will be appended to the end of the formatted message in square braces.
	 * 
	 * @param template
	 *            a non-null string containing 0 or more {@code %s}
	 *            placeholders.
	 * @param args
	 *            the arguments to be substituted into the message template.
	 *            Arguments are converted to strings using
	 *            {@link String#valueOf(Object)}. Arguments can be {@code null}.
	 */
	private static String format(String template, Object... args) {
		StringBuilder sb = new StringBuilder(template);
		int i, c = 0;
		for (i = sb.indexOf("%s"); i > -1 && c < args.length; i = sb
				.indexOf("%s")) {
			sb.replace(i, i + 2, String.valueOf(args[c]));
			c++;
		}
		if (c < args.length) {
			sb.append(" [");
			for (i = c; i < args.length; i++)
				sb.append(String.valueOf(args[i])).append(", ");
			sb.replace(sb.length() - 2, sb.length(), "]");
		}
		return sb.toString();
	}

}