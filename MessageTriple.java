
import java.util.Date;

public class MessageTriple 
{
    private String user;
	  private String timestamp;
	  private String message;

	  public MessageTriple(String user, String timestamp, String message) 
	  {
		this.user = user;
		this.timestamp = timestamp;
		this.message = message;
	  }

	  public String getUser() { 
		return user; 
	  }
	  
	  public String getTime() { 
		  return timestamp; 
	  }

	  public String getMessage() { 
		  return message; 
	  }
}
