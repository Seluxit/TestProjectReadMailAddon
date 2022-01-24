package com.seluxit;

import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.enums.ParameterDirection;
import io.testproject.java.sdk.v2.addons.GenericAction;
import io.testproject.java.sdk.v2.addons.helpers.AddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

@Action(name = "Read Email", description="Read an Email from {{host}}")
public class ReadEmail implements GenericAction {
    @Parameter(description = "Host")
    public String host;

    @Parameter(description = "Port")
    public String port;

    @Override
    public ExecutionResult execute(AddonHelper helper) throws FailureException {
        try {
            String result = check(host, port, "true", "imap", "andreas@seluxit.com", "neo21515");

            helper.getReporter().result("Addition result is: " + result);
        } catch (Exception e) {
            throw new FailureException(
                "Failed to register user: " + e.getMessage()
            );
        }

        return ExecutionResult.PASSED;
    }

    public String check(String host, String port, String startTLS, String storeType, String user, String password) {
        try {
            //create properties field
            Properties properties = new Properties();

            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", port);
            properties.put("mail.imap.starttls.enable", startTLS);
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("imaps");

            store.connect(host, user, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            //for (int i = ; i > 0; i--) {
                Message message = messages[messages.length-1];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (messages.length));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());

                //}

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "mail";

    }
}
