package io.github.vm.patlego.graphql.datafetcher;

import javax.annotation.Nonnull;

import graphql.schema.DataFetcher;

public interface DataFetcheEntry<T> {

    /**
     * Creates a DataFetcher for a given object and can be incorporated within the{@link graphql.schema.idl.RuntimeWiring}
     * @return Returns a {@link graphql.schema.DataFetcher} and can be incorporated within the Runtime envrionment
     * @throws Exception - Failed to create the necessary DataFetcher
     */
    public @Nonnull DataFetcher<T> get() throws Exception;
}