package com.haokan.onescreen.utils;

import java.lang.reflect.Field;

import android.content.Context;

public class MResource {
	/**
	 * 对于 context.getResources().getIdentifier 无法获取的数据 , 或者数组 资源反射值
	 * 
	 * @paramcontext
	 * @param name
	 * @param type
	 * @return
	 */
	private static Object getResourceId(Context context, String name, String type) {
		String className = context.getPackageName() + ".R";
		try {
			Class<?> cls = Class.forName(className);
			for (Class<?> childClass : cls.getClasses()) {
				String simple = childClass.getSimpleName();
				if (simple.equals(type)) {
					for (Field field : childClass.getFields()) {
						String fieldName = field.getName();
						if (fieldName.equals(name)) {
							System.out.println(fieldName);
							return field.get(null);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * context.getResources().getIdentifier 无法获取到 styleable 的数据
	 * 
	 * @paramcontext
	 * @param name
	 * @return
	 */
	public static int getStyleable(Context context, String name) {
		return ((Integer) getResourceId(context, name, "styleable")).intValue();
	}

	/**
	 * 获取 styleable 的 ID 号数组
	 * 
	 * @paramcontext
	 * @param name
	 * @return
	 */
	public static int[] getStyleableArray(Context context, String name) {
		return (int[]) getResourceId(context, name, "styleable");
	}

}
