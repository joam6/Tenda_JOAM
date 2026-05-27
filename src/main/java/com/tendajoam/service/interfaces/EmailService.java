package com.tendajoam.service.interfaces;

public interface EmailService {
    
    // Mètode existent per a la confirmació de l'usuari base
    void enviarConfirmacio(String destinatari, String token);
    
    /**
     * Envia un correu a l'administrador amb les dades del nou venedor
     * per a la seva posterior validació.
     */
    void notificarNouVenedorAdmin(String nomEmpresa, String correuVenedor, String token);
    }