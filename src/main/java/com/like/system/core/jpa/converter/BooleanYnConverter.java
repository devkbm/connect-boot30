package com.like.system.core.jpa.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanYnConverter  implements AttributeConverter <Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean arg0) {
		return (arg0 == true) ? "Y" : "N";
	}

	@Override
	public Boolean convertToEntityAttribute(String arg0) {
		return (arg0 == "Y") ? true : false;
	}

}
