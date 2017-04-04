package com.suminjin.appbase.commandhandler;

import android.os.AsyncTask;

/**
 * AsyncTask instance를 생성하고 해당 작업의 결과 값을 저장한다.
 * <p>
 * Created by jspark on 2016-03-10.
 */
public class Command {
    public static boolean prevCmdResult = true; // 앞선 command의 실행 결과

    private CommandAsyncTask asyncTask; // Command를 실행하는 AsyncTask

    /**
     * task와 callbask을 지정하여 command 생성
     *
     * @param task
     * @param commandCallback
     */
    public Command(final Command.Task task, final Object params, final Command.Callback commandCallback) {
        asyncTask = new CommandAsyncTask() {

            @Override
            protected CommandResult doInBackground(Object... args) {
                CommandResult result = null;
                if (prevCmdResult) {
                    result = new CommandResult();
                    if (task != null) {
                        result = task.execute(params);
                    }
                }

                // set static result variable for next command
                if (result == null || !result.success) {
                    prevCmdResult = false;
                } else {
                    prevCmdResult = true;
                }
                return result;
            }

            @Override
            protected void onPostExecute(CommandResult result) {
                if (commandCallback != null) {
                    commandCallback.execute(result);
                }
                super.onPostExecute(result);
            }
        };
    }

    /**
     * get AsyncTask
     *
     * @return
     */
    public CommandAsyncTask getTask() {
        return asyncTask;
    }

    /**
     * command의 실행 내용 포함
     */
    public interface Task {
        CommandResult execute(Object params);
    }

    /**
     * command 실행 후의 처리 내용 포함
     */
    public interface Callback {
        void execute(CommandResult result);
    }

    /**
     * command 실행용 AsyncTask
     */
    public class CommandAsyncTask extends AsyncTask<Object, Void, CommandResult> {

        @Override
        protected CommandResult doInBackground(Object... args) {
            return null;
        }
    }
}