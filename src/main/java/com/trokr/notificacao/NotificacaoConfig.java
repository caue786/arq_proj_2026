package com.trokr.notificacao;

import com.trokr.repository.LogEventoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class NotificacaoConfig {

    @Bean
    public NotificacaoPort notificacaoPort(
            SimulacaoNotificacao simulacao,
            LogEventoRepository logEventoRepository,
            @Value("${trokr.notificacao.taxa-falha:0.0}") double taxaFalha,
            @Value("${trokr.notificacao.latencia-ms:150}") long latenciaMs) {

        // 1. Adaptador base (por dentro)
        NotificacaoPort base = new NotificacaoSimuladaAdapter(simulacao, taxaFalha, latenciaMs);
        
        // 2. Disjuntor envolve o adaptador
        NotificacaoPort comDisjuntor = new NotificacaoComDisjuntor(base, logEventoRepository, 5, Duration.ofSeconds(30));
        
        // 3. Retry envolve o disjuntor (de fora para dentro: retry -> disjuntor -> adaptador)[cite: 9]
        return new NotificacaoComRetry(comDisjuntor, 3, 100);
    }
}