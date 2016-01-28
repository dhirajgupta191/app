package com.example.laptop.status;

/**
 * Created by Laptop on 06-Oct-15.
 */
public interface AsyncResultInterface {
    // registration
    public abstract void OnResultSuccess(String message);

    // upload status
    public abstract void OnResultSuccess(int flag, String server_id);

    // upload profile pic
    public abstract void OnResultSuccess();

    public abstract void OnResultFail();
}
