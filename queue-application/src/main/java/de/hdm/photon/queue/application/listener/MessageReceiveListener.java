package de.hdm.photon.queue.application.listener;

import de.hdm.photon.logic.hardware.IHardwareManager;
import de.hdm.photon.logic.measurement.IMeasurementManager;
import de.hdm.photon.queue.logic.event.MessageReceiveEvent;
import de.hdm.photon.schema.exception.auth.FormatMismatchException;
import de.hdm.photon.schema.exception.entity.EntityNotPresentException;
import de.hdm.photon.schema.hardware.IHardware;
import de.hdm.photon.schema.payload.IMessagePayload;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Component
@Log4j2
public final class MessageReceiveListener {

  @Autowired
  private IHardwareManager hardwareManager;

  @Autowired
  private IMeasurementManager measurementManager;

  @EventListener
  public void handle(@NotNull final MessageReceiveEvent event) {
    final IMessagePayload messagePayload = event.messagePayload();
    final String token = messagePayload.token();
    @Nullable final String mac = messagePayload.macAddress();
    final Map<String, Object> valueMap = messagePayload.values();

    try {
      final IHardware hardware = this.hardwareManager.getHardware(token);

      if (hardware.macAddress() != null && !hardware.macAddress().equals(mac)) {
        log.warn("Mismatch of mac address for token={}.", token);
        return;
      }

      if (hardware.macAddress() == null) {
        this.hardwareManager.updateMacAddress(hardware.id(), mac);
        log.info("Set mac address for token={}.", token);
      }

      if (!hardware.tokenIsActive()) {
        log.info("Token={} is deactivated.", token);
        return;
      }

      final Map<String, Object> influxValues = new HashMap<>(valueMap);
      final int resolution = (int) Math.pow(2., messagePayload.bitResolution());

      Optional
          .ofNullable(influxValues.get("v"))
          .map(object -> (int) object)
          .map(integer -> voltage(integer, resolution, 25.0))
          .ifPresent(aDouble -> influxValues.put("v", aDouble));

      Optional
          .ofNullable(influxValues.get("c"))
          .map(object -> (int) object)
          .map(integer -> current(integer, resolution, 5.0))
          .ifPresent(aDouble -> influxValues.put("c", aDouble));

      //P = U*I
      if (influxValues.get("v") instanceof Double voltage && influxValues.get("c") instanceof Double current) {
        influxValues.put("p", voltage*(current/1000));
      }

      this.measurementManager.addMeasurements(token, influxValues);
      log.info("Added data for token={} with a amount of key={}.", token, valueMap.size());
    } catch (final EntityNotPresentException | FormatMismatchException exception) {
      System.out.println(exception.getMessage());
    }
  }

  private double percentage(int value, int resolution) {
    return ((double) value)/ (resolution-1);
  }

  private double voltage(int value, int resolution, double referenceVoltage) {
    return ((double) value/(resolution-1))*referenceVoltage;
  }

  //185mV/A
  private double current(int value, int resolution, double referenceVoltage) {
    final double voltage = this.voltage(value, resolution, referenceVoltage);

    return (voltage-(referenceVoltage/2.))/0.185;
  }

}
