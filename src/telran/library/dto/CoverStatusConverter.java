package telran.library.dto;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
@Converter(autoApply = true)
public class CoverStatusConverter implements AttributeConverter<Cover, Integer>{

	@Override
	public Integer convertToDatabaseColumn(Cover cover) {
		
		return cover == null?null:cover.getId();
	}

	@Override
	public Cover convertToEntityAttribute(Integer dbData) {
		return dbData == null?null:Cover.getByid(dbData);
	}

}
