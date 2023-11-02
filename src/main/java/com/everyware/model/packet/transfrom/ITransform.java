package com.everyware.model.packet.transfrom;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ITransform {
    private Position position;
    private Quaternion quaternion;
    private String state;
}
