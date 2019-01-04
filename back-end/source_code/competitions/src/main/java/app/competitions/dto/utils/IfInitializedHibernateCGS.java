package app.competitions.dto.utils;

import java.util.ArrayList;
import java.util.List;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.generator.AggregateSpecification;
import ma.glasnost.orika.impl.generator.CodeGenerationStrategy;
import ma.glasnost.orika.impl.generator.Specification;
import ma.glasnost.orika.impl.generator.specification.AnyTypeToString;
import ma.glasnost.orika.impl.generator.specification.ApplyRegisteredMapper;
import ma.glasnost.orika.impl.generator.specification.ArrayOrCollectionToArray;
import ma.glasnost.orika.impl.generator.specification.ArrayOrCollectionToCollection;
import ma.glasnost.orika.impl.generator.specification.ArrayOrCollectionToMap;
import ma.glasnost.orika.impl.generator.specification.Convert;
import ma.glasnost.orika.impl.generator.specification.CopyByReference;
import ma.glasnost.orika.impl.generator.specification.EnumToEnum;
import ma.glasnost.orika.impl.generator.specification.MapToArray;
import ma.glasnost.orika.impl.generator.specification.MapToCollection;
import ma.glasnost.orika.impl.generator.specification.MapToMap;
import ma.glasnost.orika.impl.generator.specification.MultiOccurrenceElementToObject;
import ma.glasnost.orika.impl.generator.specification.MultiOccurrenceToMultiOccurrence;
import ma.glasnost.orika.impl.generator.specification.ObjectToMultiOccurrenceElement;
import ma.glasnost.orika.impl.generator.specification.ObjectToObject;
import ma.glasnost.orika.impl.generator.specification.PrimitiveAndObject;
import ma.glasnost.orika.impl.generator.specification.StringToEnum;
import ma.glasnost.orika.impl.generator.specification.StringToStringConvertible;
import ma.glasnost.orika.impl.generator.specification.UnmappableEnum;

public class IfInitializedHibernateCGS implements CodeGenerationStrategy {

    private List<Specification> specs = new ArrayList<Specification>();
    private final ArrayList<AggregateSpecification> aggregateSpecifications;
    
    public IfInitializedHibernateCGS() {
        super();
        specs.add(new IfInitializedHibernateSpecification(new Convert()));
        specs.add(new IfInitializedHibernateSpecification(new CopyByReference()));
        specs.add(new IfInitializedHibernateSpecification(new ApplyRegisteredMapper()));
        specs.add(new IfInitializedHibernateSpecification(new EnumToEnum()));
        specs.add(new IfInitializedHibernateSpecification(new StringToEnum()));
        specs.add(new IfInitializedHibernateSpecification(new UnmappableEnum()));
        specs.add(new IfInitializedHibernateSpecification(new ArrayOrCollectionToArray()));
        specs.add(new IfInitializedHibernateSpecification(new ArrayOrCollectionToCollection()));
        specs.add(new IfInitializedHibernateSpecification(new MapToMap()));
        specs.add(new IfInitializedHibernateSpecification(new MapToArray()));
        specs.add(new IfInitializedHibernateSpecification(new MapToCollection()));
        specs.add(new IfInitializedHibernateSpecification(new ArrayOrCollectionToMap()));
        specs.add(new IfInitializedHibernateSpecification(new StringToStringConvertible()));
        specs.add(new IfInitializedHibernateSpecification(new AnyTypeToString()));
        specs.add(new IfInitializedHibernateSpecification(new MultiOccurrenceElementToObject()));
        specs.add(new IfInitializedHibernateSpecification(new ObjectToMultiOccurrenceElement()));
        specs.add(new IfInitializedHibernateSpecification(new PrimitiveAndObject()));
        specs.add(new IfInitializedHibernateSpecification(new ObjectToObject()));
        
        this.aggregateSpecifications = new ArrayList<AggregateSpecification>();
        aggregateSpecifications.add(new MultiOccurrenceToMultiOccurrence());
    }



    public void setMapperFactory(MapperFactory mapperFactory) {
        for (Specification spec: this.specs) {
            spec.setMapperFactory(mapperFactory);
        }
        for (AggregateSpecification spec: this.aggregateSpecifications) {
            spec.setMapperFactory(mapperFactory);
        }
    }

   

    /* (non-Javadoc)
     * @see ma.glasnost.orika.impl.generator.CodeGenerationStrategy#getSpecifications()
     */
    public List<Specification> getSpecifications() {
        return specs;
    }

    /* (non-Javadoc)
     * @see ma.glasnost.orika.impl.generator.CodeGenerationStrategy#addAggregateSpecification(ma.glasnost.orika.impl.generator.AggregateSpecification, ma.glasnost.orika.impl.generator.CodeGenerationStrategy.Position, ma.glasnost.orika.impl.generator.AggregateSpecification)
     */
    public void addAggregateSpecification(AggregateSpecification spec, Position relativePosition, Class<AggregateSpecification> relativeSpec) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see ma.glasnost.orika.impl.generator.CodeGenerationStrategy#getAggregateSpecifications()
     */
    public List<AggregateSpecification> getAggregateSpecifications() {
        return aggregateSpecifications;
    }



    public void addSpecification(Specification spec, Position relativePosition, Class<Specification> relativeSpec) {
        throw new UnsupportedOperationException();
    }
    
}
