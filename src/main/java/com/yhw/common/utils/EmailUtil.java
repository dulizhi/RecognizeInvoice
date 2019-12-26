package com.yhw.common.utils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wang.dingan on 2019/11/12.
 */
public class EmailUtil {
    private static String smtpServer = "10.17.29.14";
    private static String protocol = "smtp";
    private static String from = "cx.server@byd.com";
    private static String username = "cx.server@byd.com";
    private static String password = "bq6s@jkd7";

    /**
     *
     * @param to 收件人
     * @param copyTo 抄送人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return
     */
    public static boolean sendEmail(String to, String copyTo, String subject, String content) {
        boolean result=false;
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", protocol);
        props.setProperty("mail.host", smtpServer);
        props.setProperty("mail.smtp.auth", "true");
        EmailAuthentication authentication = new EmailAuthentication(username, password);
        Session session = Session.getInstance(props, authentication);
        session.setDebug(true);
        //创建代表邮件的MimeMessage对象
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(to));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(copyTo));
            message.setSentDate(new Date());
            message.setSubject(subject);
            //保存并且生成邮件对象
            BodyPart mdp = new MimeBodyPart();//新建一个存放信件内容的BodyPart对象
            mdp.setContent(content, "text/html;charset=utf-8");//给BodyPart对象设置内容和格式/编码方式tcontent为邮件内容
            Multipart mm = new MimeMultipart(); //新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个)
            mm.addBodyPart(mdp);

            message.setContent(mm);
            message.saveChanges();
            //建立发送邮件的对象
            Transport.send(message);
            result=true;
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    /**
     * 发送带Excel附件的邮件
     * @param to      收件人
     * @param copyTo  抄送人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param attachment 附件map集合，key：文件名，要带后缀名；value：文件流
     * @return 发送状态，true：成功发送，false：发送失败
     */
    public static boolean sendEmail(String to, String copyTo, String subject, String content, Map<String, InputStream> attachment) {
        boolean result = false;
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", protocol);
        props.setProperty("mail.host", smtpServer);
        props.setProperty("mail.smtp.auth", "true");
        EmailAuthentication authentication = new EmailAuthentication(username, password);
        Session session = Session.getInstance(props, authentication);
        session.setDebug(true);
        //创建代表邮件的MimeMessage对象
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(to));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(copyTo));
            message.setSentDate(new Date());
            message.setSubject(subject);
            //保存并且生成邮件对象
            Multipart mm = new MimeMultipart(); //新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个)
            BodyPart mdp = new MimeBodyPart();//新建一个存放信件内容的BodyPart对象
            mdp.setContent(content, "text/html;charset=utf-8");//给BodyPart对象设置内容和格式/编码方式tcontent为邮件内容

            Iterator<Map.Entry<String, InputStream>> iterator = attachment.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, InputStream> entry = iterator.next();
                String fileName = entry.getKey();
                InputStream is = entry.getValue();
                MimeBodyPart fileBody = new MimeBodyPart();
                DataSource source = new ByteArrayDataSource(is, "application/msexcel");
                fileBody.setDataHandler(new DataHandler(source));
                // 中文乱码问题
                fileBody.setFileName(MimeUtility.encodeText(fileName));
                mm.addBodyPart(fileBody);
            }
            mm.addBodyPart(mdp);

            message.setContent(mm);
            message.saveChanges();
            //建立发送邮件的对象
            Transport.send(message);
            result = true;
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        } finally {
            if (attachment!=null){
                Iterator<Map.Entry<String, InputStream>> iterator = attachment.entrySet().iterator();
                while (iterator.hasNext()){
                    try {
                        InputStream inputStream = iterator.next().getValue();
                        if (inputStream!=null) {
                            inputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        EmailUtil.sendEmail("wang.dingan@byd.com","wang.dingan@byd.com","test","test");
    }
}
