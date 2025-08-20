//**********************Einsendeaufgabe 13************
//**********************Aufgabe 1 ********************
// In den Zeilen 94, 212, 252-255, 432
// und in der neuen Methode zeigeEndausgabe Zeile 261-309
// steht der neue Code f�r die Aufgabe 1 
//****************************************************



//**********************Aufgabe 2 ********************
// �nderungen in den Zeilen 70, 143-144, 158, und in der Methode
// spielerWechsel Zeile 364, 375-377
//****************************************************


//**********************Aufgabe 3 ********************
// In den Zeilen 100, 165-182, 368-386. Die neue Methode schummelAnzeigen Zeile 391-418 
//****************************************************


import java.util.Arrays;
import java.util.Collections;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class MemoryFeld {
	//eine innere Klasse f�r den Eventhandler des Timers
		class TimerHandler implements EventHandler<ActionEvent>{

			@Override
			//die Methode ruft die Methode karteSchliessen auf 
			public void handle(ActionEvent arg0) {
				karteSchliessen();
			}
		}
	//das Array f�r die Karten
	private MemoryKarte[] karten;
	
	//das Array f�r die Namen der Grafiken
	private String[] bilder = {
			"grafiken/apfel.jpg",
			"grafiken/birne.jpg", 
			"grafiken/blume.jpg",
			"grafiken/blume2.jpg", "grafiken/ente.jpg",
			"grafiken/fisch.jpg", "grafiken/fuchs.jpg",
			"grafiken/igel.jpg", "grafiken/kaenguruh.jpg",
			"grafiken/katze.jpg", "grafiken/kuh.jpg",
			"grafiken/maus1.jpg", "grafiken/maus2.jpg",
			"grafiken/maus3.jpg", "grafiken/melone.jpg",
			"grafiken/pilz.jpg", "grafiken/ronny.jpg",
			"grafiken/schmetterling.jpg","grafiken/sonne.jpg",
			"grafiken/wolke.jpg", "grafiken/maus4.jpg"};

//f�r die Punkte
private int menschPunkte, computerPunkte;

//zwei Labels f�r die Punkte und eins f�r den aktuellen Spieler
//**********************Aufgabe 2 **************************
private Label menschPunkteLabel, computerPunkteLabel, amZugLabel;
//**********************************************************

//f�r die Computerst�rke
private int computerst�rke;

//wie viele Karten sind aktuell umgedreht?
private int umgedrehteKarten;

//f�r das aktuelle umgedrehte Paar
private MemoryKarte [] paar;

//f�r den aktuellen Spieler
private int spieler;

//das Ged�chtnis f�r den Computer
//er speicher hier, wo das Gegenst�ck liegt
private int[][] gemerkteKarten;

//f�r den Timer
private Timeline timer;

//************Aufgabe 1 ****************************
//damit das Spiel nicht sofort beendet wird
private boolean spielZuEnde = false;
//**************************************************


//************Aufgabe 3 ****************************
//der neue Button zum Schummeln
private Button schummelButton;
//**************************************************



//der Konstruktor
public MemoryFeld() {
	
	//das Array f�r die Karten erstellen, insgesamt 42 St�ck
	karten = new MemoryKarte[42];
	
	//f�r das Paar 
	paar = new MemoryKarte[2];
	
	//f�r das Ged�chtnis
	//es speicher f�r jede Karte paarweise die Position im Spielfeld
	gemerkteKarten = new int [2][21];
	
	//keiner hat zu Beginn einen Punkt
	menschPunkte = 0;
	computerPunkte = 0;
	
	//es ist keine Karte umgedreht
	umgedrehteKarten = 0;
	//der Mensch f�ngt an
	spieler = 0;
	
	//es gibt keine gemerkten Karten
	for (int aussen = 0; aussen < 2; aussen++)
		for (int innen = 0; innen < 21; innen++)
			gemerkteKarten[aussen][innen] = -1;
}

//die Methode erstellt die Oberfl�che und zeichnet die Karten �ber eine eigene Methode. �bergeben wird ein FlowPane
public FlowPane initGUI(FlowPane feld) {
	
	//f�r die Ausgaben
	kartenZeichnen(feld);
	menschPunkteLabel = new Label(Integer.toString(menschPunkte));
	computerPunkteLabel = new Label(Integer.toString(computerPunkte));
	
//*****************************Aufgabe 2 *****************************
	//Neues Label f�r Anzeige des aktuellen Spielers, plus eine kleine optische Hervorhebung
	amZugLabel = new Label("Am Zug: Mensch");
	amZugLabel.setStyle("-fx-font-size: 14 px; -fx-font-weight: bold;");
//********************************************************************
	
	//Layout f�r Punkte und Spieleranzeige
	//in zwei Spalten anzeigen
	GridPane tempGrid = new GridPane();
	//und einf�gen, dabei werden die Koordinaten angegeben
	tempGrid.add(new Label ("Mensch: "), 0,0);
	tempGrid.add(menschPunkteLabel, 1,0);
	tempGrid.add(new Label ("Computer: "), 0,1);
	tempGrid.add(computerPunkteLabel, 1,1);
	
//****************************Aufgabe 2 *****************************	
	//einf�gen ins GridPane wichtig in die richtige Position damit es unterhalb steht
	tempGrid.add(amZugLabel, 0,2,2,1);
//*******************************************************************	
	
	
	
//*****************************Aufgabe 3 ****************************
	//der Schummel Button als Klassenvariable
	schummelButton = new Button("Alles aufdecken");
	//Aktion beim klicken auf den Button einbinden
	schummelButton.setOnAction(e -> {schummelAnzeigen();});
	//Button am Anfang aktiv, da Mensch dran ist und keine Karten offen sind
	schummelButton.setDisable(umgedrehteKarten != 0);
	//Sichtbarkeit initial abh�ngig vom Spieler
	schummelButton.setVisible(spieler == 0);
	
	//VBox 
	VBox layout = new VBox(15);
	layout.getChildren().addAll(tempGrid, schummelButton);
	layout.setStyle("-fx-padding: 20; -fx-alignment:center;");
		
	//Alle Elemente ins Feld einf�gen
	feld.getChildren().add(layout);
	
	return feld;
}
//*******************************************************************


//das eigentliche Spielfeld erstellen
private void kartenZeichnen(FlowPane feld) {
	int count = 0;
	for (int i = 0; i <= 41; i++) {
		//eine neue Karte erzeugen
		karten[i] = new MemoryKarte (bilder[count], count, this);
		//bei jeder zweiten Karte kommt auch ein neues Bild
		if ((i + 1) % 2 == 0) count++;
	}
	//die Karten werden gemischt
	Collections.shuffle(Arrays.asList(karten));
	
	//die Karten ins Spielfeld setzten
	for (int i = 0; i <= 41; i++) {
		feld.getChildren().add(karten[i]);
		//die Position der Karte setzen
		karten[i].setBildPos(i);
	}
}

//die Methode �bernimmt die wesentliche Steuerung des Spiels
//Sie wird beim Anklicken einer Karte ausgef�hrt
public void karte�ffnen(MemoryKarte karte) {
	
//****************Aufgabe1***********************	
	//keine Z�ge mehr nach Spielende
	if(spielZuEnde) return;
//***********************************************
	
	
	
	//zum Zwischenspeicher der ID und der Position
	int kartenID, kartenPos;
	
	//die Karten zwischenspeichern
	paar[umgedrehteKarten]=karte;
	
	//die ID und die Position beschaffen
	kartenID = karte.getBildID();
	kartenPos = karte.getBildPos();
	
	//die Karte in das Ged�chtnis des Computers eintragen, 
	//aber nur dann, wenn es noch keinen Eintrag an der entsprechenden Stelle gibt
	if ((gemerkteKarten[0] [kartenID] == -1))
		gemerkteKarten[0][kartenID] = kartenPos;
	else
		//wenn es schon einen Eintrag gibt und der nicht mit der aktuellen Position �bereinstimmt,
		//dann haben wir die zweite Karte gefunden
		//die wird dann in die zweite Dimension eingetragen
		if (gemerkteKarten[0][kartenID] != kartenPos)
			gemerkteKarten[1][kartenID] = kartenPos;
	//umgedrehte Karten erh�hen
	umgedrehteKarten++;
	
	//sind zwei Karten umgedreht worden?
	if (umgedrehteKarten == 2) {
		//dann pr�fen wir, ob es ein Paar ist 
		paarPr�fen(kartenID);
		//die Karten wieder umdrehen
		timer = new Timeline (new KeyFrame(Duration.millis(1500), new TimerHandler()));
		timer.play();
	}
	
	
	//haben wir zusammen 21 Paare, dann ist das Spiel vorbei
//******************Aufgabe 1*******************************	
	if(computerPunkte + menschPunkte == 21) {
		spielZuEnde = true;
		zeigeEndausgabe();
	}	
}
//**********************************************************

//******************Aufgabe 1 ******************************
//die Methode ermittelt den Gewinner und zeigt es in einem Label an
private void zeigeEndausgabe() {
	
	//Spiel stoppen damit es keine Probleme mit dem Timer gibt 
	spielZuEnde = true;
	if (timer != null) {
		timer.stop();
	}
    //Ermitteln wer gewonnen hat und dies als String ausgeben
	String gewinner;
	if(menschPunkte > computerPunkte) {
		gewinner = "Du hast gewonnen!";
	}else if (menschPunkte < computerPunkte) {
		gewinner = "Der Computer hat gewonnen!";
	}else {
		gewinner = "Unentschieden!";
}

//Zeigt das Ergebnis in einem neuem Fenster an
//Neues Fenster (Stage) erstellen, das unabh�ngig vom Hauptfenster ist 	
Stage endStage = new Stage();
//Titel f�r das neue Fenster
endStage.setTitle("Spielende");

//Layout-Container erstellen (VBox = vertikale Anordnung der Elemente) mit Abstand = 15
VBox layout = new VBox(15);
//Innenabstand und Zentrierung setzen
layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

//Label f�r die Gewinnerausgabe erstellen
Label endeLabel = new Label(gewinner);
//Text gr��er und fett darstellen
endeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

//Button zum Beenden des Spiels
Button beendenButton = new Button ("Spiel beenden");
//Beim Klick auf den Button wird Fenster schlie�en und Anwendung beenden ausgef�hrt
beendenButton.setOnAction(e -> {endStage.close(); Platform.exit();});

// Label und Button ins Layout einf�gen
layout.getChildren().addAll(endeLabel, beendenButton);

//Inhalt erstellen und Gr��e setzen
Scene scene = new Scene(layout, 300, 150);
//Szene dem Fenster zuweisen
endStage.setScene(scene);
//und anzeigen lassen
endStage.show();
	
}
//**********************************************************



//die Methode pr�ft, ob ein Paar gefunden wurde 
private void paarPr�fen(int kartenID) {
	if (paar[0].getBildID() == paar[1].getBildID()) {
		//die Punkte setzen
		paarGefunden();
		//die Karten aus dem Ged�chtnis l�schen
		gemerkteKarten[0][kartenID]= -2;
		gemerkteKarten[1][kartenID]= -2;
	}
}
//die Methode setzt die Punkte, wenn ein Paar gefunden wurde
private void paarGefunden() {
	//spielt gerade der Mensch?
	if (spieler == 0) {
		menschPunkte++;
		menschPunkteLabel.setText(Integer.toString(menschPunkte));
	}
	else {
		computerPunkte++;
		computerPunkteLabel.setText(Integer.toString(computerPunkte));
	}
}
//die Methode dreht die Karten wieder auf die R�cksetie bzw nimmt sie aus dem Spiel
private void karteSchliessen() {
	boolean raus = false;
	//ist es ein Paar?
	if (paar[0].getBildID() == paar[1].getBildID())
		raus = true;
	//wenn es ein Paar war, nehmen wir die Karten aus dem Spiel, sonst drehen wir sie nur wieder um
	paar[0].backZeigen(raus);
	paar[1].backZeigen(raus);
	//es ist keine Karte mehr ge�ffnet
	umgedrehteKarten = 0;
	//hat der Spieler kein Paar gefunden?
	if (raus == false)
		//dann wird der Spieler gewechselt
		spielerWechseln();
	else
		//hat der Computer ein Paar gefunden? Dann ist er nochmal dran
		if (spieler == 1)
			computerZug();
}

//die Methode wechselt den Spieler
private void spielerWechseln() {
	//wenn der Mensch an der Reihe war, kommt jetzt der Computer
	if (spieler == 0) {
		spieler = 1;

//*************************Aufgabe 2 ***************************		
		amZugLabel.setText("Am Zug: Computer");
		
		///****************Aufgabe 3 ***************************
		//Schummel Button deaktivieren. der Button muss hier als Klassenvariable deklariert werden
		if (schummelButton != null) schummelButton.setVisible(false);
		//******************************************************
		
		//runLater damit auf jeden Fall zuerst das Label gewechselt wird auf den aktuellen Spieler
		// und dann erst der Computer zieht
		Platform.runLater(() -> computerZug());
	}
	else {
		spieler = 0;
		amZugLabel.setText("Am Zug: Mensch");
		
		//*****************Aufgabe 3 ***************************
		// nur aktivieren, wenn gerade keine Karte offen ist
		if(schummelButton != null) {
			//wenn wir wieder dran sind wird der Button angezeigt
			schummelButton.setVisible(true);
			schummelButton.setDisable(umgedrehteKarten !=0);
		}		
	}
}

//*******************************Aufgabe 3 **********************************

private void schummelAnzeigen() {
	// nur, wenn der Mensch am Zug ist und keine Karte offen ist 
	if (spieler !=0 || umgedrehteKarten !=0) return;
	
	//alle noch nicht aufgedeckten Karten zeigen
	for (MemoryKarte karte : karten) {
		if (karte.isNochImSpiel() && !karte.isUmgedreht()) 
			karte.vorderseiteZeigen();
		}
		if (schummelButton != null) schummelButton.setDisable(true);
	
	
	//Timer setzen: nach 3 Sekunden wieder umdrehen
	Timeline schummelTimer = new Timeline(new KeyFrame(Duration.millis(3000), e -> {
		for (MemoryKarte karte : karten) {
			if (karte.isNochImSpiel() && karte.isUmgedreht()) 
				karte.backZeigen(false); //false = nicht aus dem Spiel nehmen
			}
		
		//Button wieder aktivieren, falls Mensch am Zug ist und keine Karten offen sind
		if (spieler == 0 && umgedrehteKarten == 0 && schummelButton != null) {
			schummelButton.setDisable(false);
		}
	}));
	
	schummelTimer.play();
	
}

//*****************************************************************************



//die Methode setzt die Computerz�ge um.
private void computerZug() {
	int kartenZ�hler = 0;
	int zufall = 0;
	
	boolean treffer = false;
	
	//damit der Computer keine weiteren Z�ge mehr macht, wenn das Spiel beendet ist
	if (spielZuEnde)return;
	
	//zur Steuerung �ber die Computerst�rke
	if ((int)(Math.random() * computerst�rke) == 0) {
	//erst einmal nach einem Paar suchen
	//dazu durchsuchen wir das Array gemerkteKarten, bis wir in beiden Dimensionen einen Wert f�r eine Karte finden
	while ((kartenZ�hler < 21) && (treffer == false)) {
		//gibt es in beiden Dimensionen einen Wert gr��er oder gleich 0?
		if ((gemerkteKarten[0] [kartenZ�hler] >=0) && (gemerkteKarten[1] [kartenZ�hler] >=0)) {
			//dann haben wir ein Paar
			treffer = true;
			//die Vorderseite der Karte zeigen
			karten[gemerkteKarten[0][kartenZ�hler]].vorderseiteZeigen();
			//und dann die Karte �ffnen
			karte�ffnen(karten[gemerkteKarten[0][kartenZ�hler]]);
			//die zweite Karte auch
			karten[gemerkteKarten[0][kartenZ�hler]].vorderseiteZeigen();
			karte�ffnen(karten[gemerkteKarten[1][kartenZ�hler]]);
		}
		kartenZ�hler++;
	}
}
	//wenn wir kein Paar gefunden haben, drehen wir zuf�llig zwei Karten um
	if (treffer == false) {
		//solange eine Zufallszahl suche, bis eine Karte gefunden wird, die noch im Spiel ist 
		do {
			zufall = (int)(Math.random() * karten.length);
		} while (karten[zufall].isNochImSpiel() == false);
		//die erste Karte umdrehen
		//die Vorderseite der Karte zeigen
		karten[zufall].vorderseiteZeigen();
		//und dann die Karte �ffnen
		karte�ffnen(karten[zufall]);
		//f�r die zweite Karte m�ssen wir au�erdem pr�fen, ob sie nicht gerade angezeigt wird
		do {
			zufall = (int)(Math.random() * karten.length);
		} while ((karten[zufall].isNochImSpiel() == false) || (karten[zufall].isUmgedreht() == true));
		//und die zweite Karte umdrehen
		karten[zufall].vorderseiteZeigen();
		karte�ffnen(karten[zufall]);
	}
}

//die Methode liefert, ob Z�ge des Menschen erlaubt sind 
//die R�ckgabe ist false, wenn gerade der Computer zieht oder wenn schon zwei Karten umgedreht sind
//sonst ist die R�ckgabe true
public boolean zugErlaubt() {
	boolean erlaubt = true;
	//zieht der Computer
	if (spieler == 1)
		erlaubt = false;
	//sind schon zwei Karten umgedreht?
	if (umgedrehteKarten == 2)
		erlaubt = false;
	return erlaubt;
}
}
