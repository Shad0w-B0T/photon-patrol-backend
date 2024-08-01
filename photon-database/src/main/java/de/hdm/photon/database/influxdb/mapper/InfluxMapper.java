package de.hdm.photon.database.influxdb.mapper;

import org.springframework.stereotype.Component;

/**
 * {@code InfluxMapper} is a Spring-managed component that extends {@link AbstractInfluxMapper}.
 * <p>
 * This class serves as a concrete implementation of {@link AbstractInfluxMapper} for mapping
 * data between InfluxDB and the application’s domain model. It is responsible for translating
 * data from the InfluxDB format to the application format and vice versa.
 * </p>
 * <p>
 * As a {@link Component}, {@code InfluxMapper} is automatically managed by the Spring container,
 * allowing it to be injected into other components and services as needed.
 * </p>
 */
@Component
public final class InfluxMapper extends AbstractInfluxMapper {
}
