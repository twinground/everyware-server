package com.twinground.model.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChattingBody extends body{
    private String session_id;
    private String expo_name;
    private String message;

}
