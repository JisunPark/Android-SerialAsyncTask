package com.suminjin.serialasynctask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.suminjin.appbase.AppConfig;
import com.suminjin.appbase.commandhandler.Command;
import com.suminjin.appbase.commandhandler.CommandHandler;
import com.suminjin.appbase.commandhandler.CommandResult;

/**
 * 성공적으로 수행되는 command까지의 결과를 textView에 표시한다.
 * <p>
 * Created by jspark on 2017-04-04.
 */
public class MainActivity extends Activity {
    // 테스트용 임의의 결과. testResult[0]의 값이 false라면 첫 번째 task의 결과가 실패임을 의미하고
    // 두 번째부터의 task를 실행하지 않는다. 단, callback 작업은 계속 실행될 수도 있기 때문에
    // dafaultCallback() 메소드가 false를 return한 경우에 대해서는 ui 작업을 수행하지 않아야
    // 작업 중단 이후의 결과가 화면에 반영되지 않는다.
    private static boolean[] testResult = new boolean[]{true, true, true, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("작업 시작");

        final CommandHandler commandHandler = new CommandHandler(this);
        commandHandler.add(FIRST_TASK, null, new Command.Callback() {
            @Override
            public void execute(CommandResult result) {
                if (defaultCallback(result, commandHandler)) {
                    textView.setText(textView.getText().toString() + "\n첫 번째 작업 성공");
                }
            }
        });
        commandHandler.add(SECOND_TASK, null, new Command.Callback() {
            @Override
            public void execute(CommandResult result) {
                if (defaultCallback(result, commandHandler)) {
                    textView.setText(textView.getText().toString() + "\n두 번째 작업 성공");
                }
            }
        });
        commandHandler.add(THIRD_TASK, null, new Command.Callback() {
            @Override
            public void execute(CommandResult result) {
                if (defaultCallback(result, commandHandler)) {
                    textView.setText(textView.getText().toString() + "\n세 번째 작업 성공");
                }
            }
        });
        commandHandler.add(FOURTH_TASK, null, new Command.Callback() {
            @Override
            public void execute(CommandResult result) {
                if (defaultCallback(result, commandHandler)) {
                    textView.setText(textView.getText().toString() + "\n네 번째 작업 성공");
                }
            }
        });
        commandHandler.add(FIFTH_TASK, null, new Command.Callback() {
            @Override
            public void execute(CommandResult result) {
                if (defaultCallback(result, commandHandler)) {
                    textView.setText(textView.getText().toString() + "\n다섯 번째 작업 성공");
                }
            }
        });
        commandHandler.execute();
    }


    public boolean defaultCallback(CommandResult result, CommandHandler handler) {
        boolean taskResult; // asyncTask 작업의 결과
        if (result == null || !result.success) { // 실패
            taskResult = false;
            // TODO 필요하다면 실패 결과에 대한 공통 작업 추가
            handler.interrupt();
        } else { // 성공
            // TODO 필요하다면 성공 결과에 대한 공통 작업 추가
            taskResult = true;
        }
        return taskResult;
    }

    public Command.Task FIRST_TASK = new Command.Task() {

        @Override
        public CommandResult execute(Object objs) {
            specificDoInBackground(1);
            CommandResult result = new CommandResult(testResult[0]);
            return result;
        }
    };

    public Command.Task SECOND_TASK = new Command.Task() {

        @Override
        public CommandResult execute(Object objs) {
            specificDoInBackground(2);
            CommandResult result = new CommandResult(testResult[1]);
            return result;
        }
    };

    public Command.Task THIRD_TASK = new Command.Task() {

        @Override
        public CommandResult execute(Object objs) {
            specificDoInBackground(3);
            CommandResult result = new CommandResult(testResult[2]);
            return result;
        }
    };

    public Command.Task FOURTH_TASK = new Command.Task() {

        @Override
        public CommandResult execute(Object objs) {
            specificDoInBackground(4);
            CommandResult result = new CommandResult(testResult[3]);
            return result;
        }
    };

    public Command.Task FIFTH_TASK = new Command.Task() {

        @Override
        public CommandResult execute(Object objs) {
            specificDoInBackground(5);
            CommandResult result = new CommandResult(testResult[4]);
            return result;
        }
    };

    private void specificDoInBackground(int count) {
        Log.e(AppConfig.TAG, "백그라운드 작업 " + count);
    }
}
