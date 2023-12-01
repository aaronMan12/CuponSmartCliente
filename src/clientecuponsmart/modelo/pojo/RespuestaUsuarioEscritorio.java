package clientecuponsmart.modelo.pojo;

import java.util.List;


public class RespuestaUsuarioEscritorio {
    private boolean error;
    private String contenido;
    private List <Usuario> usuario;

    public RespuestaUsuarioEscritorio() {
    }

    public RespuestaUsuarioEscritorio(boolean error, String contenido, List<Usuario> usuario) {
        this.error = error;
        this.contenido = contenido;
        this.usuario = usuario;
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

    public List<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(List<Usuario> usuario) {
        this.usuario = usuario;
    }
    
    
    
}
