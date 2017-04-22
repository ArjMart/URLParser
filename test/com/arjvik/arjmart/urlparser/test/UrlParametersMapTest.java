package com.arjvik.arjmart.urlparser.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.arjvik.arjmart.urlparser.*;

import org.junit.Test;

public class UrlParametersMapTest {

	@Test
	public void testAddParameter() {
		@SuppressWarnings("unchecked")
		Map<String,ParameterValue> map = (Map<String,ParameterValue>) mock(HashMap.class);
		UrlParametersMap parameters = new UrlParametersMap(map);
		parameters.addParameter("ParameterName", "STRING", "value");
		verify(map).put("ParameterName", new ParameterValue(ParameterType.STRING,"value"));
	}

	@Test
	public void testParameterExistsForRealParam() {
		@SuppressWarnings("unchecked")
		Map<String,ParameterValue> map = (Map<String,ParameterValue>) mock(HashMap.class);
		when(map.containsKey("ParameterName")).thenReturn(true);
		UrlParametersMap parameters = new UrlParametersMap(map);
		parameters.addParameter("ParameterName", "STRING", "value");
		assertTrue("ParameterMap.parameterExists should return true for items that exist",parameters.parameterExists("ParameterName"));
		verify(map).containsKey("ParameterName");
	}
	
	@Test
	public void testParameterExistsForNonexistingParam() {
		Map<String,ParameterValue> map = new MockMap();
		UrlParametersMap parameters = new UrlParametersMap(map);
		assertEquals("ParameterMap.parameterExists should return false for items that don't exist",map.containsKey("DoesntExists"),parameters.parameterExists("DoesntExist"));
	}

	@Test
	public void testGetStringOnString() {
		Map<String,ParameterValue> map = new MockMap();
		UrlParametersMap parameters = new UrlParametersMap(map);
		parameters.addParameter("ParameterName", "STRING", "value");
		assertEquals("ParameterMap.getString should return the right string",parameters.getString("ParameterName"),"value");
	}
	
	@Test(expected=IncompatibleParameterTypeException.class)
	public void testGetStringOnInt() throws IncompatibleParameterTypeException{
		Map<String,ParameterValue> map = new MockMap();
		UrlParametersMap parameters = new UrlParametersMap(map);
		parameters.addParameter("ParameterName", "INT", "1");
		parameters.getString("ParameterName");
		fail("ParameterMap.getString should throw an error if wrong type");
	}
	
	@Test
	public void testGetIntOnInt() {
		Map<String,ParameterValue> map = new MockMap();
		UrlParametersMap parameters = new UrlParametersMap(map);
		parameters.addParameter("ParameterName", "INT", 1);
		assertEquals("ParameterMap.getInt should return the right int",parameters.getInt("ParameterName"),1);
	}
	
	@Test(expected=IncompatibleParameterTypeException.class)
	public void testGetIntOnString() throws IncompatibleParameterTypeException{
		Map<String,ParameterValue> map = new MockMap();
		UrlParametersMap parameters = new UrlParametersMap(map);
		parameters.addParameter("ParameterName", "STRING", "value");
		parameters.getInt("ParameterName");
		fail("ParameterMap.getInt should throw an error if wrong type");
	}

	@Test
	public void testGetBooleanOnBooolean() {
		Map<String,ParameterValue> map = new MockMap();
		UrlParametersMap parameters = new UrlParametersMap(map);
		parameters.addParameter("ParameterName", "BOOLEAN", true);
		assertEquals("ParameterMap.getBoolean should return the right boolean",parameters.getBoolean("ParameterName"),true);
	}
	
	@Test(expected=IncompatibleParameterTypeException.class)
	public void testGetBooleanOnString() throws IncompatibleParameterTypeException{
		Map<String,ParameterValue> map = new MockMap();
		UrlParametersMap parameters = new UrlParametersMap(map);
		parameters.addParameter("ParameterName", "STRING", "value");
		parameters.getBoolean("ParameterName");
		fail("ParameterMap.getBoolean should throw an error if wrong type");
	}
	
	private static class MockMap implements Map<String,ParameterValue>{
		HashMap<String,ParameterValue> map;
		
		public MockMap(){
			map = new HashMap<>();
		}
		
		@Override
		public ParameterValue put(String key, ParameterValue value) {
			return map.put(key,value);
		}
		
		@Override
		public boolean containsKey(Object key) {
			return map.containsKey(key);
		}
		
		@Override public ParameterValue get(Object key) {
			return map.get(key);
		}
		@Override public void clear() {}
		@Override public boolean containsValue(Object value) {return false;}
		@Override public Set<java.util.Map.Entry<String, ParameterValue>> entrySet() {return null;}
		@Override public boolean isEmpty() {return false;}
		@Override public Set<String> keySet() {return null;}
		@Override public void putAll(Map<? extends String, ? extends ParameterValue> m) {}
		@Override public ParameterValue remove(Object key) {return null;}
		@Override public int size() {return 0;}
		@Override public Collection<ParameterValue> values() {return null;}
	}
	
}