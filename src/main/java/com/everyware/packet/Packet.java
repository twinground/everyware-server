package com.everyware.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Packet {
    private int type;
    private body body;
}
