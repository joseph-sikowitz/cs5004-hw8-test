package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Stores Elements in a map hashed by the Elements' names in all lowercase.
 * @param <T> a subtype of Element.
 */
class Inventory<T extends Element> {
  private final Map<String, T> elements;

  /**
   * Constructor initializes Inventory HashMap based on parameter.
   * @param elements a Map of elements hashed by their names.
   */
  Inventory(Map<String, T> elements) {
    this.elements = new HashMap<>();
    //add all elements to ele
    for (T element : elements.values()) {
      this.addElement(element);
    }
  }

  /**
   * Helper method formats a String for use as a key by Inventory's elements Map.
   * @param name a String representing the name of an Element.
   * @return a trimmed String with all lowercase characters.
   */
  private static String formatName(String name) {
    return name.trim().toLowerCase();
  }

  /**
   * Default constructor initializes an empty HashMap.
   */
  Inventory() {
    this.elements = new HashMap<>();
  }

  /**
   * Add an element to the Inventory.
   * @param element a subtype of Element.
   */
  void addElement(T element) {
    this.elements.put(formatName(element.getName()), element);
  }

  /**
   * Returns whether this Inventory contains an Element of a given name.
   * @param name a String representing the name of an Element.
   * @return true if an Element with this name exists in this Inventory, otherwise false.
   */
  boolean containsElement(String name) {
    return this.elements.containsKey(formatName(name));
  }

  /**
   * Returns an Element subtype stored in this Inventory.
   * @param name a String representing the name of an Element
   *     that may exist within the Inventory.
   * @return an Element subtype contained within this inventory or null.
   */
  T getElement(String name) {
    return this.elements.get(formatName(name));
  }

  /**
   * Removes and returns an Element from this Inventory.
   * @param name a String representing the name of an Element
   *         that may exist within the Inventory.
   * @return an Element subtype contained within this inventory or null.
   */
  T removeElement(String name) {
    return this.elements.remove(formatName(name));
  }

  /**
   * Returns a safe copy of all elements within this Inventory.
   * @return a Map of elements hashed by their names.
   */
  Map<String, T> getElements() {
    return new HashMap<>(this.elements);
  }

}
