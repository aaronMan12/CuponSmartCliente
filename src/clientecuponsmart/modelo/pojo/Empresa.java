/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecuponsmart.modelo.pojo;

/**
 *
 * @author Oscar
 */
public class Empresa {
    private Integer idEmpresa;
    private Integer idUbicacion;
    private String nombre;
    private String nombreComercial;
    private byte[] logo;
    private String email;
    private String telefono;
    private String paginaWeb;
    private String RFC;
    private String estatus;
    private String logoBase64;
    private String nombreRepresentante;
    private String direccion;
    private String ciudad;

    public Empresa() {
    }

    public Empresa(Integer idEmpresa, Integer idUbicacion, String nombre, String nombreComercial, byte[] logo, String email, String telefono, String paginaWeb, String RFC, String estatus, String logoBase64, String nombreRepresentante, String direccion, String ciudad) {
        this.idEmpresa = idEmpresa;
        this.idUbicacion = idUbicacion;
        this.nombre = nombre;
        this.nombreComercial = nombreComercial;
        this.logo = logo;
        this.email = email;
        this.telefono = telefono;
        this.paginaWeb = paginaWeb;
        this.RFC = RFC;
        this.estatus = estatus;
        this.logoBase64 = logoBase64;
        this.nombreRepresentante = nombreRepresentante;
        this.direccion = direccion;
        this.ciudad = ciudad;
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

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getLogoBase64() {
        return logoBase64;
    }

    public void setLogoBase64(String logoBase64) {
        this.logoBase64 = logoBase64;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }
    
}
