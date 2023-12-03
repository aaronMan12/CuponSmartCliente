package clientecuponsmart.modelo.pojo;

import java.util.List;


public class RespuestaUsuarioEscritorio {
    private boolean error;
    private String contenido;
    private List<Usuario> usuarios;
    
    public RespuestaUsuarioEscritorio() {
    }

    public RespuestaUsuarioEscritorio(boolean error, String contenido, List<Usuario> usuarios) {
        this.error = error;
        this.contenido = contenido;
        this.usuarios = usuarios;
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
