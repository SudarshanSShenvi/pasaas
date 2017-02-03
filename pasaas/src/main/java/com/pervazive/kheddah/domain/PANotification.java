package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PANotifType;

/**
 * A PANotification.
 */
@Entity
@Table(name = "pa_notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PANotification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "notiftype")
    private PANotifType notiftype;

    @Column(name = "msgto")
    private String msgto;

    @Column(name = "msgcc")
    private String msgcc;

    @Column(name = "msgsubject")
    private String msgsubject;

    @Column(name = "msgbody")
    private String msgbody;

    @Column(name = "msgtouchtime")
    private ZonedDateTime msgtouchtime;

    @Lob
    @Column(name = "msgattachments")
    private byte[] msgattachments;

    @Column(name = "msgattachments_content_type")
    private String msgattachmentsContentType;

    @ManyToOne
    private PAOrganization paorgnot;

    @ManyToOne
    private PAProject papronot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PANotifType getNotiftype() {
        return notiftype;
    }

    public PANotification notiftype(PANotifType notiftype) {
        this.notiftype = notiftype;
        return this;
    }

    public void setNotiftype(PANotifType notiftype) {
        this.notiftype = notiftype;
    }

    public String getMsgto() {
        return msgto;
    }

    public PANotification msgto(String msgto) {
        this.msgto = msgto;
        return this;
    }

    public void setMsgto(String msgto) {
        this.msgto = msgto;
    }

    public String getMsgcc() {
        return msgcc;
    }

    public PANotification msgcc(String msgcc) {
        this.msgcc = msgcc;
        return this;
    }

    public void setMsgcc(String msgcc) {
        this.msgcc = msgcc;
    }

    public String getMsgsubject() {
        return msgsubject;
    }

    public PANotification msgsubject(String msgsubject) {
        this.msgsubject = msgsubject;
        return this;
    }

    public void setMsgsubject(String msgsubject) {
        this.msgsubject = msgsubject;
    }

    public String getMsgbody() {
        return msgbody;
    }

    public PANotification msgbody(String msgbody) {
        this.msgbody = msgbody;
        return this;
    }

    public void setMsgbody(String msgbody) {
        this.msgbody = msgbody;
    }

    public ZonedDateTime getMsgtouchtime() {
        return msgtouchtime;
    }

    public PANotification msgtouchtime(ZonedDateTime msgtouchtime) {
        this.msgtouchtime = msgtouchtime;
        return this;
    }

    public void setMsgtouchtime(ZonedDateTime msgtouchtime) {
        this.msgtouchtime = msgtouchtime;
    }

    public byte[] getMsgattachments() {
        return msgattachments;
    }

    public PANotification msgattachments(byte[] msgattachments) {
        this.msgattachments = msgattachments;
        return this;
    }

    public void setMsgattachments(byte[] msgattachments) {
        this.msgattachments = msgattachments;
    }

    public String getMsgattachmentsContentType() {
        return msgattachmentsContentType;
    }

    public PANotification msgattachmentsContentType(String msgattachmentsContentType) {
        this.msgattachmentsContentType = msgattachmentsContentType;
        return this;
    }

    public void setMsgattachmentsContentType(String msgattachmentsContentType) {
        this.msgattachmentsContentType = msgattachmentsContentType;
    }

    public PAOrganization getPaorgnot() {
        return paorgnot;
    }

    public PANotification paorgnot(PAOrganization pAOrganization) {
        this.paorgnot = pAOrganization;
        return this;
    }

    public void setPaorgnot(PAOrganization pAOrganization) {
        this.paorgnot = pAOrganization;
    }

    public PAProject getPapronot() {
        return papronot;
    }

    public PANotification papronot(PAProject pAProject) {
        this.papronot = pAProject;
        return this;
    }

    public void setPapronot(PAProject pAProject) {
        this.papronot = pAProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PANotification pANotification = (PANotification) o;
        if (pANotification.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pANotification.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PANotification{" +
            "id=" + id +
            ", notiftype='" + notiftype + "'" +
            ", msgto='" + msgto + "'" +
            ", msgcc='" + msgcc + "'" +
            ", msgsubject='" + msgsubject + "'" +
            ", msgbody='" + msgbody + "'" +
            ", msgtouchtime='" + msgtouchtime + "'" +
            ", msgattachments='" + msgattachments + "'" +
            ", msgattachmentsContentType='" + msgattachmentsContentType + "'" +
            '}';
    }
}
