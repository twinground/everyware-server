package com.twinground.model.packet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConnectionBody extends body {
    private String user_id;
    private int type;
}