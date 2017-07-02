package listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class ServerSocketListener
 *
 */
@WebListener
public class ServerSocketListener implements ServletContextListener {
    /**
     * Default constructor. 
     */
    public ServerSocketListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  {
    	MyThread test = new MyThread(arg0.getServletContext());
    	test.start();
    }
    
    class MyThread extends Thread{
    	private ServerSocket server;
    	private Socket socket;
    	private BufferedReader reader;
    	private ServletContext context;
    	public MyThread(ServletContext context){
    		this.context = context;
    	}
    	//��дrun����
		public void run(){
			try{
				server = new ServerSocket(8888);
				System.out.println("�������׽��ִ����ɹ�");
				System.out.println("�ȴ��ͻ�������...");
				while(true){
					socket = server.accept();
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					//System.out.println("�ͻ�����"+reader.readLine());
					context.setAttribute("humiTemp", reader.readLine());
					System.out.println("�������������óɹ���");
					if(reader != null)
						reader.close();
					if(socket != null){
						socket.close();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
