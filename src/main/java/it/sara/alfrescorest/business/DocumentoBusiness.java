package it.sara.alfrescorest.business;
import org.alfresco.core.handler.NodesApi;
import org.alfresco.core.model.Node;
import org.alfresco.core.model.NodeBodyCreate;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DocumentoBusiness {
    


    static final Logger LOGGER = LoggerFactory.getLogger(DocumentoBusiness.class);

    private List<String> include = null;
    private List<String> fields = null;

    // Set generic file content
    private String contentType = "cm:content";
    // If true, then  a name clash will cause an attempt to auto rename by
    // finding a unique name using an integer suffix.
    private Boolean autoRename = true;
    // If true, then created node will be version '1.0 MAJOR'. If false, then created node will be version '0.1 MINOR'.
    private Boolean majorVersion = true;
    // Should versioning be enabled at all?
    private Boolean versioningEnabled = true;
    // Add a version comment which will appear in version history. Setting this parameter also enables versioning of
    // this node, if it is not already versioned.
    private String updateComment = null;
    // Optional new name. This should include the file extension. The name must not contain spaces or the following
    // special characters: * " < > \ / ? : and |. The character `.` must not be used at the end of the name.
    private String updatedName = null;

    @Autowired
    NodesApi nodesApi;

    @Value("${folderId}")
    String folderId;
    
    public void execute(String nomeFile, MultipartFile file) throws IOException {
        // Create a text file under the /Company Home/Guest Home folder
        //Node newTextFile = createTextFile("-root-", "somestuff1.txt", "TextfileTitle",
          //      "TextfileDesc", "/Guest Home", "Some text for the file vito12");

        // Upload a file from disk to the /Company Home/Guest Home folder, from the same directory we are running the app
        Node newFile = uploadFile(folderId, nomeFile, nomeFile, "descrizione di nome file", "/API", file);
                        
                        

    }

    /**
     * Upload a file from disk
     */
    private Node uploadFile(String parentFolderId, String fileName, String title, String description,
                            String relativeFolderPath, MultipartFile file) {
        Node fileNode = createFileMetadata(parentFolderId, fileName, title, description, relativeFolderPath);

        // Get the file bytes
        byte[] fileData = null;
        try {
            fileData = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add the file node content
        Node updatedFileNode = nodesApi.updateNodeContent(fileNode.getId(),
                fileData, majorVersion, updateComment, updatedName, include, fields).getBody().getEntry();

        LOGGER.info("Created file with content: {}", updatedFileNode.toString());

        return updatedFileNode;
    }

    /**
     * Create a text file
     */
    private Node createTextFile(String parentFolderId, String fileName, String title, String description,
                                String relativeFolderPath, String textContent) {
        Node fileNode = createFileMetadata(parentFolderId, fileName, title, description, relativeFolderPath);

        // Add the file node content
        Node updatedFileNode = nodesApi.updateNodeContent(fileNode.getId(),
                textContent.getBytes(), majorVersion, updateComment, updatedName, include, fields).getBody().getEntry();

        LOGGER.info("Created file with content: {}", updatedFileNode.toString());

        return updatedFileNode;
    }

    /**
     * Create metadata for a file node
     *
     * @param parentFolderId the parent folder node ID where the file should be stored
     * @param fileName the name for the new file
     * @param title the title property value for the new file
     * @param description the description property value for the new file
     * @param relativeFolderPath path relative to /Company Home
     * @return a Node object with file metadata and the Node ID
     */
    private Node createFileMetadata(String parentFolderId, String fileName, String title, String description,
                                    String relativeFolderPath) {
        List<String> fileAspects = new ArrayList<String>();
        fileAspects.add("cm:titled");
        Map<String, String> fileProps = new HashMap<>();
        fileProps.put("cm:title", title);
        fileProps.put("cm:description", description);
        
        NodeBodyCreate nodeBodyCreate = new NodeBodyCreate();
        nodeBodyCreate.setName(fileName);
        nodeBodyCreate.setNodeType(contentType);
        nodeBodyCreate.setAspectNames(fileAspects);
        nodeBodyCreate.setProperties(fileProps);
        nodeBodyCreate.setRelativePath(relativeFolderPath);

        // Create the file node metadata
        Node fileNode = nodesApi.createNode(parentFolderId, nodeBodyCreate, autoRename, majorVersion, versioningEnabled,
                include, fields).getBody().getEntry();

        return fileNode;
    }



}
