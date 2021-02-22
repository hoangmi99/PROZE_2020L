package klient.com.company;

import java.util.LinkedList;

/**
 * klasa odpowiadajaca za stworzenie uchwytu do obiektu
 */
public class Handler {
    LinkedList<GameObject> object = new LinkedList<GameObject>();

    /**
     * aktualizuje stan obiektu
     */
    public void update() {
        for (GameObject tempObject : object) {
            tempObject.update();
        }
    }

    /**
     * dodaje obiekt do gry
     *
     * @param object obiekt (gracz)
     */
    public void addObject(GameObject object) {
        this.object.add(object);
    }

}
