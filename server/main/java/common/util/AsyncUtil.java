package common.util;

import common.util.reflection.ReflectionUtil;

public class AsyncUtil implements Runnable {
    
	String classFilePath, packageName, className, methodName;
	Object[] paramValues;
	Class<?>[] paramTypes;
	
	public AsyncUtil (String classFilePath, String packageName, String className, String methodName, Object[] paramValues, Class<?>[] paramTypes) {
		this.classFilePath = classFilePath;
		this.packageName = packageName;
		this.className = className;
		this.methodName = methodName;
		this.paramValues = paramValues;
		this.paramTypes = paramTypes;
	}
	
	public AsyncUtil (String packageName, String className, String methodName, Object[] paramValues, Class<?>[] paramTypes) {
		this.packageName = packageName;
		this.className = className;
		this.methodName = methodName;
		this.paramValues = paramValues;
		this.paramTypes = paramTypes;
	}
	
	@Override
    public void run () {
		//객체 생성
		Object clazz = ReflectionUtil.classAndBeanLoad(classFilePath, (packageName + "." + className));
		//메서드 실행
		ReflectionUtil.invokeByMethodName(clazz, methodName, paramValues, paramTypes);
    }

}
