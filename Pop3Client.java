import sum.komponenten.*;

public class Pop3Client extends Client {
    Zeilenbereich textbereich;
   
    public Pop3Client(String pServerIP, int pServerPort, Zeilenbereich pZeilenbereich) {
       super(pServerIP, pServerPort);
       textbereich = pZeilenbereich;
    }
   
    public void processMessage(String pMessage){
        textbereich.haengeAn(pMessage);
    }
}
