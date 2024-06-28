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

import java.util.Map;


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

      this.measurementManager.addMeasurements(token, valueMap);
      log.info("Added data for token={} with a amount of key={}.", token, valueMap.size());
    } catch (final EntityNotPresentException exception) {
      System.out.println(exception.getMessage());
    } catch (final FormatMismatchException exception) {
      System.out.println(exception.getMessage());
    }
  }
}
