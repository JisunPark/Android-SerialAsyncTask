package com.suminjin.appbase.commandhandler;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * queue에 있는 command들을 순차적으로 실행한다.
 * <p/>
 * Created by jspark on 2016-03-10.
 */
public class CommandHandler {

    private final Context context;
    private Queue<Command> cmdQueue = null;
    private Command.CommandAsyncTask currentTask;

    public CommandHandler(Context context) {
        this.context = context;
        cmdQueue = new LinkedList<>(); // commond queue 초기화
        Command.prevCmdResult = true; // 처음에는 이전 결과가 존재하지 않으므로 true로 설정
    }

    /**
     * command queue의 command들을 순차적으로 실행한다.
     */
    public void execute() {
        Iterator<Command> it = cmdQueue.iterator();
        while (it.hasNext()) {
            Command next = it.next();
            currentTask = next.getTask();
            currentTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        }
    }

    /**
     * 새로운 command 생성.
     *
     * @param task
     */
    public void add(Command.Task task) {
        add(task, null, null);
    }

    public void add(Command.Task task, Object params, Command.Callback callback) {
        cmdQueue.add(new Command(task, params, callback));
    }

    /**
     * 실행중인 command task를 취소하고, 다음 command가 실행되지 않도록 prevResult를 설정한다.
     */
    public void interrupt() {
        Command.prevCmdResult = false;
        if (currentTask != null) {
            currentTask.cancel(true);
        }
    }
}
