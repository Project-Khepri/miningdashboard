package org.feathercoin.monitoring.json.rest;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JsonRequestDummyRequests {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/org/radics/monitoring/spring-config.xml");
        GiveMeCoinsJsonRequestExecutor requestExecutor = applicationContext.getBean(GiveMeCoinsJsonRequestExecutor.class);
        System.out.println(
                requestExecutor.fetchGiveMeCoinsData("0c79329187ada4ee76026f3dc44be06566f13bb7a1bd1e17e75d4624fbba8230"));

        D2JsonRequestExecutor requestExecutorD2 = applicationContext.getBean(D2JsonRequestExecutor.class);
        System.out.println(
                requestExecutorD2.fetchD2Data("46e4230c3d78197cab78dea0f8613f7eb1f2759ca36b3f1de6c2ca9951459c0e"));


    }
}
