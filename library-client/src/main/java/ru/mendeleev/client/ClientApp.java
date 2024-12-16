package ru.mendeleev.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.mendeleev.client.gui.MainFrame;

@ComponentScan
@PropertySource("classpath:/config/client.properties")
public class ClientApp {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ClientApp.class);
        ctx.getBean(MainFrame.class);
    }
}
