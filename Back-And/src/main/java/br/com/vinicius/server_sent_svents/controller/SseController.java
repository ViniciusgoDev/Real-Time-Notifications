package br.com.vinicius.server_sent_svents.controller;

import br.com.vinicius.server_sent_svents.Service.NotificationService;
import br.com.vinicius.server_sent_svents.Service.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class SseController {

    @Autowired
    private final NotificationService notificationService;
    private final SseService service;
    public SseController(SseService sseService, NotificationService notificationService) {
        this.service = sseService;
        this.notificationService = notificationService;
    }

    public Map<Long, SseEmitter> emitters = new HashMap<>();


    @GetMapping("/subscribe")
    public SseEmitter subscribe(@RequestParam("identificador") Long identificador) {
        System.out.println("Conexão SSE criada para o identificador: " + identificador);
        return service.createSseConnection(identificador, emitters);
    }
    @PostMapping(value = "/notification")
    public void newFollow(@RequestParam("identificador") Long identificador,
                          @RequestParam("notificationType") NotificationType notificationType) {
        notificationService.createNotification(identificador,notificationType,  emitters);
    }

}
