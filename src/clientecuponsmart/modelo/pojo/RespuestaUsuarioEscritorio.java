package clientecuponsmart.modelo.pojo;

import java.util.List;

public class RespuestaUsuarioEscritorio {

    private boolean error;
    private String contenido;
    private List<Usuario> usuarios;
    private List<Roll> roles;
    private List<Empresa> empresas;
    private Empresa empresa;
    private List<Sucursal> sucursales;
    private Sucursal sucursal;
    private Ubicacion ubicacion;

    public RespuestaUsuarioEscritorio() {
    }

    public RespuestaUsuarioEscritorio(boolean error, String contenido, List<Usuario> usuarios, List<Roll> roles, List<Empresa> empresas, Empresa empresa, List<Sucursal> sucursales, Sucursal sucursal, Ubicacion ubicacion) {
        this.error = error;
        this.contenido = contenido;
        this.usuarios = usuarios;
        this.roles = roles;
        this.empresas = empresas;
        this.empresa = empresa;
        this.sucursales = sucursales;
        this.sucursal = sucursal;
        this.ubicacion = ubicacion;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }


    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Roll> getRoles() {
        return roles;
    }

    public void setRoles(List<Roll> roles) {
        this.roles = roles;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

<<<<<<< HEAD

   
=======
>>>>>>> 14efde948cc5b38fff9aa1ae584ebddee73fd4a9
}
