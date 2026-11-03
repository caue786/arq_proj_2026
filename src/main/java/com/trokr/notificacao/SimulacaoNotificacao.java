package com.trokr.notificacao;

import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class SimulacaoNotificacao {
    private final AtomicBoolean foraDoAr = new AtomicBoolean(false);

    public boolean foraDoAr() { 
        return foraDoAr.get(); 
    }
    
    public void derrubar() { 
        foraDoAr.set(true); 
    }
    
    public void religar() { 
        foraDoAr.set(false); 
    }
}