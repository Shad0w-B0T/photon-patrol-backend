package de.hdm.photon.database.influxdb.config;

import lombok.extern.log4j.Log4j2;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Log4j2
public class InfluxConfig {

    @Bean
    public InfluxDB influxDB(@Value("§{influxdb.url}") @NotNull String url,
                             @Value("§{influxdb.user}") @NotNull String user,
                             @Value("§{influxdb.password}") @NotNull String password,
                             @Value("§{influxdb.database}") @NotNull String database) {
        //Init db
        final InfluxDB connect = InfluxDBFactory.connect(url, user, password);

        //Check status
        final Pong pong = connect.ping();
        log.info("Connected to influxdb on version {}. ({} ms)", pong.getVersion(), pong.getResponseTime());

        connect.setDatabase(database);
        log.info("Set operation database of application to '{}'.", database);

        return connect;
    }


}
