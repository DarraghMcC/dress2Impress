package com.dress2Impress.api;

import com.dress2Impress.common.localisation.MessageLocaliser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {

    private final MessageLocaliser messageLocaliser;

    @Autowired
    public ResponseFactory(final MessageLocaliser messageLocaliser) {
        this.messageLocaliser = messageLocaliser;
    }

    public <T> GenericSuccessfulResponse<T> buildResponse(final T data, final String code) {
        final GenericSuccessfulResponse<T> response = new GenericSuccessfulResponse<>();
        final String message = this.messageLocaliser.localiseMessage(code);

        response.setSuccessMessage(message);
        response.setData(data);

        return response;
    }


}
