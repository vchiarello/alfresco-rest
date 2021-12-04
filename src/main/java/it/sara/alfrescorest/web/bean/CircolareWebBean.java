package it.sara.alfrescorest.web.bean;

import javax.mail.Multipart;

import org.springframework.web.multipart.MultipartFile;

public class CircolareWebBean {
    public MultipartFile file;
    public String nome;
    public MultipartFile getFile() {
        return file;
    }
    public void setFile(MultipartFile file) {
        this.file = file;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
