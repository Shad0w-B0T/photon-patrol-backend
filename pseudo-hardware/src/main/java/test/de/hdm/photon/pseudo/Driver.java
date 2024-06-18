package test.de.hdm.photon.pseudo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hdm.photon.schema.payload.IMessagePayload;
import de.hdm.photon.schema.payload.ImmutableMessagePayload;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.UUID;

@SpringBootApplication
public class Driver {


  private static final String CONNECTION_URL = "tcp://localhost:1883";
  private static final String SUBSCRIPTION = ".photon.device";
  private static final String USERNAME = "admin";
  private static final String PASSWORD = "instar";


  public static void main(String[] args) throws MqttException, JsonProcessingException {
    SpringApplication.run(Driver.class,args);
   /* MqttClient client = new MqttClient(CONNECTION_URL, UUID.randomUUID().toString());

    MqttConnectOptions connOpts = setUpConnectionOptions(USERNAME, PASSWORD);
    client.connect(connOpts);

    final ObjectMapper objectMapper = new ObjectMapper();
    final IMessagePayload messagePayload = new ImmutableMessagePayload("sag ich dir nicht", "addr", Map.of());

    client.publish(SUBSCRIPTION, objectMapper.writeValueAsBytes(messagePayload), 1, false);
  */
  }
/*
  private static MqttConnectOptions setUpConnectionOptions(String username, String password) {
    MqttConnectOptions connOpts = new MqttConnectOptions();
    connOpts.setCleanSession(true);
    connOpts.setUserName(username);
    connOpts.setPassword(password.toCharArray());
    return connOpts;
  }*/
}
