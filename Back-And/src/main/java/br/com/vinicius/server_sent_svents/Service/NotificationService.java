package br.com.vinicius.server_sent_svents.Service;


import br.com.vinicius.server_sent_svents.controller.NotificationType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

@Service
public class NotificationService {

    private final SseService sseService;

    @Autowired
    public NotificationService(SseService sseService) {
        this.sseService = sseService;
    }

    public void createNotification(Long identificador,NotificationType notificationType, Map<Long, SseEmitter> emitters) {
        sseService.notifyFrontend(identificador, notificationType.name() , emitters);
    }

}