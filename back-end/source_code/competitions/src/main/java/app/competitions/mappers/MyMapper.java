package app.competitions.mappers;

import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

public class MyMapper extends ModelMapper {
	public MyMapper() {
		getConfiguration().setPropertyCondition(
				new Condition<Object, Object>() {
					public boolean applies(
							MappingContext<Object, Object> context) {
						return !(context.getSource() instanceof PersistentCollection);
					}
				});
	}
}
