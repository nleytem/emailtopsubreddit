package com.mn.topsubreddit.notifier;

import com.mn.topsubreddit.config.model.Auth;
import com.mn.topsubreddit.config.model.Recipient;
import com.mn.topsubreddit.retriever.model.Result;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.List;
import java.util.Properties;

public class Notifier {
    private List<Result> results;
    private List<Recipient> recipients;
    private Auth auth;

    public Notifier(List<Recipient> recipients, Auth auth) {
        this.auth = auth;
        this.results = null;
        this.recipients = recipients;
    }

    public Notifier(List<Result> results, List<Recipient> recipients, Auth auth) {
        this.auth = auth;
        this.results = results;
        this.recipients = recipients;
    }

    public void sendNotifications() {
        for (Recipient recipient : this.recipients) {
            this.sendNotification(recipient);
        }
    }
    private void sendNotification(Recipient recipient) {
        String emailBody = buildMessage(recipient);
        // Setup mail server
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", "587");

        // Get the default Session object.
        String emailUsername = this.auth.getEmail_username();
        String emailPassword = this.auth.getEmail_password();
        Session session = Session.getInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication( emailUsername, emailPassword);
                    }
                });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(emailUsername));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient.getEmail()));

            // Set Subject: header field
            message.setSubject("Today's Top Links");

            // Now set the actual message
            message.setText(emailBody);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    private String buildMessage(Recipient recipient) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hey ");
        sb.append(recipient.getName());
        sb.append("!\n\n");
        sb.append("Enjoy these top posts from the last 24 hours.\n\n");

        for (Result result : this.results) {
            sb.append(result.getTitle());
            sb.append(": ");
            sb.append(result.getLink());
            sb.append("\n");
        }

        sb.append("\nHave a nice day!\n");
        sb.append("Top Post Bot");

        return sb.toString();
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public List<Recipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Recipient> recipients) {
        this.recipients = recipients;
    }
}
