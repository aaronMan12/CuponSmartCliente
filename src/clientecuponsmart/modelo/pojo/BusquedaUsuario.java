package clientecuponsmart.modelo.pojo;

public class BusquedaUsuario {
    
    private Integer idBusqueda;
    private String nombreBusqueda;

    public BusquedaUsuario() {
    }

    public BusquedaUsuario(Integer idBusqueda, String nombreBusqueda) {
        this.idBusqueda = idBusqueda;
        this.nombreBusqueda = nombreBusqueda;
    }

    public Integer getIdBusqueda() {
        return idBusqueda;
    }

    public void setIdBusqueda(Integer idBusqueda) {
        this.idBusqueda = idBusqueda;
    }

    public String getNombreBusqueda() {
        return nombreBusqueda;
    }

    public void setNombreBusqueda(String nombreBusqueda) {
        this.nombreBusqueda = nombreBusqueda;
    }

    @Override
    public String toString() {
        return nombreBusqueda;
    }
    
    
}
