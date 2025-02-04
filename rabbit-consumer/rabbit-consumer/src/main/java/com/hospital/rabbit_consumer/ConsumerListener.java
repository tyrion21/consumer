package com.hospital.rabbit_consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerListener {

    // Creamos un ObjectMapper para convertir objetos a JSON
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PACIENTE)
    public void readPaciente(Models.Paciente paciente) {
        // Log de la información recibida
        log.info("Paciente recibido: {}", paciente);

        // Guardar el objeto Paciente en un archivo JSON
        saveToFile(paciente, "paciente.json");
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_SENALES_VITALES)
    public void readSenalesVitales(Models.SenalesVitales senalesVitales) {
        // Log de la información recibida
        log.info("Señales vitales recibidas: {}", senalesVitales);

        // Guardar el objeto SenalesVitales en un archivo JSON
        saveToFile(senalesVitales, "senales_vitales.json");
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_MENSAJE_ALERTA)
    public void readMensajeAlerta(Models.MensajeAlerta mensajeAlerta) {
        // Log de la información recibida
        log.info("Mensaje de alerta recibido: {}", mensajeAlerta);

        // Guardar el objeto MensajeAlerta en un archivo JSON
        saveToFile(mensajeAlerta, "mensaje_alerta.json");
    }

    // Método para guardar el objeto en un archivo JSON
    private void saveToFile(Object object, String fileName) {
        try {
            // Convertir el objeto a JSON
            String json = objectMapper.writeValueAsString(object);

            // Crear el archivo donde guardar el JSON
            File file = new File(fileName);
            objectMapper.writeValue(file, object); // Escribir el objeto directamente al archivo como JSON

            // También puedes escribir en consola si deseas
            log.info("Archivo JSON generado: {}", file.getAbsolutePath());
        } catch (IOException e) {
            log.error("Error al guardar el objeto en un archivo JSON", e);
        }
    }
}
