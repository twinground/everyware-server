package com.everyware.model.packet.transfrom;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransformData {
    String session_id;
    String user_name;
    private Position position;
    private Quaternion quaternion;
    private String state;
}
