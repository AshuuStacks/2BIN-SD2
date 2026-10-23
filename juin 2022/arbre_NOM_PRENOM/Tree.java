import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class Tree implements Iterable<Tree> {

	private int value;

	private Tree parent;

	private Tree[] children;

	// *******************************************************
	// CONSTRUCTEURS
	// *******************************************************
	public Tree(int v, Tree[] chd) {
		value = v;
		children = chd;

		for (Tree child : chd) {
			child.parent = this;
		}
	}

	public Tree(int v) {
		this(v, new Tree[0]);
	}

	// *******************************************************
	// GETTERS
	// *******************************************************
	public int getValue() {
		return value;
	}

	public Tree getParent() {
		return parent;
	}

	public Tree[] getChildren() {
		return children;
	}

	// *******************************************************
	// ITERATEUR
	// *******************************************************
	public Iterator<Tree> iterator() {
		return Arrays.asList(children).iterator();
	}

	public int nbrChildren() {
		return children.length;
	}

	public boolean isLeaf() {
		return children.length == 0;
	}

	// Renvoie true si toutes les feuilles de l'arbre sont positives, false sinon.
	public boolean feuillesPositives() {
     if(isLeaf() && value<=0){
         return  false;
     }
        for (Tree child : children) {
            if(!child.feuillesPositives()){
                return false;
            }
        }

     return true;
	}

	// Renvoie un map qui a chaque entier n, associe toutes les valeurs de l'arbre se trouvant au niveau n
	public  HashMap<Integer, ArrayDeque<Integer>> niveaux(){
        HashMap<Integer,ArrayDeque<Integer>> map = new HashMap<>();
        mapbis(map,0);

        return map;
	}

    private void mapbis(HashMap<Integer, ArrayDeque<Integer>> map, int niveau) {

              if(!map.containsKey(niveau)){
                  map.put(niveau,new ArrayDeque<>());
              }
              map.get(niveau).add(value);

        for (Tree child : children) {
               child.mapbis(map,niveau+1);
        }

    }

    private void niveauAdd(HashMap<Integer, ArrayDeque<Integer>> niveaux , int niveau) {

    }

    public static void main(String[] args) {
		Tree l6 = new Tree(1);
		Tree l1 = new Tree(1);
		Tree t9 = new Tree(-9, new Tree[] { l6, l1 });
		Tree l3 = new Tree(3);
		Tree l7 = new Tree(7);
		Tree t8 = new Tree(8, new Tree[] { l3, l7 });
		Tree l4 = new Tree(4);
		Tree t1 = new Tree(-1, new Tree[] { t8, t9, l4 });
		System.out.println(t1.feuillesPositives());
		System.out.println(t1.niveaux());
	}
}
