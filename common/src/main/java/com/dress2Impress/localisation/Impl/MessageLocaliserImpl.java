package com.dress2Impress.localisation.Impl;

import com.dress2Impress.localisation.MessageLocaliser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageLocaliserImpl implements MessageLocaliser {

    private final MessageSource messageSource;

    @Autowired
    public MessageLocaliserImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String localiseMessage(final String messageCode) {
        return messageSource.getMessage(messageCode, null, Locale.UK);
        //TODO introduce full localisation
    }
}
