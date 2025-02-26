package br.com.vinicius.server_sent_svents.Service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class SseService {

    public SseEmitter createSseConnection(Long identificador, Map<Long, SseEmitter> emitters) {
        SseEmitter sseEmitter = new SseEmitter(120_000L);

        sseEmitter.onCompletion(() -> {
            emitters.remove(identificador);
            System.out.println("Conexão SSE completada para o identificador: " + identificador);
        });

        sseEmitter.onTimeout(() -> {
            sseEmitter.complete();
            emitters.remove(identificador);
            System.out.println("Timeout na conexão SSE para o identificador: " + identificador);
        });

        sseEmitter.onError((error) -> {
            sseEmitter.completeWithError(error);
            emitters.remove(identificador);
            System.out.println("Erro na conexão SSE para o identificador: " + identificador);
        });

        emitters.put(identificador, sseEmitter);

        try {
            sseEmitter.send(SseEmitter.event().name("CONNECTED").data("Conexão SSE estabelecida para o identificador: " + identificador));
        } catch (IOException e) {
            sseEmitter.completeWithError(e);
            emitters.remove(identificador);
            System.out.println("Erro ao enviar mensagem inicial para o identificador: " + identificador);
        }

        return sseEmitter;
    }

    public void notifyFrontend(Long userToBeNotified, String notificationType, Map<Long, SseEmitter> emitters) {
        String event = new JSONObject()
                .toString();

        SseEmitter emitter = emitters.get(userToBeNotified);
        if (Objects.nonNull(emitter)) {
            try {
                emitter.send(SseEmitter.event().name(notificationType).data(event));
                System.out.println("Enviando notificação para o usuário: " + userToBeNotified);
                System.out.println("Tipo de notificação: " + notificationType);
            } catch (Exception e) {
                emitter.completeWithError(e);
                emitters.remove(userToBeNotified);
                System.out.println("Erro ao notificar o frontend para o usuário: " + userToBeNotified);
            }
        } else {
            System.out.println("Emitter não encontrado para o usuário: " + userToBeNotified);
        }
    }
}