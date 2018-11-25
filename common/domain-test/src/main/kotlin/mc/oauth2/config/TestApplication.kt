package mc.oauth2.config

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter
import org.springframework.boot.context.TypeExcludeFilter
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * @author Michael Chalabine
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@SpringBootConfiguration
@ExtendWith(SpringExtension::class)
@ComponentScan(excludeFilters = [ComponentScan.Filter(type = FilterType.CUSTOM,
        classes = arrayOf(TypeExcludeFilter::class)), ComponentScan.Filter(type = FilterType.CUSTOM,
        classes = arrayOf(AutoConfigurationExcludeFilter::class))])
annotation class TestApplication

