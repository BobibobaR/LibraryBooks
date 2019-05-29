package telran.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Cover {
SOLID(0),
SOFT(1);
	
	private Integer id;
	
	public static Cover getByid(Integer id) {
		if(id == null) throw new IllegalArgumentException("Cover cannot be null");
		
		for (Cover cover : values()) {
			if(cover.getId().equals(id))
				return cover;
			
		}
		return null;
	}
	
}
