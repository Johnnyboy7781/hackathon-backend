package com.byrdparkgeese.hackathonbackend.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "repliesbytype")
public class RepliesByTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(name = "category")
    public String category;

    @Column(name = "webhook_action")
    public boolean webhookAction;

    @Column(name = "reply_text")
    public String replyText;

    public void setId(long id) { this.id = id; }
    public long getId() {return id; }

    public void setCategory(String category) {this.category = category; }
    public String getCategory() { return category; }

    public void setWebhookAction(boolean webhookAction) { this.webhookAction = webhookAction; }
    public boolean getWebhookAction() { return webhookAction; }

    public void setReplyText(String replyText) { this.replyText = replyText; }
    public String getReplyText() { return replyText; }
}
