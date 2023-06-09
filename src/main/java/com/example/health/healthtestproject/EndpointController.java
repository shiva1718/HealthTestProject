package com.example.health.healthtestproject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller

public class EndpointController {
    private final RequestMappingHandlerMapping handlerMapping;
    @Autowired
   
    public EndpointController(@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }
    @GetMapping("points")
    public String getEndpoints(Model model) throws IOException {
        Map<String,String> endpoints = new HashMap<>();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        for(Map.Entry<RequestMappingInfo,HandlerMethod> ent:handlerMethods.entrySet()){
            String s = ent.getKey().getPatternValues().toString();
            String a = ent.getKey().getMethodsCondition().toString();
            String uri  = s.substring(1,s.length()-1);
            String methods = a.substring(1,a.length()-1);
            try{
                if(!methods.isEmpty()&&!uri.equals("/points")){
                    endpoints.put(uri,req("http://localhost:8080"+uri,methods)+"");
                }
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        model.addAttribute("message",endpoints);

        return "test";
    }
    public int req(String url,String meth) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }

}