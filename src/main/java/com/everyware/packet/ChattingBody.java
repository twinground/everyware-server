package com.everyware.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChattingBody extends body{
    private String session_id;
    private String user_name;
    private String expo_name;
    private String message;

}
