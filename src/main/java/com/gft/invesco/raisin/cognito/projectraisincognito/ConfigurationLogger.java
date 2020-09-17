package com.gft.invesco.raisin.cognito.projectraisincognito;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

@Component
public class ConfigurationLogger {

    private static final Logger LOG = LoggerFactory.getLogger( ConfigurationLogger.class );

    @Autowired
    private Environment env;

    @PostConstruct
    public void logConfiguration() {
        LOG.info( "********************************************************************************" );
        LOG.info( "* APPLICATION CONFIGURATION" );
        LOG.info( "********************************************************************************" );
        LOG.info( " * Active profiles: {}", Arrays.toString( env.getActiveProfiles() ) );
        LOG.info( " * Found properties:" );
        final MutablePropertySources sources = ( ( AbstractEnvironment ) env ).getPropertySources();
        //noinspection rawtypes
        StreamSupport
            .stream( sources.spliterator(), false )
            .filter( ps -> ps instanceof EnumerablePropertySource )
            .map( ps -> ( ( EnumerablePropertySource ) ps ).getPropertyNames() )
            .flatMap( Arrays::stream )
            .distinct()
            .forEach( prop -> LOG.info( "{}: {}", prop, env.getProperty( prop ) ) );
        LOG.info( "********************************************************************************" );
    }
}
