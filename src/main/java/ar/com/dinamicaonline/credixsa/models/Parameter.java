package ar.com.dinamicaonline.credixsa.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "api_Parameters")
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String parameterGroup;
    private String parameterName;
    private String parameterDescription;
    private String parameterType;
    private String parameterValue;
    private String userCreate;
    private Date createdOn;
    private String userUpdate;
    private Date updatedOn;

    public Parameter(int id, String parameterGroup, String parameterName, String parameterDescription,
            String parameterType, String parameterValue, String userCreate, Date createdOn, String userUpdate,
            Date updatedOn) {
        this.id = id;
        this.parameterGroup = parameterGroup;
        this.parameterName = parameterName;
        this.parameterDescription = parameterDescription;
        this.parameterType = parameterType;
        this.parameterValue = parameterValue;
        this.userCreate = userCreate;
        this.createdOn = createdOn;
        this.userUpdate = userUpdate;
        this.updatedOn = updatedOn;
    }

    public Parameter() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParameterGroup() {
        return parameterGroup;
    }

    public void setParameterGroup(String parameterGroup) {
        this.parameterGroup = parameterGroup;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterDescription() {
        return parameterDescription;
    }

    public void setParameterDescription(String parameterDescription) {
        this.parameterDescription = parameterDescription;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(String userUpdate) {
        this.userUpdate = userUpdate;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

}
