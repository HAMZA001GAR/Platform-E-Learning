package com.jobintechtracking.app.services.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.jobintechtracking.app.services.facade.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageServiceImpl implements ImageService {

    private final BlobClientBuilder blobClientBuilder;
    private final String sasToken = "sp=racwd&st=2024-06-13T08:28:04Z&se=2026-11-08T16:28:04Z&spr=https&sv=2022-11-02&sr=c&sig=3Cu7JmlZU7vZdFEkVlVamz65Um87kmjIaZvjKTSG2AM%3D";


    public ImageServiceImpl(BlobClientBuilder blobClientBuilder) {
        this.blobClientBuilder = blobClientBuilder;
    }


    @Override
    public InputStream getImage(String filename) throws IOException {
        BlobClient blobClient = blobClientBuilder
                .blobName ( filename )
                .buildClient ( );
        try {
            return blobClient.openInputStream ( );
        } catch (Exception e) {
            throw new IOException ( "Failed to retrieve file" , e );
        }
    }


    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename ( );
        assert filename != null;
        BlobClient blobClient = blobClientBuilder
                .blobName ( filename )
                .buildClient ( );
        BlobHttpHeaders headers = new BlobHttpHeaders ( ).setContentType ( file.getContentType ( ) );
        try {
            blobClient.upload ( file.getInputStream ( ) , file.getSize ( ) , true );
        } catch (IOException e) {
            throw new RuntimeException ( "Failed to upload file" , e );
        }
        blobClient.setHttpHeaders ( headers );
        return blobClient.getBlobUrl ( );
    }

    private String extractBlobNameFromUrl(String blobFileUrl) {
        return blobFileUrl.substring ( blobFileUrl.lastIndexOf ( '/' ) + 1 );
    }

    public String updateFile(String blobFileUrl , MultipartFile file) {
        String blobName = extractBlobNameFromUrl ( blobFileUrl );
        BlobClient blobClient = blobClientBuilder
                .blobName ( blobName )
                .buildClient ( );
        BlobHttpHeaders headers = new BlobHttpHeaders ( ).setContentType ( file.getContentType ( ) );
        try {
            blobClient.upload ( file.getInputStream ( ) , file.getSize ( ) , true );
        } catch (Exception e) {
            throw new RuntimeException ( "file updated failed" );
        }

        blobClient.setHttpHeaders ( headers );

        return blobClient.getBlobUrl ( );
    }

}