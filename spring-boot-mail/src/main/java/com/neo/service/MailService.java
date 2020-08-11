package com.neo.service;

import java.util.List;

/**
 * Created by summer on 2017/5/4.
 */
public interface MailService {

    /**
     * 给一个人发送邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String to, String subject, String content);

    /**
     *给多人发送邮件
     */
    public String sendSimpleMailToMore(List<String> adds, String subject, String content);

    public String sendHtmlMail(String to, String subject, String content);

    public void sendAttachmentsMail(String to, String subject, String content, String filePath);

    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);

}
