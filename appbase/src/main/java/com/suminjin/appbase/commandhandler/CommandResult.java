package com.suminjin.appbase.commandhandler;

import java.util.HashMap;

/**
 * TODO 응답 결과에 맞게 멤버 변수 추가하면 됨.
 * <p>
 * Created by jspark on 2016-03-10.
 */
public class CommandResult {
    public boolean success = false;
    public String errorLog = "default error log";
    public String msgCode = null; // msg_code filed in response header
    public byte[] rawData;

    public HashMap<String, byte[]> dataMap = new HashMap<>();

    public CommandResult() {
    }

    public CommandResult(boolean success) {
        this.success = success;
    }
}
