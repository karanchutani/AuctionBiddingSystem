package com.planify.Planify_Auc_System_Karan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@ToString
public class User extends Auditable<String> {

    @Column(unique = true)
    private String username;

    @JsonProperty
    private Boolean isSeller;

    @JsonProperty
    private Boolean isPreferredUser;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getSeller() {
        return isSeller;
    }

    public void setSeller(Boolean seller) {
        isSeller = seller;
    }

    public Boolean getPreferredUser() {
        return isPreferredUser;
    }

    public void setPreferredUser(Boolean preferredUser) {
        isPreferredUser = preferredUser;
    }
}
