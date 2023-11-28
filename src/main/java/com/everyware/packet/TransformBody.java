package com.everyware.packet;

import com.everyware.packet.transform.ITransform;
import com.everyware.packet.transform.TransformData;
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
    private String user_name;
    private String expo_name;
    private ITransform data;

     public TransformData toTransformData(){
         return new TransformData(this.session_id,this.user_name,this.data.getPosition(), this.data.getQuaternion(), this.data.getState());
     }
    public TransformData toTransformDataFromSession(String user_name){
        return new TransformData(this.session_id,user_name,this.data.getPosition(), this.data.getQuaternion(), this.data.getState());
    }
}

