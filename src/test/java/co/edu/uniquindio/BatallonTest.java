package co.edu.uniquindio;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BatallonTest {

    /**
     * Clase para probar el metodo calcular kilometraje promedio de los vehiculos,
     * @author Sofia Cabezas mejia, Santiago Mejia, yvette daniela
     * @since 2025-04
     *
     * Licencia GNU/GPL V3.0 (https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE)
     */

    @Test
    void testcalcularKilometrajePromedioTrasporteTropas() {

        Batallon batallon = new Batallon("Alpha", "A-01");

        VehiculoTransporteTropa v1 = new VehiculoTransporteTropa(
                "V1", "Modelo1", 2020, 5000.0, EstadoOperativo.DISPONIBLE, 10
        );
        VehiculoTransporteTropa v2 = new VehiculoTransporteTropa(
                "V2", "Modelo2", 2021, 3000.0, EstadoOperativo.DISPONIBLE, 8
        );

        batallon.getListVehiculosTransporteTropa().add(v1
        );
        batallon.getListVehiculosTransporteTropa().add(v2);

        double promedio = batallon.calcularKilometrajePromedioTrasporteTropas();

        assertEquals(4000.0, promedio, 0.01, "El promedio debe ser (5000 + 3000)/2 = 4000");
    }


    @Test
    void testfiltralmisionesporMisionesPorFechayUbicacion() {

        Batallon batallon = new Batallon("Alpha", "A-01");

        Mision m1 = new Mision("M1", LocalDate.of(2023, 5, 10), "Base Norte");
        Mision m2 = new Mision("M2", LocalDate.of(2023, 6, 15), "Base Sur");
        Mision m3 = new Mision("M3", LocalDate.of(2023, 5, 20), "Base Norte");


       batallon.getListMisiones().add(m1);
       batallon.getListMisiones().add(m2);
       batallon.getListMisiones().add(m3);


        LinkedList<Mision> resultado = batallon.filtralmisionesporMisionesPorFechayUbicacion(
                "Base Norte",
                LocalDate.of(2023, 5, 1),
                LocalDate.of(2023, 5, 31)
        );


        assertEquals(2, resultado.size(), "Deberían encontrarse 2 misiones");
        assertEquals("M1", resultado.get(0).getId());
        assertEquals("M3", resultado.get(1).getId());
    }

    @Test

    void testVehiculosOrdenadosPorMisione() {


        Batallon batallon = new Batallon("Élite", "E-01");

        VehiculoTransporteTropa v1 = new VehiculoTransporteTropa(
                "V1", "ModeloA", 2020, 5000.0, EstadoOperativo.DISPONIBLE, 10
        );
        v1.setMisionesCompletadas(15);

        VehiculoBlindado v2 = new VehiculoBlindado(
                "V2", "TanqueX", 2021, 3000.0, EstadoOperativo.EN_MISION, 5
        );
        v2.setMisionesCompletadas(30);
        VehiculoApoyo v3 = new VehiculoApoyo(
                "V3", "ApoyoY", 2019, 8000.0, EstadoOperativo.DISPONIBLE, TipoFuncion.MEDICO
        );
        v3.setMisionesCompletadas(5);

        batallon.getListVehiculosTransporteTropa().add(v1);
        batallon.getListVehiculosBlindados().add(v2);
        batallon.getListVehiculosApoyo().add(v3);


        List<Vehiculo> resultado = batallon.VehiculosOrdenadosPorMisiones();


        assertEquals(3, resultado.size(), "Deberían haber 3 vehículos");


        assertEquals(30, resultado.get(0).getMisionesCompletadas(), "El vehículo con 30 misiones debería ir primero");
        assertEquals(15, resultado.get(1).getMisionesCompletadas(), "El vehículo con 15 misiones debería ir segundo");
        assertEquals(5, resultado.get(2).getMisionesCompletadas(), "El vehículo con 5 misiones debería ir último");

        assertSame(v2, resultado.get(0), "El vehículo V2 debería estar en la posición 0");
    }
}