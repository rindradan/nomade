package org.nomade.load.service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

abstract class BaseService<T> {

    private MongoClient connexion;

    BaseService() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClientOptions options = MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build();
        connexion = new MongoClient("localhost", options);
    }

    MongoDatabase getDatabase() {
        return connexion.getDatabase("nomade_load");
    }

//    MongoCollection getCollection(T t) {
//        return getDatabase().getCollection(t.getClass().getDeclaringClass().getSimpleName(), t.getClass().getDeclaringClass());
//    }

    abstract MongoCollection getCollection();
}
