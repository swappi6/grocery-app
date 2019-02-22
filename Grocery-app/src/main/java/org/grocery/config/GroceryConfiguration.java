package org.grocery.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroceryConfiguration extends Configuration {
    
    private static final Logger logger = LoggerFactory.getLogger(GroceryConfiguration.class);
    
    /**
     * A factory used to connect to a relational database management system.
     * Factories are used by Dropwizard to group together related configuration
     * parameters such as database connection driver, URI, password etc.
     */
    @Valid
    @NotNull
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

    /**
     * A getter for the database factory.
     *
     * @return An instance of database factory deserialized from the
     * configuration file passed as a command-line argument to the application.
     */
    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        logger.info("DB Url " + dataSourceFactory.getUrl());
        logger.info("DB User " + dataSourceFactory.getUser());
        logger.info("DB Properties " + dataSourceFactory.getProperties());
        return dataSourceFactory;
    }
    
    @Valid
    @NotNull
    @Getter
    @Setter
    @JsonProperty("redis")
    private RedisConfiguration redisConfiguration = new RedisConfiguration();
    
}

