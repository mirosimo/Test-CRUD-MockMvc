package cz.istep.javatest.model_serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import cz.istep.javatest.model.JavaScriptFramework;

public class FrameworkParentDeserializer extends StdDeserializer<JavaScriptFramework> {

    public FrameworkParentDeserializer() {
        this(null);
    }

    public FrameworkParentDeserializer(Class<?> vc) {
        super(vc);
    }

	@Override
	public JavaScriptFramework deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		
		return new JavaScriptFramework();
	}


}


