package com.byrdparkgeese.hackathonbackend.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "conversations")
public class ConversationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @JoinColumn(name="users_id")
    public long usersId;

    @Column(name="status", length = 10)
    public String status;

    @Column(name = "address", length = 100)
    public String address;

    @Column(name="latitude")
    public String latitude;

    @Column(name = "longitude")
    public String longitude;

    @Column(name = "issue_desc")
    public String issueDesc;

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setUsers_id(long users_id) {
        this.usersId = users_id;
    }
    public long getUsers_id() {
        return usersId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLatitude() {
        return latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getLongitude() {
        return longitude;
    }

    public void setIssueDesc(String issueDesc) {
        this.issueDesc = issueDesc;
    }
    public String getIssueDesc() {
        return issueDesc;
    }
}
