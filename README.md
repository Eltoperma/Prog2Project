README
UDLR MODIFY
by: Marten Schnack, Tjark Hüter, Matthias Schulze

How to start:

Durch Starten der Main-Methode in der Main-Klasse.

How to use:

Login-Menü:
Es ist eine Liste vorhandener Benutzer zu sehen. Möchte man einen Benutzer hinzufügen, so gibt man den gewünschten Namen in das Input-Feld ein und klickt "Registrieren".
Anschließend wählt man diesen aus der Liste aus und startet das Spiel mit "Login". Möchte man selbst spielen, so lässt man "Spectate" unchecked. Möchte man zuschauen, so hakt man dieses ab.
Mehr dazu unten.

Controls:
W/A/S/D: Zum Steuern der Figur
R: Restart des Levels
P: Um ins nächste Level zu springen
O: Um ins vorherige Level zurückzuspringen

Upgrades:
^: Ein Schritt mit der Figur
^^: Zwei Schritte mit der Figur
^^^: Drei Schritte mit der Figur
°: Ein Block zum Entfernen eines vorhandenen Upgrades auf dieser Position
+: Freier Fertigkeitsblock, der die Position des nächsten Upgrades bestimmt.

Mit Upgrades lassen sich andere Upgrades verschieben.

Ziel des Spiels:
Ziel des Spiels ist es mit der Figur alle Upgrades einzusammeln und in eines der Ziele zu kommen und dies bei allen Levels in möglichst kurzer Zeit und in möglichst wenig Schritten zu schaffen.
Upgrades lassen sich auf einer Seite nur einmal einsammeln und nehmen Einfluss auf die Bewegungen, was dieses Spiel sehr komplex machen kann.

How to spectate:

Das Zuschauen eines Spiels ist durch das Hosten einen Spieles (dafür ganz normal spielen) und dem Aktivieren der Main-Methode des GameClients möglich, wo man die entsprechende IP in den Code einspeisen muss.
Prinzipiell ist es auch möglich, durch das Starten des Spiels auf einem anderen Gerät im gleichen Netzwerk oder dem gleichzeitigen Aktivieren der Main-Methode in "Starter" ("Main"-Klon) möglich, wenn dort die entsprechende IP
eingegeben und auf "Spectate" gedrückt wird. Aus für uns unerklärlichen Gründen bleibt das Window dann jedoch weiß, obwohl die Daten ankommen. Für den Effekt des Zuschauens reicht die hier zuerst genannte Methode aber aus.

Useful information:

Die verschiedenen Level-Klassen sind Code zur Generierung von Leveln basierend auf der Level-SuperKlasse und dessen Utility-Methoden.

Das GameFrame benutzt den Timer aus Performance-Gründen für die Animationen nicht (genauere Stelle im Code kommentiert).
