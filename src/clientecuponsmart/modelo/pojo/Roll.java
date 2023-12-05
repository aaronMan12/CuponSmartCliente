package clientecuponsmart.modelo.pojo;

public class Roll {

    private Integer idRollUsuario;
    private String roll;

    public Roll() {
    }

    public Roll(Integer idRollUsuario, String roll) {
        this.idRollUsuario = idRollUsuario;
        this.roll = roll;
    }

    public Integer getIdRollUsuario() {
        return idRollUsuario;
    }

    public void setIdRollUsuario(Integer idRollUsuario) {
        this.idRollUsuario = idRollUsuario;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    @Override
    public String toString() {
        return this.roll;
    }

}
