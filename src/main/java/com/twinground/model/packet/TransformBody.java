package com.twinground.model.packet;

import com.twinground.model.packet.transfrom.ITransform;
import com.twinground.model.packet.transfrom.TransformData;
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
    private String expo_name;
    private ITransform data;

     public TransformData toTransformData(){
         return new TransformData(this.session_id,this.data.getPosition(), this.data.getQuaternion(), this.data.getState());
     }
}

