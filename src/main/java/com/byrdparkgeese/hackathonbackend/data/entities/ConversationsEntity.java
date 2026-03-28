package com.byrdparkgeese.hackathonbackend.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Conversations")
public class ConversationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @OneToMany
    @JoinColumn(name="users_id")
    public long users_id;

    @Column(name="status", length = 10)
    public String status;

    @Column(name = "address", length = 100)
    public String address;

    @Column(name="latitude")
    public long latitude;

    @Column(name = "longitude")
    public long longitude;

    @Column(name = "issue_desc")
    public String issueDesc;

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setUsers_id(long users_id) {
        this.users_id = users_id;
    }
    public long getUsers_id() {
        return users_id;
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

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }
    public long getLatitude() {
        return latitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }
    public long getLongitude() {
        return longitude;
    }

    public void setIssueDesc(String issueDesc) {
        this.issueDesc = issueDesc;
    }
    public String getIssueDesc() {
        return issueDesc;
    }
}
