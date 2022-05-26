package org.chreso.edots;

public class ClientVideoUploadServerResponse {
    private final String file;
    private final String uuid;
    private final String client_uuid;

    public ClientVideoUploadServerResponse(String uuid, String file, String client_uuid) {
        this.uuid = uuid;
        this.file = file;
        this.client_uuid = client_uuid;
    }


    public String getUuid() {
        return uuid;
    }

    public String getFile() {
        return file;
    }

    public String getClient_uuid() {return client_uuid;}






}
