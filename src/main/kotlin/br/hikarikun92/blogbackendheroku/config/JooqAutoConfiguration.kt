package br.hikarikun92.blogbackendheroku.config

import io.r2dbc.spi.ConnectionFactory
import org.jooq.*
import org.jooq.conf.Settings
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer
import org.springframework.boot.autoconfigure.jooq.JooqProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(DSLContext::class)
@EnableConfigurationProperties(JooqProperties::class)
class JooqAutoConfiguration {
    @Bean
    fun jooqConfiguration(
        properties: JooqProperties, connectionFactory: ConnectionFactory,
        executeListenerProviders: ObjectProvider<ExecuteListenerProvider>,
        configurationCustomizers: ObjectProvider<DefaultConfigurationCustomizer>
    ): DefaultConfiguration {
        val configuration = DefaultConfiguration()
        configuration.setSQLDialect(properties.sqlDialect)
        configuration.setConnectionFactory(connectionFactory)
        configuration.set(
            *executeListenerProviders.orderedStream().toArray { arrayOfNulls<ExecuteListenerProvider>(it) })
        configurationCustomizers.orderedStream().forEach { it.customize(configuration) }
        return configuration
    }

    @Bean
    @Order(0)
    @Deprecated("")
    fun jooqProvidersDefaultConfigurationCustomizer(
        transactionProvider: ObjectProvider<TransactionProvider?>,
        recordMapperProvider: ObjectProvider<RecordMapperProvider?>,
        recordUnmapperProvider: ObjectProvider<RecordUnmapperProvider?>,
        settings: ObjectProvider<Settings?>,
        recordListenerProviders: ObjectProvider<RecordListenerProvider?>,
        visitListenerProviders: ObjectProvider<VisitListenerProvider?>,
        transactionListenerProviders: ObjectProvider<TransactionListenerProvider?>,
        executorProvider: ObjectProvider<ExecutorProvider?>
    ): DefaultConfigurationCustomizer {
        return DefaultConfigurationCustomizer { configuration ->
            transactionProvider.ifAvailable { configuration.set(it) }
            recordMapperProvider.ifAvailable { configuration.set(it) }
            recordUnmapperProvider.ifAvailable { configuration.set(it) }
            settings.ifAvailable { configuration.set(it) }
            executorProvider.ifAvailable { configuration.set(it) }
            configuration.set(
                *recordListenerProviders.orderedStream()
                    .toArray<RecordListenerProvider> { arrayOfNulls<RecordListenerProvider>(it) })
            configuration.set(
                *visitListenerProviders.orderedStream()
                    .toArray<VisitListenerProvider> { arrayOfNulls<VisitListenerProvider>(it) })
            configuration.setTransactionListenerProvider(
                *transactionListenerProviders.orderedStream()
                    .toArray { arrayOfNulls<TransactionListenerProvider>(it) })
        }
    }

    @Bean
    fun dslContext(configuration: org.jooq.Configuration) = DefaultDSLContext(configuration)
}