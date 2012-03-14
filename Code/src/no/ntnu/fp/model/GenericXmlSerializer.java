package no.ntnu.fp.model;

import java.awt.List;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;


public class GenericXmlSerializer {
	
	private static String TYPE_OBJECT = "object";
	private static String TYPE_FIELD = "field";
	private static String TYPE_REF = "ref";
	
	public static void main(String[] args) throws ValidityException, ParsingException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String mockmodel1 = "<no.ntnu.fp.model.MockModel id=\"1\" type=\"object\"><Name type=\"field\">Navn</Name><Age type=\"field\">13</Age></no.ntnu.fp.model.MockModel>";
		String mockmodel2 = "<no.ntnu.fp.model.MockModel type=\"object\"><Name type=\"field\">Navn</Name><Age type=\"field\">13</Age><no.ntnu.fp.model.MockModel type=\"ref\" id =\"1\" /></no.ntnu.fp.model.MockModel>";
		
		String xml = "<no.ntnu.fp.model.MockListModel type=\"object\">" + mockmodel1 + mockmodel2 + "</no.ntnu.fp.model.MockListModel>" ;
		
		System.out.println(fromXml(xml));	
	}
	
	public static String toXmlSimple(Object obj) throws IllegalArgumentException, IllegalAccessException {
		
		Class < ? extends Object> clazz = obj.getClass();
		
		Field[] fields = clazz.getDeclaredFields();
		
		for (Field field : fields) {
			
			field.getName();
			field.setAccessible(true);
			Object value = field.get(obj);
			field.setAccessible(false);
			
			 if (value.getClass().isInstance(new Integer())) {
					
			} else if (value.getClass().isInstance(new String())) {
				
			} else if (value.getClass().isInstance(new Double())) {
				
			} else if (value.getClass().isInstance(obj))
			
			
			
			
		}
		
		return null;
	}
	
	public static String toXmlDeep(Object obj) {
		
		return null;
	}
	
	public static Object fromXml(String xml) throws ValidityException, ParsingException, IOException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Builder builder = new Builder();
		Document doc = builder.build(xml, null);
		
		Element root = doc.getRootElement();
		
		Map<String, Object> objects = new HashMap<String, Object>();
		
		return fromXml(root, objects);
	}
	
	public static Object fromXml(Element root, Map<String, Object> objects) throws ValidityException, ParsingException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		String typeName = root.getQualifiedName();
		
		Class <? extends Object> clazz = Class.forName(typeName);
		
		Object object = clazz.newInstance();
		
		int id = Integer.parseInt(root.getAttributeValue("id"));
		String key = typeName + id;
		objects.put(typeName, object);
		
		Elements elements = root.getChildElements();
		
		for(int i=0; i<elements.size(); i++) {
			
			Element element = elements.get(i);
			String type = element.getAttributeValue("type"); 
			
 			if (type.equals(TYPE_OBJECT)) {
				
				Object value = fromXml(element, objects);
				String[] parts = element.getQualifiedName().split("\\.");
				String methodName = parts[parts.length-1];
				
				Method method = findAddMethod(clazz, methodName);
				
				method.invoke(object, value);
				
			} else if (type.equals(TYPE_FIELD)) {
			
				String methodName = element.getQualifiedName();
			
				Method method = findSetMethod(clazz, methodName);
				
				String value = element.getValue();
				
				method.invoke(object, value);
			} else if (type.equals(TYPE_REF)) {
				
				id = Integer.parseInt(element.getAttributeValue("id"));
				
				String[] parts = element.getQualifiedName().split("\\.");
				String methodName = parts[parts.length-1];
				
				Object value = objects.get(methodName + id); 
				
				Method method = findAddMethod(clazz, methodName);
				
				method.invoke(object, value);
				
			}
		}
		
		return object;
	}
	
	private static String parseValue(Object value) {
		return String.format("%s", value);
	}

	private static Method findSetMethod(Class<? extends Object> clazz, String fieldName) {
		
		Method [] methods = clazz.getMethods();
		
		Object obj = new String();
		
		for(Method method: methods) {
			if (method.getName().equals("set" + fieldName)) {
				if (method.getParameterTypes()[0].isInstance(obj)) {
					return method;
				}
			}
		}
		
		return null;
	}

	private static Method findAddMethod(Class<? extends Object> clazz, String fieldName) {
		
		Method [] methods = clazz.getMethods();
		
		for(Method method: methods) {
			if (method.getName().equals("add" + fieldName)) {
				return method;
			}
		}
		
		return null;
	}
}
