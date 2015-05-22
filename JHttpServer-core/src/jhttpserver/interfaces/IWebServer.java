package jhttpserver.interfaces;


public interface IWebServer {

   public void get(String router,Execution exe);

   public void post(String router,Execution exe);

   public void put(String router,Execution exe);

   public void patch(String router,Execution exe);

   public void delete(String router,Execution exe);

}
