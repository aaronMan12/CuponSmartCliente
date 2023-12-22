package clientecuponsmart.modelo.pojo;

public class Ubicacion {
    
    private Integer idUbicacion;
    private Integer idSucursal;
    private Integer idEmpresa;
    private String calle;
    private Integer numero;
    private String codigoPostal;
    private String ciudad;

    public Ubicacion() {
    }

    public Ubicacion(Integer idUbicacion, Integer idSucursal, Integer idEmpresa, String calle, Integer numero, String codigoPostal, String ciudad) {
        this.idUbicacion = idUbicacion;
        this.idSucursal = idSucursal;
        this.idEmpresa = idEmpresa;
        this.calle = calle;
        this.numero = numero;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
    }

    public Integer getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Integer idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

}
