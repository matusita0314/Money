package com.example.demo.util;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * アプリケーション共通クラス
 * 
 */
public class AppUtil {
	/**
		 メッセージをとってくるクラスです。
		 keyを渡せばメッセージが取れる。
		 paramsは置換できる。
		 ...は可変変数
	*/

	public static String getMessage(MessageSource messageSource, String key, Object... params) {
		return messageSource.getMessage(key, params, Locale.JAPAN);
	}

}