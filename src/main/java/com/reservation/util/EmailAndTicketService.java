package com.reservation.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.reservation.model.Passenger;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class EmailAndTicketService {

    @Autowired
    private JavaMailSender emailSender;

    public void generateAndSendTicket(Passenger passenger, String fromLocation, String toLocation, String fromDate) {
        try {
            byte[] pdfData = generateTicketPDF(passenger,fromLocation,toLocation,fromDate);
            sendEmailWithPDF(passenger.getEmail(), pdfData);
        } catch (DocumentException | MessagingException e) {
            e.printStackTrace();
            // Handle exceptions here
        }
    }

    public byte[] generateTicketPDF(Passenger passenger,String fromLocation, String toLocation, String fromDate) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();
        document.add(new Paragraph("Ticket Details"));
        document.add(new Paragraph("First Name: " + passenger.getFirstName()));
        document.add(new Paragraph("Last Name: " + passenger.getLastName()));
        document.add(new Paragraph("Email: " + passenger.getEmail()));
        document.add(new Paragraph("Mobile: " + passenger.getMobile()));
        document.add(new Paragraph("Bus ID: " + passenger.getBusId()));
        document.add(new Paragraph("Route ID: " + passenger.getRouteId()));
        document.add(new Paragraph("From Location"+ fromLocation));
        document.add(new Paragraph("To Location"+ toLocation));
        document.add(new Paragraph("From  Location"+ fromDate));

        document.close();
        return out.toByteArray();
    }

    public void sendEmailWithPDF(String toEmail, byte[] pdfData) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Set email properties
        helper.setTo(toEmail);
        helper.setSubject("Your Ticket Details");
        helper.setText("Please find the attached ticket for your journey.");

        // Attach the PDF
        ByteArrayDataSource dataSource = new ByteArrayDataSource(pdfData, "application/pdf");
        helper.addAttachment("ticket.pdf", dataSource);

        // Send the email
        emailSender.send(message);
    }








}
