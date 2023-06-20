package cz.istep.javatest.model_serializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import cz.istep.javatest.model.JavaScriptFramework;

public class FrameworkParentItemSerializer extends StdSerializer<JavaScriptFramework> {

	public FrameworkParentItemSerializer() {
		this(null);
	}
	
	public FrameworkParentItemSerializer(Class<JavaScriptFramework> t) {
		super(t);
	}
	
	@Override
	public void serialize(JavaScriptFramework framework, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		List<Integer> ids = new ArrayList<>();
		ids.add(framework.getId().intValue());
		generator.writeObject(ids);
	}

}
