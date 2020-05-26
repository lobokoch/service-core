/**********************************************************************************************
Code generated with MKL Plug-in version: 6.10.6
Code generated at time stamp: 2019-07-06T07:24:17.721
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.servicecore.mapper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.Id;

import org.hibernate.Hibernate;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import br.com.kerubin.api.servicecore.annotation.Password;
import static br.com.kerubin.api.servicecore.util.CoreUtils.*;

@Component("kerubin.servicecore.mapper.ObjectMapper")
public class ObjectMapper {
	
	private static Logger log = LoggerFactory.getLogger(ObjectMapper.class);
	
	public static final String PASSWORD = "*******";
	
	private static final List<?> DSL_PRIMITIVE_TYPES = Arrays.asList(LocalDate.class, LocalTime.class, LocalDateTime.class,
				Date.class, Instant.class, String.class, Long.class, 
	            Boolean.class, UUID.class, BigDecimal.class, Double.class,
	            Integer.class);
		
		
		public <T> T map(Object source, Class<T> targetClass) {
			return map(source, targetClass, false);
		}
		
		public <T> T map(Object source, Class<T> targetClass, boolean isEntityToDto) {
	        if (source == null) {
	            return null;
	        }
	        
	        T target = BeanUtils.instantiateClass(targetClass);
	        copyProperties(source, target, isEntityToDto);
	        return target;
	    }
		
		public void copyProperties(Object source, Object target, boolean isEntityToDto) {
	        Map<Object, Object> visited = new HashMap<>();
	        copyProperties(source, target, visited, isEntityToDto);
	    }
		
		public static List<Field> getAllDeclaredFields(Class<?> clazz) {
			
			if (clazz == null) {
				return Collections.emptyList();
			}
			
			Set<Field> fields = new LinkedHashSet<>();
			do {
				fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
				clazz = clazz.getSuperclass();
			} while (clazz != null);
			
			return fields.stream().collect(Collectors.toList());
			
		}
		
	    public static boolean isList(Object obj) {
	    	return obj != null && (obj instanceof Collection);
	    }
		
	    @SuppressWarnings("unchecked")
		protected void copyProperties(Object source, Object target, Map<Object, Object> visited, boolean isEntityToDto) {
	        if (source == null || target == null) {
	            return;
	        }
	        
	        visited.put(source, target);
	        
	        if (isList(source)) {
	        	Collection<Object> sourceList = (Collection<Object>) source;
	        	Collection<Object> targetList = (Collection<Object>) target;
	        	Class<?> targetListItemClass = targetList.iterator().next().getClass();
	        	targetList.clear();
	        	if (isEmpty(sourceList)) {
	        		return;
	        	}
	        	
	        	sourceList.forEach(it -> {
	        		Object targetListItem = BeanUtils.instantiateClass(targetListItemClass);
	        		copyProperties(it, targetListItem, isEntityToDto);
	        		targetList.add(targetListItem);
	        	});
	        	
	        	return;
	        }
	        
	        List<Field> sourceFieds = getAllDeclaredFields(source.getClass());
	        List<Field> targetFieds = getAllDeclaredFields(target.getClass());
	        targetFieds.forEach(targetField -> {
	            Optional<Field> sourceFieldOptional = sourceFieds.stream().filter(it -> it.getName().equals(targetField.getName())).findFirst();
	            if (sourceFieldOptional.isPresent()) {
	                Field sourceField = sourceFieldOptional.get();
	                try {
	                    Class<?> targetFieldType = targetField.getType();
	                    Class<?> sourceFieldType = sourceField.getType();
	                    
	                    if (DSL_PRIMITIVE_TYPES.stream().anyMatch(it -> it.equals(targetFieldType)) || (targetFieldType.isPrimitive() && sourceFieldType.isPrimitive()) ) {
	                    	// Do not permit leave password outside by DTOs
	                    	Object value = (isPasswordField(sourceField) && isEntityToDto) ? PASSWORD : getFieldValue(source, sourceField);
	                    	setFieldValue(target, targetField, value);
	                    }
	                    else if (targetFieldType.isEnum()) {
	                        if (sourceFieldType.isEnum()) {
	                        	Object value = getFieldValue(source, sourceField);
	                            if (value != null) {
		                            String sourceEnumName = ((Enum<?>) value).name();
		                            
		                            @SuppressWarnings({ "rawtypes" })
		                            Enum<?> targetEnumValue = Enum.valueOf( (Class<Enum>) targetFieldType, sourceEnumName);
		                            setFieldValue(target, targetField, targetEnumValue);
	                            }
	                        }
	                        else {
	                        	throw new IllegalStateException("Source \"" + sourceFieldType.getName() + "\" is not a enum.");
	                        }
	                    }
	                    else { // Is another entity reference, call recursive if needed.
							Object sourceFieldValue = getFieldValue(source, sourceField);
							if (isProxy(sourceFieldValue)) {
								sourceFieldValue = Hibernate.unproxy(sourceFieldValue);
							}
							
						    if (sourceFieldValue != null) {
						        Object targetFieldValue = null;
						        if (visited.containsKey(sourceFieldValue)) {
						            targetFieldValue = visited.get(sourceFieldValue);
						        }
						        else {
						        	if (targetFieldType.equals(List.class) || targetFieldType.equals(Set.class)) {
						        		if (targetFieldType.equals(List.class)) {
						        			targetFieldValue = BeanUtils.instantiateClass(ArrayList.class);						        			
						        		} else {
						        			targetFieldValue = BeanUtils.instantiateClass(HashSet.class);	        			
						        		}
						        		ParameterizedType listType = (ParameterizedType) targetField.getGenericType();
						        	    Class<?> listItemClass = (Class<?>) listType.getActualTypeArguments()[0];
						        	    Object listItem = BeanUtils.instantiateClass(listItemClass);
						        	    ((Collection<Object>)targetFieldValue).add(listItem);
						        	}
						        	else {
						        		targetFieldValue = BeanUtils.instantiateClass(targetFieldType);
						        	}
						            
						            // Source field can has an entity name, but with uuid value instead of an entity object
						            // Also, source field and target field can be objects with field names matching, but with different class names.
						            boolean isSourceFieldAnEntityWithOnlyId = sourceFieldType.equals(UUID.class) && !targetFieldType.equals(UUID.class);
						            
						            if (!isSourceFieldAnEntityWithOnlyId) { // both should be objets
						            	copyProperties(sourceFieldValue, targetFieldValue, visited, isEntityToDto);
						            }
						            else {
					            		Field targetFieldValueIdField = getEntityIdField(targetFieldValue.getClass());
					            		if (targetFieldValueIdField == null) {
					            			throw new IllegalStateException(targetField.getClass().getName() + " should be an entity class with a field annoted with @Id.");
					            		}
					            		setFieldValue(targetFieldValue, targetFieldValueIdField, sourceFieldValue);
						            }
						            
						        }
						        
						        setFieldValue(target, targetField, targetFieldValue);
						    }
						}
	                } catch (Exception e) {
	                	throw new IllegalStateException("Error copying properties from '" + source.getClass().getName() + "' to '" + target.getClass().getName() + "'.", e);
	                }
	            }
	            else {
	            	//throw new IllegalStateException("Target field \"" + target.getClass().getName() + "." + targetField.getName() + "\" not found in source \"" + source.getClass().getName() + "\".");
	            }
	        });
	       
	    }
	    
	    private boolean isPasswordField(Field targetField) {
			return targetField != null && targetField.isAnnotationPresent(Password.class);
		}
	
		private boolean isProxy(Object value) {
	        if (value == null) {
	            return false;
	        }
	        if ((value instanceof HibernateProxy) || (value instanceof PersistentCollection)) {
	            return true;
	        }
	        return false;
	    }
	    
	    private Field getEntityIdField(Class<?> entityClass) {
			Field idField = Arrays.asList(entityClass.getDeclaredFields())
					.stream()
					.filter(field -> field.isAnnotationPresent(Id.class))
					.findFirst()
					.orElse(null);
			
			return idField;
		}
	
	    
	    private void setFieldValue(Object obj, Field field, Object value) {
	    	
	    	if (obj == null || field == null) {
	    		log.warn("Calling setFieldValue with obj or field null.");
	    		return;
	    	}
	    	
	    	PropertyDescriptor pd = getPropertyDescriptor(obj, field);
	    	if (pd != null && pd.getWriteMethod() != null) { // Has setter
	    		try {
					pd.getWriteMethod().invoke(obj, value);
				} catch (Exception e) {
					String objStr = obj.getClass().getName();
					String fieldStr = field.getName();
					String str = objStr + "." + fieldStr;
					log.error("Error(1) at setFieldValue object value for: " + str, e);
				}
	    	}  else { // Does not has setter.
	    		try {
	    			field.setAccessible(true);
	    			field.set(obj, value);
				}
				catch(Exception ex) {
					String objStr = obj.getClass().getName();
					String fieldStr = field.getName();
					String str = objStr + "." + fieldStr;
					log.error("Error(2) at setFieldValue object value for: " + str, ex);
				}
	    	}
	    }
	    
	    private Object getFieldValue(Object obj, Field field) {
	    	PropertyDescriptor pd = getPropertyDescriptor(obj, field);
	    	Object value = null;
	    	if (pd != null && pd.getReadMethod() != null) {
	    		try {
					value = pd.getReadMethod().invoke(obj); // Has getter method
				} catch (Exception e) {
					log.error("Erro getting object value: " + e.getMessage(), e);
				}
	    	}  else { // Does not has getter.
	    		try {
	    			field.setAccessible(true);
					value = field.get(obj);
				}
				catch(Exception ex) {
					log.error("Erro getting object value: " + ex.getMessage(), ex);
				}
	    	}
	    	
	    	return value;
	    }
	    
	    private PropertyDescriptor getPropertyDescriptor(Object obj, Field field) {
	    	PropertyDescriptor pd = null;
	    	try {
	    		pd = BeanUtils.getPropertyDescriptor(obj.getClass(), field.getName());
	    	} catch(Exception e) {
	    		log.error("Erro getting property descriptor: " + e.getMessage(), e);
	    	}
	    	return pd;
	    }
	
	
	}

