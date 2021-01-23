import sum.ereignis.*;
import sum.komponenten.*;

public class GuiPop3 extends EBAnwendung {
    private Pop3Client pop3;
    private final Knopf verbindenKnopf; 
    private final Etikett serverEtikett;
    private final Textfeld serverFeld;
    private final Etikett portEtikett;
    private final Textfeld portFeld;
    private final Zeilenbereich ausgabeZeilenbereich;
    private final Knopf loginKnopf;
    private final Textfeld usernameFeld;
    private final Textfeld passwordFeld;
    private final Knopf statKnopf;
    private final Knopf listKnopf;
    private final Textfeld listFeld;
    private final Knopf retrKnopf;
    private final Textfeld retrFeld;
    
    private final Knopf deleKnopf;
    private final Textfeld deleFeld;
    private final Knopf rsetKnopf;
    private final Knopf quitKnopf;
    
    public GuiPop3() {
        super(680, 600);
        
        verbindenKnopf = new Knopf(110, 87, 120, 50, "Verbinden");   
        verbindenKnopf.setzeBearbeiterGeklickt("verbindenGeklickt");
        
        serverEtikett = new Etikett(250, 70, 160, 20, "POP3-Server");
        serverFeld = new Textfeld(250, 100, 160, 30, "");
        
        portEtikett = new Etikett(430, 70, 80, 20, "port");
        portFeld = new Textfeld(430, 100, 90, 30, "");
        
        ausgabeZeilenbereich = new Zeilenbereich(110, 360, 440, 140, "");

        loginKnopf = new Knopf(110, 150, 110, 30, "Log In");
        loginKnopf.setzeBearbeiterGeklickt("loginGeklickt");
        
        usernameFeld = new Textfeld(250, 150, 160, 30, "Benutzername...");
        passwordFeld = new Textfeld(430, 150, 160, 30, "Passwort...");
        
        statKnopf = new Knopf(110, 200, 110, 30, "STAT");
        statKnopf.setzeBearbeiterGeklickt("statKnopfGeklickt");

        listKnopf = new Knopf(110, 250, 110, 30, "LIST");
        listKnopf.setzeBearbeiterGeklickt("listKnopfGeklickt");
        listFeld = new Textfeld(250, 250, 40, 30, "");

        retrKnopf = new Knopf(110, 300, 110, 30, "RETR");
        retrKnopf.setzeBearbeiterGeklickt("retrKnopfGeklickt");
        retrFeld = new Textfeld(250, 300, 40, 30, "");
        
        deleKnopf = new Knopf(350, 200, 110, 30, "DELE");
        deleKnopf.setzeBearbeiterGeklickt("deleKnopfGeklickt");
        deleFeld = new Textfeld(480, 200, 40, 30, "");
        
        rsetKnopf = new Knopf(350, 250, 110, 30, "RSET");
        rsetKnopf.setzeBearbeiterGeklickt("rsetKnopfGeklickt");
        
        quitKnopf = new Knopf(350, 300, 110, 30, "QUIT");
        quitKnopf.setzeBearbeiterGeklickt("quitKnopfGeklickt");
    }

    public void loginGeklickt() {
        try{
            if(pop3.isConnected()){
                pop3.send("USER " + usernameFeld.inhaltAlsText());
                pop3.send("PASS " + passwordFeld.inhaltAlsText());
            } else{
                ausgabeZeilenbereich.haengeAn("Noch nicht mit Server verbunden!");
            }
        } catch(NullPointerException e){
            ausgabeZeilenbereich.haengeAn("Noch nicht mit Server verbunden!");
        }
    }

    public void verbindenGeklickt() {
        try{
            pop3 = new Pop3Client(serverFeld.inhaltAlsText(), portFeld.inhaltAlsGanzeZahl(), ausgabeZeilenbereich);
        } catch(Exception e){
            ausgabeZeilenbereich.haengeAn("Falschert in einem der beiden Felder!");
        }
    }

    public void statKnopfGeklickt() {
        try{
            if(pop3.isConnected()){
                pop3.send("STAT");
            } else{
                ausgabeZeilenbereich.haengeAn("Noch nicht mit Server verbunden!");
            }
        } catch(NullPointerException e){
            ausgabeZeilenbereich.haengeAn("Noch nicht mit Server verbunden!");
        }
    }

    public void listKnopfGeklickt() {
        try{
            if(pop3.isConnected()){
                if(listFeld.inhaltIstGanzeZahl()){
                    pop3.send("LIST " + listFeld.inhaltAlsGanzeZahl());
                    listFeld.loescheAlles();
                } else{
                    pop3.send("LIST");
                }
            } else{
                ausgabeZeilenbereich.haengeAn("Noch nicht mit Server verbunden!");
            }
        } catch(NullPointerException e){
            ausgabeZeilenbereich.haengeAn("Noch nicht mit Server verbunden!");
        }
    }

    public void retrKnopfGeklickt() {
        try{
            if(pop3.isConnected()){
                if(retrFeld.inhaltIstGanzeZahl()){
                    pop3.send("RETR " + retrFeld.inhaltAlsGanzeZahl());
                    retrFeld.loescheAlles();
                } else{
                    ausgabeZeilenbereich.haengeAn("Es muss eine Mail angegeben werden!");
                }
            } else{
                ausgabeZeilenbereich.haengeAn("Noch nicht mit Server verbunden!");
            }
        } catch(NullPointerException e){
            ausgabeZeilenbereich.haengeAn("Noch nicht mit Server verbunden!");
        }
    }
    
    public void deleKnopfGeklickt() {
        try{
            if(pop3.isConnected()){
                if(deleFeld.inhaltIstGanzeZahl()){
                    pop3.send("DELE " + deleFeld.inhaltAlsGanzeZahl());
                    deleFeld.loescheAlles();
                } else{
                    ausgabeZeilenbereich.haengeAn("Es muss eine Mail angegeben werden!");
                }
            } else{
                ausgabeZeilenbereich.haengeAn("Noch nicht mit Server verbunden!");
            }
        } catch(NullPointerException e){
            ausgabeZeilenbereich.haengeAn("Noch nicht mit Server verbunden!");
        }
    }
    
    public void rsetKnopfGeklickt() {
        try{
            if(pop3.isConnected()){
                pop3.send("RSET");
            } else{
                ausgabeZeilenbereich.haengeAn("Noch nicht mit Server verbunden!");
            }
        } catch(NullPointerException e){
            ausgabeZeilenbereich.haengeAn("Noch nicht mit Server verbunden!");
        }
    }
    
    public void quitKnopfGeklickt() {
        try{
            if(pop3.isConnected()){
                pop3.send("QUIT");
                pop3.close();
                ausgabeZeilenbereich.haengeAn("Verbindung geschlossen!");
            } else{
                ausgabeZeilenbereich.haengeAn("Noch nicht mit Server verbunden!");
            }
        } catch(NullPointerException e){
            ausgabeZeilenbereich.haengeAn("Noch nicht mit Server verbunden!");
        }
    }
}
