//package service;
//
//import ie.ianduffy.carcloud.Application;
//import ie.ianduffy.carcloud.service.SMSService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
//import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
//import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import javax.inject.Inject;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//@WebAppConfiguration
//@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
//    DirtiesContextTestExecutionListener.class,
//    TransactionalTestExecutionListener.class })
//@ActiveProfiles("dev")
//public class SMSServiceTest {
//
//    @Inject
//    SMSService smsService;
//
//    @Test
//    public void sendSms() {
//        smsService.sendSMS("+353861770680", "CarCloud SMS Test");
//    }
//
//}
