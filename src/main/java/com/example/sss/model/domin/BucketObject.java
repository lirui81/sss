package com.example.sss.model.domin;

import com.example.sss.model.utils.Obs;
import com.obs.services.ObsClient;

public class BucketObject extends Obs {

    public BucketObject(){
        super();
    }

    public ObsClient getInstance() {
        return new ObsClient(super.getAk(), super.getSk(), super.getEndPoint());
    }


}
