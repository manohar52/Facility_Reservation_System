package errorlog;

import java.util.ArrayList;
import java.util.List;

public class AppLog {


    private List<String> messages = new ArrayList<String>();
    private static AppLog ourInstance;

    private AppLog(){}
    public static AppLog getInstance(){

        if (ourInstance == null){
            ourInstance = new AppLog();
        }
        return ourInstance;
    }
    public void addMessage(String message){
        messages.add(message);
    }
    public String popMessage(){
        return messages.remove(messages.size() - 1);
    }
    public void clearLog(){
        messages.clear();
    }
}
