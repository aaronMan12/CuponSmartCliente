package clientecuponsmart.modelo.pojo;

import java.util.List;

public class Promocion {
    
    private Integer idPromocion;
    private Integer idCategoria;
    private String nombre;
    private String descripcion;
    private byte[] fotografia;
    private String fechaInicio;
    private String fechaFin;
    private String restriccion;
    private String tipoPromocion;
    private Float porcentajeDescuento;
    private Float precioRebajado;
    private Integer noCuponesMaximo;
    private String codigo;
    private String estatus;
    private String fotografiaBase64;
    private String categoria;
    private List<Integer> idSucursales;

    public Promocion() {
    }

    public Promocion(Integer idPromocion, Integer idCategoria, String nombre, String descripcion, byte[] fotografia, String fechaInicio, String fechaFin, String restriccion, String tipoPromocion, Float porcentajeDescuento, Float precioRebajado, Integer noCuponesMaximo, String codigo, String estatus, String fotografiaBase64, String categoria, List<Integer> idSucursales) {
        this.idPromocion = idPromocion;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fotografia = fotografia;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.restriccion = restriccion;
        this.tipoPromocion = tipoPromocion;
        this.porcentajeDescuento = porcentajeDescuento;
        this.precioRebajado = precioRebajado;
        this.noCuponesMaximo = noCuponesMaximo;
        this.codigo = codigo;
        this.estatus = estatus;
        this.fotografiaBase64 = fotografiaBase64;
        this.categoria = categoria;
        this.idSucursales = idSucursales;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getFotografia() {
        return fotografia;
    }

    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getRestriccion() {
        return restriccion;
    }

    public void setRestriccion(String restriccion) {
        this.restriccion = restriccion;
    }

    public String getTipoPromocion() {
        return tipoPromocion;
    }

    public void setTipoPromocion(String tipoPromocion) {
        this.tipoPromocion = tipoPromocion;
    }

    public Float getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Float porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public Float getPrecioRebajado() {
        return precioRebajado;
    }

    public void setPrecioRebajado(Float precioRebajado) {
        this.precioRebajado = precioRebajado;
    }

    public Integer getNoCuponesMaximo() {
        return noCuponesMaximo;
    }

    public void setNoCuponesMaximo(Integer noCuponesMaximo) {
        this.noCuponesMaximo = noCuponesMaximo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getFotografiaBase64() {
        return fotografiaBase64;
    }

    public void setFotografiaBase64(String fotografiaBase64) {
        this.fotografiaBase64 = fotografiaBase64;
    }

    public List<Integer> getIdSucursales() {
        return idSucursales;
    }

    public void setIdSucursales(List<Integer> idSucursales) {
        this.idSucursales = idSucursales;
    }
    
}
