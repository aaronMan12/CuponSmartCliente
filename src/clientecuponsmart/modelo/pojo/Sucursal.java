package clientecuponsmart.modelo.pojo;

public class Sucursal {
    
    private Integer idSucursal;
    private Integer idEmpresa;
    private Integer idUbicacion;
    private String nombre;
    private String colonia;
    private String telefono;
    private Float latitud;
    private Float longitud;
    private String direccion;
    private String codigoPostal;
    private String nombreEncargado;
    private String calle;
    private String numero;

    public Sucursal() {
    }

    public Sucursal(Integer idSucursal, Integer idEmpresa, Integer idUbicacion, String nombre, String colonia, String telefono, Float latitud, Float longitud, String direccion, String codigoPostal, String nombreEncargado, String calle, String numero) {
        this.idSucursal = idSucursal;
        this.idEmpresa = idEmpresa;
        this.idUbicacion = idUbicacion;
        this.nombre = nombre;
        this.colonia = colonia;
        this.telefono = telefono;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.nombreEncargado = nombreEncargado;
        this.calle = calle;
        this.numero = numero;
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

    public Integer getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Integer idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNombreEncargado() {
        return nombreEncargado;
    }

    public void setNombreEncargado(String nombreEncargado) {
        this.nombreEncargado = nombreEncargado;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
