
package mephi.b22901.ae.lab3;

import java.util.List;


public interface Handler {
    
    void setNext(Handler nextHandler);
    List<Monster> importData(String filePath); 
    void exportData(String filePath, List<Monster> monsters);
        
}
