package earth.xor.learning;

import static org.junit.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Test;

public class TestJsonSimple {
    private String jsonExample = "{ \"url\":\"http://www.foo.org\", "
	    + "\"title\":\"foo\", " + "\"user\":\"user\"}";
    
    @Test
    public void testGettingTheJsonObjectFromString() {
	JSONObject url = (JSONObject) JSONValue.parse(jsonExample);
	
	assertEquals("foo", url.get("title"));
    }
    
    // A JSON-Object is an unordered set of name/value pairs
    @Test
    public void testParsingJsonObjectToString() {
	JSONObject url = new JSONObject();
	
	url.put("title", "foo");
	
	assertEquals("{\"title\":\"foo\"}", url.toJSONString());
    }
}
