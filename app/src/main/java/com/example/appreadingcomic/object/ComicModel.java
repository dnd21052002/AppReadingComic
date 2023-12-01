package com.example.appreadingcomic.object;

import java.util.List;

public class ComicModel {
    boolean success;
    String message;
    List<Comic> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Comic> getResult() {
        return result;
    }

    public void setResult(List<Comic> result) {
        this.result = result;
    }
}
