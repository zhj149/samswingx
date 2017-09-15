package org.sam.swing;

/**
 * 值转换对象接口
 * 主要是8种类型 + string + font + color + Date + LocalTime + LocalDate + LocalDateTime
 * @author sam
 *
 */
public interface ValueTranslate {
	
	/**
	 * 获取字符串
	 * @param value
	 * @return
	 */
	public default String getString(Object value){
		
		if (null == value)
			return null;
		
		return value.toString();
	}

	/**
	 * 获取boolean类型
	 * @param value
	 * @return
	 */
	public default Boolean getBoolean(Object value){
		return null;
	}
}
