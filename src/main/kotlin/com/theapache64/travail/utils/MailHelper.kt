package com.theapache64.travail.utils

import com.theapache64.travail.models.Config
import java.io.UnsupportedEncodingException
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


object MailHelper {

    fun sendMail(
        config: Config,
        to: String,
        subject: String,
        message: String
    ) {
        val properties = Properties()
        val smtp = config.smtpConfig
        properties["mail.smtp.host"] = smtp.host
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.port"] = smtp.port
        properties["mail.smtp.starttls.enable"] = true
        properties["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(smtp.username, smtp.password)
            }
        })
        val mimeMessage: Message = MimeMessage(session)
        try {

            mimeMessage.setFrom(InternetAddress(smtp.username, config.yourName))
            if (to.contains(",")) {
                //Bulk mail
                mimeMessage.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(to))
            } else {
                //Single mail
                mimeMessage.setRecipient(Message.RecipientType.TO, InternetAddress.parse(to)[0])
            }
            mimeMessage.subject = subject
            mimeMessage.setContent(message.replace("\n", "<br/>"), "text/html; charset=utf-8")

            Transport.send(mimeMessage)
        } catch (e: MessagingException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }
}