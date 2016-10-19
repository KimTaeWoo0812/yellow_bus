//package net.computeering.newschoolbus.TCP;
//
//public class TCP_Que {
//    public static final int QUEUESIZE = 500;
//    public volatile static String Queue[] = new String[QUEUESIZE];
//    public volatile static int top = 0;
//    public volatile static int rear = 0;
//
//    public static synchronized boolean HasMsg() {
//        if (top == rear)
//            return false;
//        else
//            return true;
//    }
//
//    /**
//     * 메시지 큐에 넣기
//     * @param msg
//     */
//    public static void InQue(String msg){
//        Queue[top++ % QUEUESIZE] = msg;
//    }
//
//    /**
//     * 받은 메시지 빼오기
//     * @return
//     */
//    public static String DeQue(){
//        while (!HasMsg())
//            ;
//        /*
//        이부분에 빙글빙글 도는 로직 넣으면 된다.
//         */
//        return Queue[rear++ % QUEUESIZE];
//    }
//
//}
