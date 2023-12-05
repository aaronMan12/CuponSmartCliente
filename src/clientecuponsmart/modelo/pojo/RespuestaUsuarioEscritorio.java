package clientecuponsmart.modelo.pojo;

import java.util.List;


public class RespuestaUsuarioEscritorio {
    private boolean error;
    private String contenido;
    private List<Usuario> usuarios;
    private List<Roll> roles;
    private List<Empresa> empresas;
    
    public RespuestaUsuarioEscritorio() {
    }

    public RespuestaUsuarioEscritorio(boolean error, String contenido, List<Usuario> usuarios, List<Roll> roles, List<Empresa> empresas) {
        this.error = error;
        this.contenido = contenido;
        this.usuarios = usuarios;
        this.roles = roles;
        this.empresas = empresas;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public List<Roll> getRoles() {
        return roles;
    }

    public void setRoles(List<Roll> roles) {
        this.roles = roles;
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

}
