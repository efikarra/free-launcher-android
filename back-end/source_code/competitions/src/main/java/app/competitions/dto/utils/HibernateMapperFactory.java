package app.competitions.dto.utils;

import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.unenhance.HibernateUnenhanceStrategy;

public class HibernateMapperFactory extends DefaultMapperFactory {
    public HibernateMapperFactory(MapperFactoryBuilder<?, ?> builder) {
        super(builder);
    }

    public static abstract class IfInitializedHibernateMapperFactoryBuilder<F extends DefaultMapperFactory, B extends MapperFactoryBuilder<F, B>> extends MapperFactoryBuilder<F, B> {
        public IfInitializedHibernateMapperFactoryBuilder() {
            super();
            codeGenerationStrategy = new IfInitializedHibernateCGS();
            unenhanceStrategy=new HibernateUnenhanceStrategy();
        }
    }

    public static class Builder extends IfInitializedHibernateMapperFactoryBuilder<HibernateMapperFactory, Builder> {
        @Override
        public HibernateMapperFactory build() {
            return new HibernateMapperFactory(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}