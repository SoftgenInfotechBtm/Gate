package com.softgen.gate.sender;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class GMailSender extends javax.mail.Authenticator {
    private String _user; 
      private String _pass; 
    
      private String[] _to; 
      private String _from; 
    
      private String _port; 
      private String _sport; 
    
      private String _host; 
    
      private String _subject; 
      private String _body; 
    
      private boolean _auth; 
       
      private boolean _debuggable; 
    
      private Multipart _multipart; 
    
    
      public GMailSender() { 
        _host = "smtp.gmail.com"; // default smtp server 
        _port = "465"; // default smtp port 
        _sport = "465"; // default socketfactory port 
    
        _user = ""; // username 
        _pass = ""; // password 
        _from = ""; // email sent from 
        _subject = ""; // email subject 
        _body = ""; // email body 
    
        _debuggable = false; // debug mode on or off - default off 
        _auth = true; // smtp authentication - default on 
    
        _multipart = new MimeMultipart(); 
    
        
      } 
    
      public GMailSender(String user, String pass) { 
        this(); 
    
        _user = user; 
        _pass = pass; 
      } 
    
      public boolean send() throws Exception { 
        Properties props = _setProperties(); 
    
        if(!_user.equals("") && !_pass.equals("") && _to.length > 0 && !_from.equals("") && !_subject.equals("") && !_body.equals("")) { 
          Session session = Session.getInstance(props, this); 
    
          MimeMessage msg = new MimeMessage(session); 
    
          msg.setFrom(new InternetAddress(_from)); 
           
          InternetAddress[] addressTo = new InternetAddress[_to.length]; 
          for (int i = 0; i < _to.length; i++) { 
            addressTo[i] = new InternetAddress(_to[i]); 
          } 
            msg.setRecipients(MimeMessage.RecipientType.TO, addressTo); 
    
          msg.setSubject(_subject); 
          msg.setSentDate(new Date()); 
    
          // setup message body 
          BodyPart messageBodyPart = new MimeBodyPart(); 
          messageBodyPart.setText(_body); 
          _multipart.addBodyPart(messageBodyPart); 
    
          // Put parts in message 
          msg.setContent(_multipart); 
    
          // send email 
          Transport.send(msg); 
    
          return true; 
        } else { 
          return false; 
        } 
      } 
    
     
    
      @Override 
      public javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return  new javax.mail.PasswordAuthentication(_user,_pass); 
      } 
    
      private Properties _setProperties() { 
        Properties props = new Properties(); 
    
        props.put("mail.smtp.host", _host); 
    
        if(_debuggable) { 
          props.put("mail.debug", "true"); 
        } 
    
        if(_auth) { 
          props.put("mail.smtp.auth", "true"); 
        } 
    
        props.put("mail.smtp.port", _port); 
        props.put("mail.smtp.socketFactory.port", _sport); 
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
        props.put("mail.smtp.socketFactory.fallback", "false"); 
    
        return props; 
      } 
    
      // the getters and setters 
      public String getBody() { 
        return _body; 
      } 
    
      public void setBody(String _body) { 
        this._body = _body; 
      }

    public String[] get_to() {
        return _to;
    }

    public void set_to(String[] _to) {
        this._to = _to;
    }

    public String get_from() {
        return _from;
    }

    public void set_from(String _from) {
        this._from = _from;
    }

    public String get_subject() {
        return _subject;
    }

    public void set_subject(String _subject) {
        this._subject = _subject;
    } 
    
    } 
