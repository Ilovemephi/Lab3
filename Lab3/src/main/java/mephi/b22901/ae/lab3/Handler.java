
package mephi.b22901.ae.lab3;


public interface Handler {
    
    void setNext(Handler nextHandler);
    void handle(String filePath); 
        
}
