package co.edu.uniquindio;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Batallon {
    private String nombre;
    private String id;

    private LinkedList<VehiculoApoyo> listVehiculosApoyo;
    private LinkedList<VehiculoBlindado> listVehiculosBlindados;
    private LinkedList<VehiculoTransporteTropa> listVehiculosTransporteTropa;
    private static LinkedList<Soldado> personal = Mision.getPersonal();
    private LinkedList<Mision> listMisiones;

    public Batallon(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;

        this.listVehiculosApoyo = new LinkedList<>();
        this.listVehiculosBlindados = new LinkedList<>();
        this.listVehiculosTransporteTropa = new LinkedList<>();
        this.listMisiones = new LinkedList<>();
    }

    public boolean registrarMision(LocalDate fechaMision, String ubicacionMision,
                                   LinkedList listPersonal, String idVehiculomision){
        boolean flag = false;

        //Convertir de int a string
        String cantMisionesActuales = String.valueOf(listMisiones.size()+1);

        Mision newMision = new Mision(cantMisionesActuales,fechaMision,ubicacionMision);

        return flag;
    }
    public boolean registarMision (LocalDate fechaMision, String ubicacioMision, LinkedList listPersonal, String idVehiculo){

        boolean flag = false;
        
        String idMisionNueva = String.valueOf(listMisiones.size()+1);
        Mision newMision = new Mision (idMisionNueva, fechaMision, ubicacioMision);
        newMision.setPersonal(listPersonal);

        for(VehiculoTransporteTropa vehiculo : listVehiculosTransporteTropa){
            if(vehiculo.getId().equals(idVehiculo)){
                newMision.setTheVehiculo(vehiculo);
                LinkedList<Mision> listMisionesAux = vehiculo.getListMisiones();
                listMisionesAux.add(newMision);
                vehiculo.setListMisiones(listMisionesAux);
            }
        }
        listMisiones.add(newMision);

        return flag;
    }
    public LinkedList<Vehiculo> obtenerVehiculosCantMisiones() {
        LinkedList<Vehiculo> vehiculosMisionesCompletadas = new LinkedList<>();


        for (VehiculoApoyo vehiculo : listVehiculosApoyo) {
            if (vehiculo.getMisionesCompletadas() > 50){
                vehiculosMisionesCompletadas.add(vehiculo);
            }
        }

        for(VehiculoBlindado vehiculo : listVehiculosBlindados){
            if (vehiculo.getMisionesCompletadas() > 50){
                vehiculosMisionesCompletadas.add(vehiculo);
            }
        }

        for(VehiculoTransporteTropa vehiculo : listVehiculosTransporteTropa){
            if (vehiculo.getMisionesCompletadas() > 50){
                vehiculosMisionesCompletadas.add(vehiculo);
            }
        }

        return vehiculosMisionesCompletadas;
    }

    public int encontrarPosicionValida(){
        for (int i = 0; i < personal.size();i++){
            if(personal.get(i)== null){
                return i;
            }
        }
        return -1;
    }





    //creacion del metodo de que calcule  el kilometraje promedio de los vehiculos

    public double calcularKilometrajePromedioTrasporteTropas() {
        if (listVehiculosTransporteTropa.size() == 0) {
            return 0;
        }
        double kilometrajePromedio = 0;
        for (VehiculoTransporteTropa vehiculo : listVehiculosTransporteTropa) {
            kilometrajePromedio += vehiculo.getKilometraje();
        }
        return kilometrajePromedio / listVehiculosTransporteTropa.size();
    }

    public double calcularKilometrajePromedioBlindados() {
        if (listVehiculosBlindados.size() == 0) {
            return 0;
        }
        double kilometrajePromedio = 0;
        for (VehiculoBlindado vehiculo : listVehiculosBlindados) {
            kilometrajePromedio += vehiculo.getKilometraje();
        }
        return kilometrajePromedio / listVehiculosBlindados.size();
    }

    public double calcularKilometrajePromedioApoyo() {
        if (listVehiculosApoyo.size() == 0) {
            return 0;
        }
        double kilometrajePromedio = 0;
        for (VehiculoApoyo vehiculo : listVehiculosApoyo) {
            kilometrajePromedio += vehiculo.getKilometraje();
        }
        return kilometrajePromedio / listVehiculosApoyo.size();
    }
//Metodo para filtrar misiones por ubicacion y fecha

    public LinkedList<Mision> filtralmisionesporMisionesPorFechayUbicacion(String ubicacionMision,
                                                                           LocalDate fechaInicio,
                                                                           LocalDate fechaFin) {
        LinkedList<Mision> misionesFiltradas = new LinkedList<>();

        for (Mision mision : this.listMisiones) {

            boolean cumpleUbicacion = ubicacionMision == null;

                    ubicacionMision.equals(mision.getUbicacion());


            boolean cumpleFecha = true;
            if (fechaInicio != null && fechaFin != null) {
                cumpleFecha = !mision.getFecha().isBefore(fechaInicio) &&
                        !mision.getFecha().isAfter(fechaFin);
            } else if (fechaInicio != null) {
                cumpleFecha = !mision.getFecha().isBefore(fechaInicio);
            } else if (fechaFin != null) {
                cumpleFecha = !mision.getFecha().isAfter(fechaFin);
            }


            if (cumpleUbicacion && cumpleFecha) {
                misionesFiltradas.add(mision);
            }
        }

        return misionesFiltradas;
    }
// Metodo para oredenar vehiculos por misiones

    public List<Vehiculo> VehiculosOrdenadosPorMisiones() {

        List<Vehiculo> vehiculosOrdenados = new LinkedList<>();
        vehiculosOrdenados.addAll(this.listVehiculosTransporteTropa);
        vehiculosOrdenados.addAll(this.listVehiculosBlindados);
        vehiculosOrdenados.addAll(this.listVehiculosApoyo);

        vehiculosOrdenados.sort((v1, v2) ->
                v2.getMisionesCompletadas() - v1.getMisionesCompletadas()
        );

        return vehiculosOrdenados;
    }
//get and set de lista vehiculostransporte

    public LinkedList<VehiculoTransporteTropa> getListVehiculosTransporteTropa() {
        return listVehiculosTransporteTropa;
    }

    public void setListVehiculosTransporteTropa(LinkedList<VehiculoTransporteTropa> listVehiculosTransporteTropa) {
        this.listVehiculosTransporteTropa = listVehiculosTransporteTropa;
    }

    public LinkedList<VehiculoApoyo> getListVehiculosApoyo() {
        return listVehiculosApoyo;
    }

    public void setListVehiculosApoyo(LinkedList<VehiculoApoyo> listVehiculosApoyo) {
        this.listVehiculosApoyo = listVehiculosApoyo;
    }

    public LinkedList<VehiculoBlindado> getListVehiculosBlindados() {
        return listVehiculosBlindados;
    }

    public void setListVehiculosBlindados(LinkedList<VehiculoBlindado> listVehiculosBlindados) {
        this.listVehiculosBlindados = listVehiculosBlindados;
    }
}
