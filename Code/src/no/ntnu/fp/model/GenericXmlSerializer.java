package no.ntnu.fp.model;

import java.awt.List;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.smartcardio.ATR;

import com.sun.xml.internal.fastinfoset.algorithm.IEEE754FloatingPointEncodingAlgorithm;

import nu.xom.Attribute;
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
	private static final HashSet <Class<?>> WRAPPER_TYPES = getWrapperTypes();
	
	
	public static void main(String[] args) throws ValidityException, ParsingException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String mockmodel1 = "<no.ntnu.fp.model.MockModel id=\"1\" type=\"object\"><Name type=\"field\">Navn</Name><Age type=\"field\">13</Age></no.ntnu.fp.model.MockModel>";
		String mockmodel2 = "<no.ntnu.fp.model.MockModel type=\"object\"><Name type=\"field\">Navn</Name><Age type=\"field\">13</Age><no.ntnu.fp.model.MockModel type=\"ref\" id =\"1\" /></no.ntnu.fp.model.MockModel>";
		
		String xml = "<no.ntnu.fp.model.MockListModel type=\"object\">" + mockmodel1 + mockmodel2 + "</no.ntnu.fp.model.MockListModel>" ;
		
		System.out.println(fromXml(xml));	
	}
	
	 public static boolean isWrapperType(Class<?> clazz){
		 return WRAPPER_TYPES.contains(clazz);
	    }
	
	private static HashSet<Class <?>> getWrapperTypes(){
		HashSet<Class<?>> ret = new HashSet<Class<?>>();
	 	ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        ret.add(String.class);
        return ret;
	}
	
	
	/**
	   * @param type the type to check.
	   *
	   * @return Returns <code>true</code> if <code>type</code> is a iterable type, <code>false</code> otherwise.
	   */
	  public static boolean isIterable(Type type) {
	    if ( type instanceof Class && isIterableClass( ( Class ) type ) ) {
	      return true;
	    }
	    if ( type instanceof ParameterizedType ) {
	      return isIterable( ( ( ParameterizedType ) type ).getRawType() );
	    }
	    if ( type instanceof WildcardType ) {
	      Type[] upperBounds = ( ( WildcardType ) type ).getUpperBounds();
	      return upperBounds.length != 0 && isIterable( upperBounds[0] );
	    }
	    return false;
	  }

	  /**
	   * Checks whether the specified class parameter is an instance of a collection class.
	   *
	   * @param clazz <code>Class</code> to check.
	   *
	   * @return <code>true</code> is <code>clazz</code> is instance of a collection class, <code>false</code> otherwise.
	   */
	  private static boolean isIterableClass(Class<?> clazz) {
	    ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
	    computeClassHierarchy( clazz, classes );
	    return classes.contains( Iterable.class );
	  }

	  /**
	   * Get all superclasses and interfaces recursively.
	   *
	   * @param clazz The class to start the search with.
	   * @param classes List of classes to which to add all found super classes and interfaces.
	   */
	  private static void computeClassHierarchy(Class<?> clazz, ArrayList<Class<?>> classes) {
	    for ( Class current = clazz; current != null; current = current.getSuperclass() ) {
	      if ( classes.contains( current ) ) {
	        return;
	      }
	      classes.add( current );
	      for ( Class currentInterface : current.getInterfaces() ) {
	        computeClassHierarchy( currentInterface, classes );
	      }
	    }
	  }
	
	  
	  public static String toXmlSimple(Object obj) throws IllegalArgumentException, IllegalAccessException{
		  Class <? extends Object> clazz = obj.getClass();
		  return toXmlSimple(obj, true).toXML();
	  }
	  
	  
	  public static Element toXmlSimple(Object obj, boolean signature) throws IllegalArgumentException, IllegalAccessException{
		  Class <? extends Object> clazz = obj.getClass();
		  Element root = new Element(clazz.getName());
		  Attribute attr = new Attribute("type", "object");
		  root.addAttribute(attr);
		  Field[] fields = clazz.getDeclaredFields();
		  for(Field field: fields){
			  //Get value
			  field.setAccessible(true);
			  Object value = field.get(obj);
			  field.setAccessible(false);
			  Element tmp = null;
			  
			 
			 if(isIterable(field.getGenericType())){
				  //New objects
				  for(Object t : (Iterable)value){
					  System.out.println("Hei");
					  tmp = toXmlSimple(t, true);
					  root.appendChild(tmp);
				  }
			 }else if(!isWrapperType(value.getClass())){
				 System.out.println("new Object: "+value .getClass().getName());
				  tmp = toXmlSimple(value, true);
			  	  root.appendChild(tmp);
			
			 }else{
				  //ordinary field
				  tmp = new Element(field.getName());
				  tmp.addAttribute(new Attribute("type", "field"));
				  //TODO: Handle null values 
				  tmp.appendChild(value.toString());
				  root.appendChild(tmp);
				  //continue;
			  }
		  }
		  return root;
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
