package com.example.laptop.status.GMmodel;

import com.example.laptop.status.Bean.StatusBean;

/**
 * Created by Laptop on 24-Dec-15.
 */
public class StatusModel {
    public static StatusModel StatusInstance;

    public StatusBean statusBean = new StatusBean();

    public static StatusModel getInstance()
    {
        if(StatusInstance != null)
        {
            return StatusInstance;
        }
        else
        {
            StatusInstance = new StatusModel();
            return StatusInstance;
        }

    }
}
