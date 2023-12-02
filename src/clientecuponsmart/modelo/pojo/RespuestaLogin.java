package clientecuponsmart.modelo.pojo;

public class RespuestaLogin {
    private Boolean error;
    private String contenido;
    private Usuario usuarioSesion;
    private Empresa empresaSesion;

    public RespuestaLogin() {
    }

    public RespuestaLogin(Boolean error, String contenido, Usuario usuarioSesion, Empresa empresaSesion) {
        this.error = error;
        this.contenido = contenido;
        this.usuarioSesion = usuarioSesion;
        this.empresaSesion = empresaSesion;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuario(Usuario usuario) {
        this.usuarioSesion = usuarioSesion;
    }

    public Empresa getEmpresaSesion() {
        return empresaSesion;
    }

    public void setEmpresaSesion(Empresa empresaSesion) {
        this.empresaSesion = empresaSesion;
    }
    
    
    
}
