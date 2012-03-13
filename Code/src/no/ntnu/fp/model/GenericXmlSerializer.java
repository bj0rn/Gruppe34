package no.ntnu.fp.model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;


public class GenericXmlSerializer {

	public static void main(String[] args) throws ValidityException, ParsingException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String xml = "<no.ntnu.fp.model.MockModel><Name>Navn</Name><Age>13</Age></no.ntnu.fp.model.MockModel>";
		
		System.out.println(fromXml(xml));
	}
	
	public static String toXml(Object obj) {
		
		Class < ? extends Object> clazz = obj.getClass();
		
		return null;
	}
	
	public static Object fromXml(String xml) throws ValidityException, ParsingException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Builder builder = new Builder();
		Document doc = builder.build(xml, null);
		
		Element root = doc.getRootElement();
		String typeName = root.getQualifiedName();
		
		Class <? extends Object> clazz = Class.forName(typeName);
		
		Object object = clazz.newInstance();
		
		Method methods[] = clazz.getMethods();

		Elements elements = root.getChildElements();
		
		for(int i=0; i<elements.size(); i++) {
			
			Element element = elements.get(i);
			
			String methodName = element.getQualifiedName();
		
			Method method = findMethod(clazz, methodName);
			Class[] types = method.getParameterTypes();
			Class type = types[0];
			
			Object value = type.cast(element.getValue());
			//new Caster<type>();
			
			element.getValue();
			
			method.invoke(object, value);
		}
		
		return object;
	}

	private static Method findMethod(Class<? extends Object> clazz,	String methodName) {
		
		Method [] methods = clazz.getMethods();
		
		for(Method method: methods) {
			if (method.getName().equals("set" + methodName))
				return method;
		}
		
		return null;
	}
	
	private class Caster<T> {
		
		public T cast(String str) {
			
			
			return null;
		}
		
	}
}
