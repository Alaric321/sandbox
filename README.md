sandbox
=======

/* varianten zur aufnahme aller datasets 
 * 
 * A) combobox
 *    + einfachste loesung
 *    - unpraktisch
 * 1. array von datasets (pro session persistent), zugriff auf index des arrays anhand 
 *    auswahl in dropdown
 * 2. datasets fortlaufend in textfields (pro session persistent) einlesen, counter i laufen 
 *    lassen zur nummerierung in dropdown, (i-1)*rowPerDataset ist startindex zur anzeige in GUI 
 * 
 * B) table
 * 1. datasets fortlaufend einlesen und in tabelle(n) schreiben
 *    + pro session nichts persistent
 *    - tabellen muessen aufgeteilt werden oder horizontal muss sehr weit gescrollt werden
 *    ? prioritaet der datensaetze koennte anzeige reduzieren
 *    
 * C) suchfeld
 *    +- suche gezielt nach bestimmten infos
 *    - aktuell ohne Persistence ist das nur moeglich, wenn ein primary key pro dataset festegelegt
 *      wird, wie z.B. die kunden-id
 *    
 * natuerlich koennen A und C auch fuer B als auswahlkomponente benutzt werden      
 *    
 * in kombination mit obigen ansaetzen:
 * i) card pane (keine aufteilung der infos moeglich)
 * ii)tabbed pane (aufteilung der infos)
 *    
 *  */
