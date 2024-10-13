package com.cda.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

    private String content;
    @Builder.Default
    private MessageType type = MessageType.blue;
}
