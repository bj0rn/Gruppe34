package no.ntnu.fp.model;

import java.util.List;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;


public class GenericXmlSerializer {

	private static String TYPE_ITERABLE = "iterable";
	private static String TYPE_OBJECT = "object";
	private static String TYPE_FIELD = "field";
	private static String TYPE_REF = "ref";
	private static final HashSet <Class<?>> WRAPPER_TYPES = getWrapperTypes();


	public static void main(String[] args) throws ValidityException, ParsingException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		User u1 = new User("Bjorn", "test", 20, 124124, "test@test.com");
		User u2 = new User("Bjorn", "test", 20, 124124, "test@test.com");
		User u3 = new User("Bjorn", "test", 20, 124124, "test@test.com");
		User u4 = new User("Bjorn", "test", 20, 124124, "test@test.com");

		List<User> users = new ArrayList<User>();
		users.add(u1);
		users.add(u2);
		users.add(u3);
		users.add(u4);

		String test = toXmlSimple(users);

		ArrayList<User> usersReceive = (ArrayList<User>)fromXml(test);

		for(User u: usersReceive) {
			System.out.println(u);
		}

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
        ret.add(ArrayList.class);
        return ret;
	}


	/**
	   * @param obj the type to check.
	   *
	   * @return Returns <code>true</code> if <code>type</code> is a iterable type, <code>false</code> otherwise.
	   */
	  public static boolean isIterable(Object obj) {
	    if ( obj instanceof Class && isIterableClass( ( Class ) obj ) ) {
	      return true;
	    }
	    if ( obj instanceof ParameterizedType ) {
	      return isIterable( ( ( ParameterizedType ) obj ).getRawType() );
	    }
	    if ( obj instanceof WildcardType ) {
	      Type[] upperBounds = ( ( WildcardType ) obj ).getUpperBounds();
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

		  // Create root
		  Element root = new Element(clazz.getName());

		  if (obj instanceof Iterable) {
			  // List<Model>

			  Attribute attr = new Attribute("type", TYPE_ITERABLE);
			  root.addAttribute(attr);

			  for (Object o : (Iterable) obj) {
				  root.appendChild(toXmlSimple(o, true));
			  }

		  } else {
			  // Model


			  Attribute attr = new Attribute("type", TYPE_OBJECT);
			  root.addAttribute(attr); 

			  for(Field field: clazz.getDeclaredFields()){

				  if(!field.isAccessible() && !Modifier.isStatic(field.getModifiers())) {

					  Object value = getFieldValue(obj, field);

					  if(value != null) {
						  Element elem = null;

						  if(value instanceof Iterable) {
							  elem = toXmlSimple(value, true);
							  root.appendChild(elem);
						  } else if (value instanceof Model){
							  elem = toXmlSimple(value, true);
							  elem.addAttribute(new Attribute("id", ((Model)value).getId()));
							  root.appendChild(elem);
						  } else if(isWrapperType(value.getClass())){
							  elem = createFieldElement(field, value);
							  root.appendChild(elem);
						  } 
					  }
				  }
			  }
		  }
		  return root;
	  }

	private static Element createFieldElement(Field field, Object value) {
		Element elem;
		elem = new Element(field.getName());
		  elem.addAttribute(new Attribute("type", TYPE_FIELD));
		  elem.appendChild(value.toString());
		return elem;
	}

	private static Object getFieldValue(Object obj, Field field)
			throws IllegalAccessException {
		field.setAccessible(true);
		  Object value = field.get(obj);
		  field.setAccessible(false);
		return value;
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

		String id = root.getAttributeValue("id");
		String key = typeName + id;
		objects.put(typeName, object);

		Elements elements = root.getChildElements();

		for(int i=0; i<elements.size(); i++) {

			Element element = elements.get(i);
			String type = element.getAttributeValue("type"); 

 			if (type.equals(TYPE_ITERABLE)) {
 				
 				Elements elems = element.getChildElements();
 				
 				for (int j=0; j<elems.size(); j++) {
 					
 					Element elem = elems.get(j);
 					
 					
 					
 				}
 				
 			} else if (type.equals(TYPE_OBJECT)) {

				Object value = fromXml(element, objects);
				String[] parts = element.getQualifiedName().split("\\.");
				String methodName = parts[parts.length-1];

				Method method = findAddMethod(clazz, methodName);

				method.invoke(object, value);

			} else if (type.equals(TYPE_FIELD)) {

				String methodName = element.getQualifiedName();

				methodName = Character.toUpperCase(methodName.charAt(0)) + methodName.substring(1);

				Method method = findSetMethod(clazz, methodName);

				String value = element.getValue();
				System.out.println(methodName);
				method.invoke(object, value);
			} else if (type.equals(TYPE_REF)) {

				id = element.getAttributeValue("id");

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