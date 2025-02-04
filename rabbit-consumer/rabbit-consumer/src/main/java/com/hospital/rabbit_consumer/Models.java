package com.hospital.rabbit_consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Models {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor

    public static class Paciente {

        private String nombre;
        private String apellido;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor

    public static class SenalesVitales {

        private double frecuenciaCardiaca;
        private double presionSanguinea;
        private double temperatura;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor

    public static class MensajeAlerta {

        private String mensaje;
        private Paciente paciente;
    }
    
}
