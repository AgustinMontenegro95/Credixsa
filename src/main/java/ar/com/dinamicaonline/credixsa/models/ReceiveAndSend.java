package ar.com.dinamicaonline.credixsa.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "api_ReceiveAndSend")
public class ReceiveAndSend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date createdOn;
    private int idOrigin;

    @Column(nullable = true)
    private Long idAccount;
    private int receiveSend;
    private String messagge;

    @Override
    public String toString() {
        return "ReceiveAndSend [id=" + id + ", createdOn=" + createdOn + ", idOrigin=" + idOrigin + ", idAccount="
                + idAccount + ", receiveSend=" + receiveSend + ", messagge=" + messagge + "]";
    }

    public ReceiveAndSend(int id, Date createdOn, int idOrigin, long idAccount, int receiveSend, String messagge) {
        this.id = id;
        this.createdOn = createdOn;
        this.idOrigin = idOrigin;
        this.idAccount = idAccount;
        this.receiveSend = receiveSend;
        this.messagge = messagge;
    }

    public ReceiveAndSend() {
        this.idAccount = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public int getIdOrigin() {
        return idOrigin;
    }

    public void setIdOrigin(int idOrigin) {
        this.idOrigin = idOrigin;
    }

    public Long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Long idAccount) {
        this.idAccount = idAccount;
    }

    public int getReceiveSend() {
        return receiveSend;
    }

    public void setReceiveSend(int receiveSend) {
        this.receiveSend = receiveSend;
    }

    public String getMessagge() {
        return messagge;
    }

    public void setMessagge(String messagge) {
        this.messagge = messagge;
    }

}
