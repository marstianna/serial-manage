/*
 * 
 */
package com.hotpot.helper;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.DefaultAopProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

import static org.springframework.util.Assert.notNull;
import static org.springframework.util.ObjectUtils.isEmpty;
import static org.springframework.util.StringUtils.hasLength;
import static org.springframework.util.StringUtils.tokenizeToStringArray;

// TODO: Auto-generated Javadoc

/**
 * {@code FactoryBean} that creates an MyBatis {@code SqlSessionFactory}.
 * This is the usual way to set up a shared MyBatis {@code SqlSessionFactory} in a Spring application context;
 * the SqlSessionFactory can then be passed to MyBatis-based DAOs via dependency injection.
 * 
 * Either {@code DataSourceTransactionManager} or {@code JtaTransactionManager} can be used for transaction
 * demarcation in combination with a {@code SqlSessionFactory}. JTA should be used for transactions
 * which span multiple databases or when container managed transactions (CMT) are being used.
 *
 * @version $Id$
 * @see #setConfigLocation
 * @see #setDataSource
 */
public class OverridableSqlSessionFactoryBean implements FactoryBean<SqlSessionFactory>, InitializingBean,
        ApplicationListener<ApplicationEvent> {

    /** The logger. */
    private final Log logger = LogFactory.getLog(getClass());

    /** The config location. */
    private Resource configLocation;

    /** The mapper locations. */
    private Resource[] mapperLocations;

    /** The data source. */
    private DataSource dataSource;

    /** The transaction factory. */
    private TransactionFactory transactionFactory;

    /** The configuration properties. */
    private Properties configurationProperties;

    /** The sql session factory builder. */
    private SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

    /** The sql session factory. */
    private SqlSessionFactory sqlSessionFactory;

    /** The environment. */
    private String environment = SqlSessionFactoryBean.class.getSimpleName(); // EnvironmentAware requires spring 3.1

    /** The fail fast. */
    private boolean failFast;

    /** The plugins. */
    private Interceptor[] plugins;

    /** The type handlers. */
    private TypeHandler<?>[] typeHandlers;

    /** The type handlers package. */
    private String typeHandlersPackage;

    /** The type aliases. */
    private Class<?>[] typeAliases;

    /** The type aliases package. */
    private String typeAliasesPackage;

    /** The type aliases super type. */
    private Class<?> typeAliasesSuperType;

    /** The database id provider. */
    private DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();

    /** The object factory. */
    private ObjectFactory objectFactory;

    /** The object wrapper factory. */
    private ObjectWrapperFactory objectWrapperFactory;

    /**
     * Sets the ObjectFactory.
     *
     * @param objectFactory the new object factory
     * @since 1.1.2
     */
    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    /**
     * Sets the ObjectWrapperFactory.
     *
     * @param objectWrapperFactory the new object wrapper factory
     * @since 1.1.2
     */
    public void setObjectWrapperFactory(ObjectWrapperFactory objectWrapperFactory) {
        this.objectWrapperFactory = objectWrapperFactory;
    }

    /**
     * Sets the DatabaseIdProvider.
     *
     * @return the database id provider
     * @since 1.1.0
     */
    public DatabaseIdProvider getDatabaseIdProvider() {
        return databaseIdProvider;
    }

    /**
     * Gets the DatabaseIdProvider.
     *
     * @param databaseIdProvider the new database id provider
     * @since 1.1.0
     */
    public void setDatabaseIdProvider(DatabaseIdProvider databaseIdProvider) {
        this.databaseIdProvider = databaseIdProvider;
    }

    /**
     * Mybatis plugin list.
     *
     * @param plugins            list of plugins
     * @since 1.0.1
     */
    public void setPlugins(Interceptor[] plugins) {
        this.plugins = plugins;
    }

    /**
     * Packages to search for type aliases.
     *
     * @param typeAliasesPackage            package to scan for domain objects
     * @since 1.0.1
     */
    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    /**
     * Super class which domain objects have to extend to have a type alias created.
     * No effect if there is no package to scan configured.
     *
     * @param typeAliasesSuperType            super class for domain objects
     * @since 1.1.2
     */
    public void setTypeAliasesSuperType(Class<?> typeAliasesSuperType) {
        this.typeAliasesSuperType = typeAliasesSuperType;
    }

    /**
     * Packages to search for type handlers.
     *
     * @param typeHandlersPackage            package to scan for type handlers
     * @since 1.0.1
     */
    public void setTypeHandlersPackage(String typeHandlersPackage) {
        this.typeHandlersPackage = typeHandlersPackage;
    }

    /**
     * Set type handlers. They must be annotated with {@code MappedTypes} and optionally with {@code MappedJdbcTypes}
     *
     * @param typeHandlers            Type handler list
     * @since 1.0.1
     */
    public void setTypeHandlers(TypeHandler<?>[] typeHandlers) {
        this.typeHandlers = typeHandlers;
    }

    /**
     * List of type aliases to register. They can be annotated with {@code Alias}
     *
     * @param typeAliases            Type aliases list
     * @since 1.0.1
     */
    public void setTypeAliases(Class<?>[] typeAliases) {
        this.typeAliases = typeAliases;
    }

    /**
     * If true, a final check is done on Configuration to assure that all mapped
     * statements are fully loaded and there is no one still pending to resolve
     * includes. Defaults to false.
     *
     * @param failFast            enable failFast
     * @since 1.0.1
     */
    public void setFailFast(boolean failFast) {
        this.failFast = failFast;
    }

    /**
     * Set the location of the MyBatis {@code SqlSessionFactory} config file. A typical value is
     * "WEB-INF/mybatis-configuration.xml".
     *
     * @param configLocation the new config location
     */
    public void setConfigLocation(Resource configLocation) {
        this.configLocation = configLocation;
    }

    /**
     * Set locations of MyBatis mapper files that are going to be merged into the {@code SqlSessionFactory}
     * configuration at runtime.
     * 
     * This is an alternative to specifying "&lt;sqlmapper&gt;" entries in an MyBatis config file.
     * This property being based on Spring's resource abstraction also allows for specifying
     * resource patterns here: e.g. "classpath*:sqlmap/*-mapper.xml".
     *
     * @param mapperLocations the new mapper locations
     */
    public void setMapperLocations(Resource[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    /**
     * Set optional properties to be passed into the SqlSession configuration, as alternative to a
     * {@code &lt;properties&gt;} tag in the configuration xml file. This will be used to
     * resolve placeholders in the config file.
     *
     * @param sqlSessionFactoryProperties the new configuration properties
     */
    public void setConfigurationProperties(Properties sqlSessionFactoryProperties) {
        this.configurationProperties = sqlSessionFactoryProperties;
    }

    /**
     * Set the JDBC {@code DataSource} that this instance should manage transactions for. The {@code DataSource} should
     * match the one used by the {@code SqlSessionFactory}: for example, you could specify the same
     * JNDI DataSource for both.
     * 
     * A transactional JDBC {@code Connection} for this {@code DataSource} will be provided to application code
     * accessing this {@code DataSource} directly via {@code DataSourceUtils} or {@code DataSourceTransactionManager}.
     * 
     * The {@code DataSource} specified here should be the target {@code DataSource} to manage transactions for, not
     * a {@code TransactionAwareDataSourceProxy}. Only data access code may work with
     * {@code TransactionAwareDataSourceProxy}, while the transaction manager needs to work on the
     * underlying target {@code DataSource}. If there's nevertheless a {@code TransactionAwareDataSourceProxy} passed
     * in, it will be unwrapped to extract its target {@code DataSource}.
     *
     * @param dataSource the new data source
     */
    public void setDataSource(DataSource dataSource) {
        if (dataSource instanceof TransactionAwareDataSourceProxy) {
            // If we got a TransactionAwareDataSourceProxy, we need to perform
            // transactions for its underlying target DataSource, else data
            // access code won't see properly exposed transactions (i.e.
            // transactions for the target DataSource).
            this.dataSource = ((TransactionAwareDataSourceProxy) dataSource).getTargetDataSource();
        } else {
            this.dataSource = dataSource;
        }
    }

    /**
     * Sets the {@code SqlSessionFactoryBuilder} to use when creating the {@code SqlSessionFactory}.
     * 
     * This is mainly meant for testing so that mock SqlSessionFactory classes can be injected. By
     * default, {@code SqlSessionFactoryBuilder} creates {@code DefaultSqlSessionFactory} instances.
     *
     * @param sqlSessionFactoryBuilder the new sql session factory builder
     */
    public void setSqlSessionFactoryBuilder(SqlSessionFactoryBuilder sqlSessionFactoryBuilder) {
        this.sqlSessionFactoryBuilder = sqlSessionFactoryBuilder;
    }

    /**
     * Set the MyBatis TransactionFactory to use. Default is {@code SpringManagedTransactionFactory}
     * 
     * The default {@code SpringManagedTransactionFactory} should be appropriate for all cases:
     * be it Spring transaction management, EJB CMT or plain JTA. If there is no active transaction,
     * SqlSession operations will execute SQL statements non-transactionally.
     * 
     * <b>It is strongly recommended to use the default {@code TransactionFactory}.</b> If not used, any
     * attempt at getting an SqlSession through Spring's MyBatis framework will throw an exception if
     * a transaction is active.
     *
     * @param transactionFactory            the MyBatis TransactionFactory
     * @see SpringManagedTransactionFactory
     */
    public void setTransactionFactory(TransactionFactory transactionFactory) {
        this.transactionFactory = transactionFactory;
    }

    /**
     * <b>NOTE:</b> This class <em>overrides</em> any {@code Environment} you have set in the MyBatis
     * config file. This is used only as a placeholder name. The default value is
     * {@code SqlSessionFactoryBean.class.getSimpleName()}.
     *
     * @param environment
     *            the environment name
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    /**
     * {@inheritDoc}
     */
    public void afterPropertiesSet() throws Exception {
        notNull(dataSource, "Property 'dataSource' is required");
        notNull(sqlSessionFactoryBuilder, "Property 'sqlSessionFactoryBuilder' is required");

        this.sqlSessionFactory = buildSqlSessionFactory();
    }

    /**
     * Build a {@code SqlSessionFactory} instance.
     *
     * The default implementation uses the standard MyBatis {@code XMLConfigBuilder} API to build a
     * {@code SqlSessionFactory} instance based on an Reader.
     *
     * @return SqlSessionFactory
     * @throws java.io.IOException
     *             if loading the config file failed
     */
    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {

        Configuration configuration;

        XMLConfigBuilder xmlConfigBuilder = null;
        if (this.configLocation != null) {
            xmlConfigBuilder = new XMLConfigBuilder(this.configLocation.getInputStream(), null,
                    this.configurationProperties);
            configuration = xmlConfigBuilder.getConfiguration();
        } else {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Property 'configLocation' not specified, using default MyBatis Configuration");
            }
            configuration = new Configuration();
            configuration.setVariables(this.configurationProperties);
        }

        final Collection<String> statementNames = configuration.getMappedStatementNames();
        
        // Proxy Configurations
        AdvisedSupport support = new AdvisedSupport();
        support.setProxyTargetClass(true);
        support.setTarget(configuration);
        support.setOptimize(true);
        support.addAdvice(new MethodInterceptor() {
            public Object invoke(MethodInvocation invocation) throws Throwable {
                if (invocation.getMethod().getName().equals("addMappedStatement")) {
                    MappedStatement statement = (MappedStatement) invocation.getArguments()[0];
                    if (statementNames.contains(statement.getId())) {
                        return ObjectUtils.NULL;
                    }
                }
                return invocation.proceed();
            }
        });
        DefaultAopProxyFactory fa = new DefaultAopProxyFactory();
        try {
            AopProxy aopProxy = fa.createAopProxy(support);
            configuration = (Configuration) aopProxy.getProxy();
        } catch (AopConfigException e) {
            e.printStackTrace();
        }

        if (this.objectFactory != null) {
            configuration.setObjectFactory(this.objectFactory);
        }

        if (this.objectWrapperFactory != null) {
            configuration.setObjectWrapperFactory(this.objectWrapperFactory);
        }

        if (hasLength(this.typeAliasesPackage)) {
            String[] typeAliasPackageArray = tokenizeToStringArray(this.typeAliasesPackage,
                    ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            for (String packageToScan : typeAliasPackageArray) {
                configuration.getTypeAliasRegistry().registerAliases(packageToScan,
                        typeAliasesSuperType == null ? Object.class : typeAliasesSuperType);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Scanned package: '" + packageToScan + "' for aliases");
                }
            }
        }

        if (!isEmpty(this.typeAliases)) {
            for (Class<?> typeAlias : this.typeAliases) {
                configuration.getTypeAliasRegistry().registerAlias(typeAlias);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Registered type alias: '" + typeAlias + "'");
                }
            }
        }

        if (!isEmpty(this.plugins)) {
            for (Interceptor plugin : this.plugins) {
                configuration.addInterceptor(plugin);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Registered plugin: '" + plugin + "'");
                }
            }
        }

        if (hasLength(this.typeHandlersPackage)) {
            String[] typeHandlersPackageArray = tokenizeToStringArray(this.typeHandlersPackage,
                    ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            for (String packageToScan : typeHandlersPackageArray) {
                configuration.getTypeHandlerRegistry().register(packageToScan);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Scanned package: '" + packageToScan + "' for type handlers");
                }
            }
        }

        if (!isEmpty(this.typeHandlers)) {
            for (TypeHandler<?> typeHandler : this.typeHandlers) {
                configuration.getTypeHandlerRegistry().register(typeHandler);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Registered type handler: '" + typeHandler + "'");
                }
            }
        }

        if (xmlConfigBuilder != null) {
            try {
                xmlConfigBuilder.parse();

                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Parsed configuration file: '" + this.configLocation + "'");
                }
            } catch (Exception ex) {
                throw new NestedIOException("Failed to parse config resource: " + this.configLocation, ex);
            } finally {
                ErrorContext.instance().reset();
            }
        }

        if (this.transactionFactory == null) {
            this.transactionFactory = new SpringManagedTransactionFactory();
        }

        Environment environment = new Environment(this.environment, this.transactionFactory, this.dataSource);
        configuration.setEnvironment(environment);

        if (this.databaseIdProvider != null) {
            try {
                configuration.setDatabaseId(this.databaseIdProvider.getDatabaseId(this.dataSource));
            } catch (SQLException e) {
                throw new NestedIOException("Failed getting a databaseId", e);
            }
        }

        if (!isEmpty(this.mapperLocations)) {
            for (Resource mapperLocation : this.mapperLocations) {
                if (mapperLocation == null) {
                    continue;
                }

                try {
                    XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperLocation.getInputStream(),
                            configuration, mapperLocation.toString(), configuration.getSqlFragments());
                    xmlMapperBuilder.parse();
                } catch (Exception e) {
                    throw new NestedIOException("Failed to parse mapping resource: '" + mapperLocation + "'", e);
                } finally {
                    ErrorContext.instance().reset();
                }

                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Parsed mapper file: '" + mapperLocation + "'");
                }
            }
        } else {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Property 'mapperLocations' was not specified or no matching resources found");
            }
        }

        return this.sqlSessionFactoryBuilder.build(configuration);
    }

    /**
     * {@inheritDoc}
     */
    public SqlSessionFactory getObject() throws Exception {
        if (this.sqlSessionFactory == null) {
            afterPropertiesSet();
        }

        return this.sqlSessionFactory;
    }

    /**
     * {@inheritDoc}
     */
    public Class<? extends SqlSessionFactory> getObjectType() {
        return this.sqlSessionFactory == null ? SqlSessionFactory.class : this.sqlSessionFactory.getClass();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isSingleton() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public void onApplicationEvent(ApplicationEvent event) {
        if (failFast && event instanceof ContextRefreshedEvent) {
            // fail-fast -> check all statements are completed
            this.sqlSessionFactory.getConfiguration().getMappedStatementNames();
        }
    }

}
