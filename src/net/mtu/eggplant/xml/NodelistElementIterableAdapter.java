package net.mtu.eggplant.xml;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * An {@link Iterable} for {@link Element}s in a {@link NodeList}.
 */
public final class NodelistElementIterableAdapter implements Iterator<Element>, Iterable<Element> {
  
  public Iterator<Element> iterator() {
    return this;
  }
  
  private final NodeList nodelist;

  private int currentIndex;

  private int nextIndex;

  public NodelistElementIterableAdapter(final NodeList nodelist) {
    this.nodelist = nodelist;
    this.currentIndex = 0;
    this.nextIndex = 0;
  }

  public boolean hasNext() {
    advanceToNextElement();
    return nextIndex < nodelist.getLength();
  }

  public Element next() {
    if (hasNext()) {
      return (Element) nodelist.item(nextIndex);
    } else {
      throw new NoSuchElementException();
    }
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }

  private void advanceToNextElement() {
    if (currentIndex >= nextIndex) {
      do {
        ++nextIndex;
      } while (nextIndex < nodelist.getLength()
          && !(nodelist.item(nextIndex) instanceof Element));

      // nothing to do if next is already past current
    }
  }
}