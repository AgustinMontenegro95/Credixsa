package ar.com.dinamicaonline.credixsa.models;

import jakarta.persistence.*;

@Entity
@Table(name = "entidades")
public class Entidad {
    @Id
    @Column(name = "ENTIDAD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int entidadId;

    @Column(name = "ENTIDAD_CODIGO")
    private int entidadCodigo;

    @Column(name = "DOCUMENTO")
    private long documento;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDO")
    private String apellido;

    @Column(name = "CVU")
    private String cvu;

    @Column(name = "ALIAS")
    private String alias;

    @Column(name = "BILLETERA")
    private double billetera;

    @Column(name = "DINERO_LIQUIDAR")
    private double dineroLiquidar;

    @Column(name = "ANTICIPO")
    private double anticipo;

    @Column(name = "MARGEN")
    private double margen;

    @Column(name = "SALDO")
    private double saldo;

    @Column(name = "TELEFONO")
    private long telefono;

    @Column(name = "CELULAR")
    private long celular;

    @Column(name = "TIPO_ENTIDAD_ID")
    private int tipoEntidadId;

    @Column(name = "RUBRO_ID")
    private int rubroId;

    @Column(name = "ZONA_ID")
    private int zonaId;

    @Column(name = "LOGO")
    private String logo;

    public Entidad(int entidadId, int entidadCodigo, long documento, String nombre, String apellido, String cvu,
            String alias, double billetera, double dineroLiquidar, double anticipo, double margen, double saldo,
            long telefono, long celular, int tipoEntidadId, int rubroId, int zonaId, String logo) {
        this.entidadId = entidadId;
        this.entidadCodigo = entidadCodigo;
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cvu = cvu;
        this.alias = alias;
        this.billetera = billetera;
        this.dineroLiquidar = dineroLiquidar;
        this.anticipo = anticipo;
        this.margen = margen;
        this.saldo = saldo;
        this.telefono = telefono;
        this.celular = celular;
        this.tipoEntidadId = tipoEntidadId;
        this.rubroId = rubroId;
        this.zonaId = zonaId;
        this.logo = logo;
    }

    public Entidad() {
    }

    public int getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(int entidadId) {
        this.entidadId = entidadId;
    }

    public int getEntidadCodigo() {
        return entidadCodigo;
    }

    public void setEntidadCodigo(int entidadCodigo) {
        this.entidadCodigo = entidadCodigo;
    }

    public long getDocumento() {
        return documento;
    }

    public void setDocumento(long documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public double getBilletera() {
        return billetera;
    }

    public void setBilletera(double billetera) {
        this.billetera = billetera;
    }

    public double getDineroLiquidar() {
        return dineroLiquidar;
    }

    public void setDineroLiquidar(double dineroLiquidar) {
        this.dineroLiquidar = dineroLiquidar;
    }

    public double getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(double anticipo) {
        this.anticipo = anticipo;
    }

    public double getMargen() {
        return margen;
    }

    public void setMargen(double margen) {
        this.margen = margen;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public long getCelular() {
        return celular;
    }

    public void setCelular(long celular) {
        this.celular = celular;
    }

    public int getTipoEntidadId() {
        return tipoEntidadId;
    }

    public void setTipoEntidadId(int tipoEntidadId) {
        this.tipoEntidadId = tipoEntidadId;
    }

    public int getRubroId() {
        return rubroId;
    }

    public void setRubroId(int rubroId) {
        this.rubroId = rubroId;
    }

    public int getZonaId() {
        return zonaId;
    }

    public void setZonaId(int zonaId) {
        this.zonaId = zonaId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}
