import java.io.File;
import java.nio.file.Files;
import java.util.*;

public class Graph {

    private final Map<Station, Set<Troncon>> mapStationTroncons;
    private final Map<String, Station> mapNomStation;
    private final Map<Integer, Ligne> mapLignes;

    public Graph(File lignes, File troncons) throws Exception {
        mapLignes = new HashMap<>();
        mapStationTroncons = new HashMap<>();
        mapNomStation = new HashMap<>();

        List<String> allLines = Files.readAllLines(lignes.toPath());
        for (String line : allLines) {
            String[] attributes = line.split(",");
            int id = Integer.parseInt(attributes[0]);
            String numero = attributes[1];
            Station depart = getStation(attributes[2]);
            Station destination = getStation(attributes[3]);
            String transport = attributes[4];
            int tempsAttenteMoyen = Integer.parseInt(attributes[5]);
            mapLignes.put(id, new Ligne(id, numero, depart, destination, transport, tempsAttenteMoyen));
        }

        allLines = Files.readAllLines(troncons.toPath());

        for (String line : allLines) {
            String[] attributes = line.split(",");
            Ligne ligne = mapLignes.get(Integer.parseInt(attributes[0]));
            Station depart = getStation(attributes[1]);
            Station arrivee = getStation(attributes[2]);
            int duree = Integer.parseInt(attributes[3]);
            Troncon troncon = new Troncon(ligne, depart, arrivee, duree);
            if (mapStationTroncons.containsKey(depart)) {
                mapStationTroncons.get(depart).add(troncon);
            } else {
                HashSet<Troncon> tronconsSet = new HashSet<>();
                tronconsSet.add(troncon);
                mapStationTroncons.put(depart, tronconsSet);
            }
        }
    }

    public Station getStation(String nomStation) {
        Station station;
        if (!mapNomStation.containsKey(nomStation)) {
            station = new Station(nomStation);
            mapNomStation.put(station.getNom(), station);
        } else {
            station = mapNomStation.get(nomStation);
        }
        return station;
    }

    // renvoie l'ensemble des tron�ons entrants de la station
    // c�d les tron�ons dont l'arrivee est la station en param�tre
    public Set<Troncon> tronconsEntrants(Station s) {
        Set<Troncon> troncons = new HashSet<>();

        for (Set<Troncon> value : mapStationTroncons.values()) {
            for (Troncon troncon : value) {
                if (troncon.getArrivee().equals(s)) {
                    troncons.add(troncon);
                }
            }
        }
        return troncons;
    }

    // affiche le chemin le plus court (en terme de nombre de troncon) entre la station de d�part
    // et la station d'arriv�e
    // Pour afficher les troncons, on utilise simplement la m�thode toString() de Troncon
    public void calculerCheminMinimisantNombreTroncons(String depart, String arrivee) {
        // A ADAPTER
        Map<Station, Troncon> map = new HashMap<>();
        LinkedList<Station> stations = new LinkedList<>();
        Set<Station> visites = new HashSet<>();
        Queue<Station> file = new ArrayDeque<Station>();
        Station stationDepart = mapNomStation.get(depart);
        Station stationArrivee = mapNomStation.get(arrivee);
        file.add(stationDepart);
        visites.add(stationDepart);
        while (!file.isEmpty()) {
            Station current = file.remove();
            if (stationArrivee.equals(current)) {
                break;
            }
            for (Troncon t : mapStationTroncons.get(current)) {
                if (!visites.contains(t.getArrivee())) {
                    map.put(t.getArrivee(), t);
                    stations.add(t.getArrivee());
                    file.add(t.getArrivee());
                    visites.add(t.getArrivee());
                }

            }


        }

        LinkedList<Troncon> chemin = new LinkedList<>();
        Station current = stationArrivee;
        while (map.containsKey(current)) {
            Troncon t = map.get(current);
            chemin.addFirst(t);
            current = t.getDepart();
        }

        // Affichage
        for (Troncon t : chemin) {
            System.out.println(t);
        }
    }

}


