package mc.oauth2.config

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter
import org.springframework.boot.context.TypeExcludeFilter
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType

/**
 * @author Michael Chalabine
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@SpringBootConfiguration
@ComponentScan(excludeFilters = [
    ComponentScan.Filter(type = FilterType.CUSTOM,
            classes = arrayOf(TypeExcludeFilter::class)),
    ComponentScan.Filter(type = FilterType.CUSTOM,
            classes = arrayOf(AutoConfigurationExcludeFilter::class))])
annotation class TestApplication

