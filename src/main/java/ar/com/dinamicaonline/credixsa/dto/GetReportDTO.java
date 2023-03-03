package ar.com.dinamicaonline.credixsa.dto;

public class GetReportDTO {
    private String wscx_id;
    private String wscx_nom;
    private String wscx_idcon;
    private String wscx_clab;
    private String wscx_idpol;
    
    public GetReportDTO() {
    }
    public GetReportDTO(String wscx_id, String wscx_nom, String wscx_idcon, String wscx_clab, String wscx_idpol) {
        this.wscx_id = wscx_id;
        this.wscx_nom = wscx_nom;
        this.wscx_idcon = wscx_idcon;
        this.wscx_clab = wscx_clab;
        this.wscx_idpol = wscx_idpol;
    }
    public String getWscx_id() {
        return wscx_id;
    }
    public void setWscx_id(String wscx_id) {
        this.wscx_id = wscx_id;
    }
    public String getWscx_nom() {
        return wscx_nom;
    }
    public void setWscx_nom(String wscx_nom) {
        this.wscx_nom = wscx_nom;
    }
    public String getWscx_idcon() {
        return wscx_idcon;
    }
    public void setWscx_idcon(String wscx_idcon) {
        this.wscx_idcon = wscx_idcon;
    }
    public String getWscx_clab() {
        return wscx_clab;
    }
    public void setWscx_clab(String wscx_clab) {
        this.wscx_clab = wscx_clab;
    }
    public String getWscx_idpol() {
        return wscx_idpol;
    }
    public void setWscx_idpol(String wscx_idpol) {
        this.wscx_idpol = wscx_idpol;
    }

    @Override
    public String toString() {
        return "GetReportDTO [wscx_id=" + wscx_id + ", wscx_nom=" + wscx_nom + ", wscx_idcon=" + wscx_idcon
                + ", wscx_clab=" + wscx_clab + ", wscx_idpol=" + wscx_idpol + "]";
    }
    
}
