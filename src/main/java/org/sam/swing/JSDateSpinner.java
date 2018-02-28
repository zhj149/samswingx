package org.sam.swing;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.InternationalFormatter;

public class JSDateSpinner extends JSpinner {

	/**
	 * 空日期类型
	 */
	@SuppressWarnings("deprecation")
	public static final Date NULL = new Date(0, 0, 1, 0, 0, 0);

	/**
	 * 获取一个系统默认的空日期类型 1900-01-01 00：00：00
	 * 
	 * @return
	 */
	public static Date getNullDate() {
		return new Date(NULL.getTime());
	}

	/**
	 * 判断日期时间是否为空
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isNull(Date date) {
		return date == null || NULL.equals(date);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1331528517619201306L;

	public DefaultFormatter _formatter;
	public DateEditor _timeEditor;
	public DateFormat _format;

	/**
	 * Creates a date spinner using locale default as the format string.
	 */
	public JSDateSpinner() {
		this(((SimpleDateFormat) SimpleDateFormat.getTimeInstance(SimpleDateFormat.DEFAULT, Locale.getDefault()))
				.toPattern());
	}

	/**
	 * Creates a date spinner using the specified format string.
	 *
	 * @param format
	 *            the format string as defined in
	 *            {@link java.text.SimpleDateFormat}.
	 */
	public JSDateSpinner(String format) {
		this(format, new Date());
	}

	/**
	 * create with format
	 * 
	 * @param format
	 */
	public JSDateSpinner(SimpleDateFormat format) {
		this(format.toPattern(), new Date());
	}

	/**
	 * cureate with format and date
	 * 
	 * @param format
	 * @param date
	 */
	public JSDateSpinner(SimpleDateFormat format, Date date) {
		this(format.toPattern(), date);
	}

	/**
	 * Creates a date spinner using the specified format string and an initial
	 * value.
	 *
	 * @param format
	 *            the format string as defined in
	 *            {@link java.text.SimpleDateFormat}.
	 * @param date
	 *            initial value
	 */
	public JSDateSpinner(String format, Date date) {
		super(new SpinnerDateModel(date, null, null, Calendar.DAY_OF_MONTH));
		// setBorder(BorderFactory.createEmptyBorder());
		// setOpaque(true);

		setFormat(format);

		customizeSpinner();
	}

	/**
	 * 设置空值的时候拦截一下操作
	 */
	@Override
	public void setValue(Object value) {
		if (value == null) {
			value = getNullDate();
		}
		super.setValue(value);
	}

	private void customizeDateEditor() {
		// _timeEditor.setBorder(BorderFactory.createEmptyBorder());
		JFormattedTextField.AbstractFormatter formatter = _timeEditor.getTextField().getFormatter();
		if (formatter instanceof DefaultFormatter) {
			_formatter = (DefaultFormatter) formatter;
		} else {
			throw new IllegalStateException("The formatter is not an instance of DefaultFormatter.");
		}

		if (formatter instanceof InternationalFormatter) {
			Format f = ((InternationalFormatter) formatter).getFormat();
			if (f instanceof DateFormat) {
				_format = ((DateFormat) f);
			}
		}

		if (_format == null) {
			throw new IllegalStateException("The format is not an instance of SimpleDateFormat.");
		}
	}

	/**
	 * Sets the date format string used by this DateSpinner. Please note, this
	 * method call will recreate the DateEditor used by DateSpinner.
	 *
	 * @param format
	 *            the format
	 */
	public void setFormat(String format) {
		_timeEditor = createDateEditor(format);
		customizeDateEditor();
		setEditor(_timeEditor);
	}

	/**
	 * Customizes the spinner.
	 */
	protected void customizeSpinner() {
		setLenient(false);
		setCommitsOnValidEdit(true);
		setAllowsInvalid(false);
		setOverwriteMode(true);
		SpinnerWheelSupport.installMouseWheelSupport(this);
	}

