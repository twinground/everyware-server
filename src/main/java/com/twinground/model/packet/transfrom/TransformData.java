package com.twinground.model.packet.transfrom;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransformData {
    String session_id;
    private Position position;
    private Quaternion quaternion;
    private String state;
}
