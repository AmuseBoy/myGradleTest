//package com.liu.mail;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeUtility;
//
//import org.apache.velocity.app.VelocityEngine;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.velocity.VelocityEngineUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//
//@Controller
//public class Mail {
//
//	@Autowired
//	private JavaMailSender mailSender;
//	@Autowired
//	private VelocityEngine velocityEngine;
//
//	/**
//	 * 发送简单文本邮件
//	 */
//	@RequestMapping(value="/api/mail/test",method=RequestMethod.GET)
//	@ResponseBody
//	public void send(){
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom("liupeizhen@bestvike.com");
//		message.setTo("552852310@qq.com");
//		message.setSubject("你是否要逆天");
//		message.setText("我要逆天");
//		mailSender.send(message);
//	}
//
//	/**
//	 * 发送html邮件
//	 */
//	public void sendHTML(){
//		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		mailSender.setHost("smtp.ym.163.com");
//		mailSender.setUsername("liupeizhen@bestvike.com");
//		mailSender.setPassword("123456");
//		MimeMessage mimeMessage = mailSender.createMimeMessage();
//		try {
//			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");
//			helper.setFrom("liupeizhen@bestvike.com");
//			helper.setTo("552852310@qq.com");
//			helper.setSubject("什么玩意，怎么不行");
//			helper.setText("<html><head></head><body bgcolor='white'><h1>请勿回复！</h1>"
//					+ "<img src='cid:abc'><br />请<a href='http://www.baidu.com'>点击</a>访问首页</body></html>",
//							true);
//			helper.addInline("abc", new File("template\\abc.jpg"));//要和 cid:abc 中的一样
//			helper.addInline("图片", new File("template\\abc.jpg"));//"d:\\abc.jpg"
//			File file = new File("template\\我的图像.jpg");
//			helper.addAttachment(MimeUtility.encodeWord(file.getName()), file);//发送附件
//
//			mailSender.send(mimeMessage);
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 发哦那个html模板邮件
//	 */
//	public void sendHTMLTemplate(){
//		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		mailSender.setHost("smtp.ym.163.com");
//		mailSender.setUsername("liupeizhen@bestvike.com");
//		mailSender.setPassword("123456");
//		MimeMessage mimeMessage = mailSender.createMimeMessage();
//		try {
//			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");
//			helper.setFrom("liupeizhen@bestvike.com");
//			helper.setTo("552852310@qq.com");
//			helper.setSubject("什么玩意，这回行了");
//
//			Map<String, Object> model = new HashMap<String, Object>();
//			model.put("username", "刘先生");
//			VelocityEngine velocityEngine = new VelocityEngine();
//			String text = VelocityEngineUtils.mergeTemplateIntoString(
//	                velocityEngine, "template\\temp.vm", "UTF-8", model);
//			helper.setText(text,true);
//			helper.addInline("abc", new File("template\\abc.jpg"));//要和 cid:abc 中的一样
//			helper.addInline("图片", new File("template\\abc.jpg"));//
//			File file = new File("template\\我的图像.jpg");
//			helper.addAttachment(MimeUtility.encodeWord(file.getName()), file);//发送附件
//
//			mailSender.send(mimeMessage);
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	/**
//	 * 发哦那个html模板邮件
//	 * 启动项目测试
//	 */
//	@RequestMapping(value="/api/mail/sendHTMLTemplate2",method=RequestMethod.GET)
//	@ResponseBody
//	public void sendHTMLTemplate2(){
////		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
////		mailSender.setHost("smtp.ym.163.com");
////		mailSender.setUsername("liupeizhen@bestvike.com");
////		mailSender.setPassword("123456");
//		MimeMessage mimeMessage = mailSender.createMimeMessage();
//		try {
//			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");
//			helper.setFrom("liupeizhen@bestvike.com");
//			helper.setTo("552852310@qq.com");
//			helper.setSubject("什么玩意，这回行2了");
//
//			Map<String, Object> model = new HashMap<String, Object>();
//			model.put("username", "刘先生");
////			VelocityEngine velocityEngine = new VelocityEngine();
//			String text = VelocityEngineUtils.mergeTemplateIntoString(
//	                velocityEngine, "temp.vm", "UTF-8", model);//spring boot 的默认文件路径
//			helper.setText(text,true);
//			helper.addInline("abc", new File("template\\abc.jpg"));//要和 cid:abc 中的一样
//			helper.addInline("图片", new File("template\\abc.jpg"));//spring boot 的默认文件路径
//			File file = new File("template\\我的图像.jpg");
//			helper.addAttachment(MimeUtility.encodeWord(file.getName()), file);//发送附件
//
//			mailSender.send(mimeMessage);
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	public void sendText(){
//		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		mailSender.setHost("smtp.ym.163.com");
//		mailSender.setUsername("liupeizhen@bestvike.com");
//		mailSender.setPassword("123456");
//
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom("liupeizhen@bestvike.com");
//		message.setTo("552852310@qq.com");
//		message.setSubject("你是否要逆天");
//		message.setText("我要逆天");
//		mailSender.send(message);
//	}
//
//
//
////	public static void main(String[] args) {
////		new MailText().sendHTMLTemplate();
////	}
//}