	/**
	 * Creates the DateEditor.
	 *
	 * @param format
	 *            the format
	 * @return the DateEditor.
	 */
	protected DateEditor createDateEditor(String format) {
		return new DateEditor(this, format);
	}

	/**
	 * Sets when edits are published back to the
	 * <code>JFormattedTextField</code>. If true, <code>commitEdit</code> is
	 * invoked after every valid edit (any time the text is edited). On the
	 * other hand, if this is false than the <code>DefaultFormatter</code> does
	 * not publish edits back to the <code>JFormattedTextField</code>. As such,
	 * the only time the value of the <code>JFormattedTextField</code> will
	 * change is when <code>commitEdit</code> is invoked on
	 * <code>JFormattedTextField</code>, typically when enter is pressed or
	 * focus leaves the <code>JFormattedTextField</code>.
	 *
	 * @param commit
	 *            Used to indicate when edits are committed back to the
	 *            JTextComponent
	 */
	public void setCommitsOnValidEdit(boolean commit) {
		_formatter.setCommitsOnValidEdit(commit);
	}

	/**
	 * Returns when edits are published back to the
	 * <code>JFormattedTextField</code>.
	 *
	 * @return true if edits are committed aftereveryy valid edit
	 */
	public boolean getCommitsOnValidEdit() {
		return _formatter.getCommitsOnValidEdit();
	}

	/**
	 * Configures the behavior when inserting characters. If
	 * <code>overwriteMode</code> is true (the default), new characters
	 * overwrite existing characters in the model.
	 *
	 * @param overwriteMode
	 *            Indicates if overwrite or overstrike mode is used
	 */
	public void setOverwriteMode(boolean overwriteMode) {
		_formatter.setOverwriteMode(overwriteMode);
	}

	/**
	 * Returns the behavior when inserting characters.
	 *
	 * @return true if newly inserted characters overwrite existing characters
	 */
	public boolean getOverwriteMode() {
		return _formatter.getOverwriteMode();
	}

	/**
	 * Sets whether or not the value being edited is allowed to be invalid for a
	 * length of time (that is, <code>stringToValue</code> throws a
	 * <code>ParseException</code>). It is often convenient to allow the user to
	 * temporarily input an invalid value.
	 *
	 * @param allowsInvalid
	 *            Used to indicate if the edited value must always be valid
	 */
	public void setAllowsInvalid(boolean allowsInvalid) {
		_formatter.setAllowsInvalid(allowsInvalid);
	}

	/**
	 * Returns whether or not the value being edited is allowed to be invalid
	 * for a length of time.
	 *
	 * @return false if the edited value must always be valid
	 */
	public boolean getAllowsInvalid() {
		return _formatter.getAllowsInvalid();
	}

	/**
	 * Sets the time zone for the calendar of this DateFormat object.
	 *
	 * @param zone
	 *            the given new time zone.
	 */
	public void setTimeZone(TimeZone zone) {
		_format.setTimeZone(zone);
	}

	/**
	 * Gets the time zone.
	 *
	 * @return the time zone associated with the calendar of DateFormat.
	 */
	public TimeZone getTimeZone() {
		return _format.getTimeZone();
	}

	/**
	 * Specify whether or not date/time parsing is to be lenient. With lenient
	 * parsing, the parser may use heuristics to interpret inputs that do not
	 * precisely match this object's format. With strict parsing, inputs must
	 * match this object's format.
	 *
	 * @param lenient
	 *            when true, parsing is lenient
	 * @see java.util.Calendar#setLenient
	 */
	public void setLenient(boolean lenient) {
		_format.setLenient(lenient);
	}

	/**
	 * Tell whether date/time parsing is to be lenient. It is the same as
	 * {@link java.text.DateFormat#isLenient()}.
	 *
	 * @return true or false.
	 */
	public boolean isLenient() {
		return _format.isLenient();
	}

}
