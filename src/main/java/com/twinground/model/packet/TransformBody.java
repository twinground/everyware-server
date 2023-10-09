package com.twinground.model.packet;

import com.twinground.model.packet.transfrom.ITransform;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransformBody extends body {
    private String session_id;
    private ITransform body;
}

