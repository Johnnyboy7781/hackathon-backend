package com.byrdparkgeese.hackathonbackend.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name="phone_number", length = 20, nullable = false)
    public String phoneNumber;

    @Column(name="convo_id", length = 10, nullable = false)
    public Long convoId;


    public void setId(Long id) { this.id = id; }
    public long getId() {
        return id;
    }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setConvoId(Long convoId) { this.convoId = convoId; }
    public Long getConvoId() {
        return convoId;
    }


}
