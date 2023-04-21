package bank.project.app;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
@EnableWs
    @Configuration

//class approvalconfiguration with the bean class as approval.
    public class ApprovalConfig extends WsConfigurerAdapter {
        @Bean(name = "approval")
        public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema approvalSchema){
            DefaultWsdl11Definition schemaObject=new DefaultWsdl11Definition();
            schemaObject.setPortTypeName("ApprovalsPort");
            schemaObject.setTargetNamespace("http://soap.project.approval");
            schemaObject.setLocationUri("/approvalPoint");
            schemaObject.setSchema(approvalSchema);
            return schemaObject;
        }
        @Bean
        public XsdSchema approvalSchema(){
            return new SimpleXsdSchema(new ClassPathResource("approval.xsd"));
        }
        @Bean
        public ServletRegistrationBean servletRegistrationBean(ApplicationContext applicationContext){
            MessageDispatcherServlet servlet=new MessageDispatcherServlet();
            servlet.setTransformWsdlLocations(true);
            servlet.setApplicationContext(applicationContext);
            return new ServletRegistrationBean(servlet,"/approvalPoint/*");
        }
    }



