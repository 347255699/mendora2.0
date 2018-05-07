package org.mendora.kernel.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.vertx.rxjava.ext.asyncsql.AsyncSQLClient;
import io.vertx.rxjava.ext.mongo.MongoClient;
import lombok.Setter;

/**
 * created by:xmf
 * date:2018/5/7
 * description:
 */
public class DataBinder extends AbstractModule {
    @Setter
    private AsyncSQLClient postgreSQLClient;
    @Setter
    private MongoClient mongoClient;

    @Provides
    @Singleton
    public AsyncSQLClient providePostgreSQLClient() {
        return this.postgreSQLClient;
    }

    @Provides
    @Singleton
    public MongoClient provideMongoClient() {
        return mongoClient;
    }
}
