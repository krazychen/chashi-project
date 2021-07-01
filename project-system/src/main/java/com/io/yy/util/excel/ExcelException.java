/**
 * 2019-12-04
 */
package com.io.yy.util.excel;

/**
 * Excel Exception
 * @author kris
 * @version 2019-12-04
 */
public class ExcelException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExcelException() {
		super();
	}

	public ExcelException(String message) {
		super(message);
	}

	public ExcelException(Throwable cause) {
		super(cause);
	}

	public ExcelException(String message, Throwable cause) {
		super(message, cause);
	}
}
