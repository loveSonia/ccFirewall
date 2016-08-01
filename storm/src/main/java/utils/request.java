import java.io.IOException;
import java.util.*;
import org.json.*;
//-------------------------------------------------------------------------------------------------
class Request
{
 public String method;
 public String host;
 public String uri;
 public String refer;
 public String ua;
 public boolean FromJSON(String s)
 {
  JSONObject jo=new JSONObject(s);
  method=jo.getString("method");
  return true;
 }
 public boolean ToJSON()
 {
  return true;
 }
}//end class Request
