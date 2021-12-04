package it.sara.alfrescorest.web.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.sara.alfrescorest.business.DocumentoBusiness;
import it.sara.alfrescorest.web.bean.CircolareWebBean;

@RestController
@RequestMapping("/rest/common")
public class DocumentController {
    private Logger logger = LoggerFactory.getLogger(DocumentController.class);
    @Autowired
    DocumentoBusiness documentoBusiness;

    @RequestMapping(value = {"/addDocument"}, method = RequestMethod.POST)
    public String uploadCircolare(@ModelAttribute("uploadForm") CircolareWebBean richiesta) throws IOException{
        logger.debug("uploadCircolare...");
        documentoBusiness.execute(richiesta.getNome(),richiesta.getFile());
        return "ok";
    }
}
